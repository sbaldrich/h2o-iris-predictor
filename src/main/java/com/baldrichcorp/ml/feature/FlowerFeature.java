package com.baldrichcorp.ml.feature;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
 * @author Santiago Baldrich.
 */
public enum FlowerFeature {

    PETAL_WIDTH("petal_wid", SimpleExtractor.class, FlowerAttribute.PETAL_WIDTH),
    PETAL_LENGHT("petal_len", SimpleExtractor.class, FlowerAttribute.PETAL_LENGTH),
    SEPAL_WIDTH("sepal_wid", SimpleExtractor.class, FlowerAttribute.SEPAL_WIDTH),
    SEPAL_LENGHT("sepal_len", SimpleExtractor.class, FlowerAttribute.SEPAL_LENGHT),
    LENGTH_SUMS("length_sum", SumExtractor.class);

    private FeatureExtractor extractor;
    private String key;

    FlowerFeature(String key, Class<? extends FeatureExtractor> extractor){
        try {
            this.key = key;
            this.extractor = extractor.newInstance();
        }
        catch(IllegalAccessException | InstantiationException ex){
            ex.printStackTrace();
        }
    }

    FlowerFeature(String key, Class<? extends FeatureExtractor> extractor, FlowerAttribute attribute){
        try {
            this.key = key;
            this.extractor = (FeatureExtractor) extractor.getConstructors()[0].newInstance(attribute);
        }
        catch(IllegalAccessException | InstantiationException | InvocationTargetException ex){
            ex.printStackTrace();
        }
    }

    public Object extract(Map<FlowerAttribute, Object> featureMap){
        return this.extractor.extract(featureMap);
    }

    public String getKey(){
        return key;
    }
}
