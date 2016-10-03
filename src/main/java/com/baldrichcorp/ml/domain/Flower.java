package com.baldrichcorp.ml.domain;


import lombok.Getter;
import lombok.ToString;

/**
 * @author Santiago Baldrich.
 */
@Getter
@ToString
public class Flower {

    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;

    private Flower() {}

    public Flower(double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
    }

}
