package com.baldrichcorp.ml.service;

import com.baldrichcorp.ml.domain.Predictor;
import com.baldrichcorp.ml.repository.PredictorRepository;
import hex.genmodel.GenModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Santiago Baldrich.
 */
@Log
@Service
public class ModelSelectionService {

    private PredictorRepository predictorRepository;

    @Autowired
    public ModelSelectionService(PredictorRepository predictorRepository) {
        this.predictorRepository = predictorRepository;
    }

    public EasyPredictModelWrapper obtainModel(Long id) {
        try{
            return new EasyPredictModelWrapper(importModel(predictorRepository.findOne(id)));
        }
        catch(IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e){
            log.warning("Model not found");
            return null;
        }
    }

    private GenModel importModel(Predictor predictor) throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {

        File file = new File(predictor.getIdentifier());
        FileCopyUtils.copy(predictor.getData(), file);
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
            String className = je.getName().substring(0, je.getName().length() - 6); //.class
            className = className.replace("/", ".");
            classes.put(className, loader.loadClass(className));
        }
        loader.close();
        jar.close();
        return (GenModel) classes.get(predictor.getQualifiedName()).newInstance();
    }
}
