package com.baldrichcorp.ml.service;

import com.baldrichcorp.ml.domain.Flower;
import com.baldrichcorp.ml.feature.FeatureAdapter;
import com.baldrichcorp.ml.feature.FlowerAttribute;
import com.baldrichcorp.ml.feature.FlowerFeature;
import hex.genmodel.easy.RowData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

import static com.baldrichcorp.ml.feature.FlowerFeature.*;

/**
 * @author Santiago Baldrich.
 */
@Service
public class FeatureService {
    public RowData extractFeatures(Flower flower){
        RowData data = new RowData();
        Map<FlowerAttribute, Object> featureMap = new FeatureAdapter().adapt(flower);
        Arrays.asList(SEPAL_LENGHT, SEPAL_WIDTH, PETAL_LENGHT, PETAL_WIDTH, LENGTH_SUMS).stream().forEach(ex -> data.put(ex.getKey(), ex.extract(featureMap)));
        return data;
    }
}
