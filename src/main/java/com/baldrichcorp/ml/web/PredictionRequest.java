package com.baldrichcorp.ml.web;

import com.baldrichcorp.ml.domain.Flower;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Santiago Baldrich.
 */
@Getter
@ToString
public class PredictionRequest {
    private Flower flower;
    private Long modelId;
}
