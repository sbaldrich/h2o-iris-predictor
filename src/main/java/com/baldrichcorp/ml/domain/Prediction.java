package com.baldrichcorp.ml.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


/**
 * @author Santiago Baldrich.
 */
@Getter
@Entity
@Builder
public class Prediction {

    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    @NotNull
    private Flower flower;
    private String predictedLabel;
    private double probability;
    private boolean successful;
    private String message;

    @Tolerate
    private Prediction() {}
}
