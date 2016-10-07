package com.baldrichcorp.ml.domain;

import com.baldrichcorp.ml.feature.FlowerAttribute;
import com.baldrichcorp.ml.feature.FlowerFeature;
import com.baldrichcorp.ml.validator.FeaturesExist;
import hex.genmodel.GenModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author Santiago Baldrich.
 */
@Getter
@Entity
@ToString
@FeaturesExist
@Slf4j
public class Predictor {
    @Id
    @GeneratedValue
    private Long id;
    private String qualifiedName;
    private String identifier;
    @Lob
    private byte[] data;
    @ElementCollection
    private List<String> features;
    @Transient
    @Getter(AccessLevel.NONE)
    private EasyPredictModelWrapper model;

    private Predictor() {
    }

    public Predictor(String qualifiedName, String identifier, byte[] data, List<String> features) {
        this();
        this.qualifiedName = qualifiedName;
        this.identifier = identifier;
        this.data = data;
        this.features = Objects.isNull(features) ? new ArrayList<>() : features.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    public RowData computeFeatures(Map<FlowerAttribute, Object> attributeMap) {
        RowData data = new RowData();
        features.stream().map(FlowerFeature::valueOf).forEach(ff -> data.put(ff.getKey(), ff.extract(attributeMap)));
        return data;
    }

    public Optional<EasyPredictModelWrapper> obtainModel() {
        if (model == null) {
            synchronized (this) {
                if (model != null)
                    return Optional.of(model);
                try {
                    log.info("Instantiating GenModel for [" + qualifiedName + "]");
                    File file = new File(identifier);
                    FileCopyUtils.copy(data, file);
                    JarFile jar = new JarFile(file);

                    Enumeration<JarEntry> entries = jar.entries();
                    URL[] urls = {new URL("file:" + file.getAbsolutePath())};
                    URLClassLoader loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
                    Map<String, Class<?>> classes = new HashMap<>();

                    while (entries.hasMoreElements()) {
                        JarEntry je = entries.nextElement();
                        if (!je.getName().endsWith(".class")) {
                            continue;
                        }
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace("/", ".");
                        classes.put(className, loader.loadClass(className));
                    }
                    loader.close();
                    jar.close();
                    model = new EasyPredictModelWrapper((GenModel) classes.get(qualifiedName).newInstance());
                    return Optional.of(model);
                } catch (IOException | ClassNotFoundException | InstantiationException |
                        IllegalAccessException ex) {
                    log.error("Couldn't instantiate GenModel for class" + qualifiedName + ".", ex);
                    return Optional.empty();
                }

            }
        }
        return Optional.of(model);
    }
}
