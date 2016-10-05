package com.baldrichcorp.ml.feature;

import java.util.Map;

/**
 * @author Santiago Baldrich.
 */
public interface FeatureExtractor {
    Object extract(Map<FlowerAttribute, Object> features);
}
