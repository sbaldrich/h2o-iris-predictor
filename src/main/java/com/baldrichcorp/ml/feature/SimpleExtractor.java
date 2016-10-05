package com.baldrichcorp.ml.feature;

import java.util.Map;

/**
 * @author Santiago Baldrich.
 */
public class SimpleExtractor implements FeatureExtractor {

    private final FlowerAttribute feature;

    public SimpleExtractor(FlowerAttribute feature) {
        this.feature = feature;
    }

    @Override
    public Object extract(Map<FlowerAttribute, Object> features) {
        return features.get(feature);
    }
}
