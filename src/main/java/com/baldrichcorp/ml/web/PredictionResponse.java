package com.baldrichcorp.ml.web;

import com.baldrichcorp.ml.domain.Prediction;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Santiago Baldrich.
 */
@Getter
public class PredictionResponse implements Serializable {

    private String label;
    private double probability;
    private Status status;

    public PredictionResponse(Prediction prediction) {
        this.status = prediction.isSuccessful() ? Status.SUCCESS : Status.FAILURE;
        this.label = prediction.getPredictedLabel();
        this.probability = prediction.getProbability();
    }

    private static enum Status {
        SUCCESS, FAILURE
    }

}
