package com.baldrichcorp.ml.web;

import com.baldrichcorp.ml.domain.Flower;
import lombok.Getter;

/**
 * @author Santiago Baldrich.
 */
@Getter
public class PredictionRequest {
    private Flower flower;
    private Long modelId;
}
