package com.baldrichcorp.ml.service;

import com.baldrichcorp.ml.domain.Flower;
import hex.genmodel.easy.RowData;
import org.springframework.stereotype.Service;

/**
 * @author Santiago Baldrich.
 */
@Service
public class FeatureService {
    public RowData extractFeatures(Flower flower){
        RowData data = new RowData();
        data.put("sepal_len", flower.getSepalLength());
        data.put("sepal_wid", flower.getSepalWidth());
        data.put("petal_len", flower.getPetalLength());
        data.put("petal_wid", flower.getPetalWidth());
        return data;
    }
}
