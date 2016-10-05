package com.baldrichcorp.ml.feature;

import java.util.Map;

/**
 * @author Santiago Baldrich.
 */
public class SumExtractor implements FeatureExtractor {
    @Override
    public Object extract(Map<FlowerAttribute, Object> features) {
        return ((Double)features.get(FlowerAttribute.PETAL_LENGTH)) + ((Double)features.get(FlowerAttribute.SEPAL_LENGHT));
    }
}
