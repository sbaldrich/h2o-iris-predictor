package com.baldrichcorp.ml.feature;

import com.baldrichcorp.ml.domain.Flower;

import java.util.HashMap;
import java.util.Map;

import static com.baldrichcorp.ml.feature.FlowerAttribute.*;

/**
 * @author Santiago Baldrich.
 */
public class FeatureAdapter {
    public Map<FlowerAttribute, Object> adapt(Flower flower){
        Map<FlowerAttribute, Object> featureMap = new HashMap<>();
        featureMap.put(PETAL_LENGTH, flower.getPetalLength());
        featureMap.put(PETAL_WIDTH, flower.getPetalWidth());
        featureMap.put(SEPAL_LENGHT, flower.getSepalLength());
        featureMap.put(SEPAL_WIDTH, flower.getSepalWidth());
        return featureMap;
    }
}
