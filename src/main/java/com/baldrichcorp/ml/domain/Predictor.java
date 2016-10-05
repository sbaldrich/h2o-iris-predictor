package com.baldrichcorp.ml.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @author Santiago Baldrich.
 */
@Getter
@Entity
@ToString
public class Predictor {
    @Id
    @GeneratedValue
    private Long id;
    private String qualifiedName;
    private String identifier;
    @Lob
    private byte[] data;

    private Predictor() {
    }

    public Predictor(String qualifiedName, String identifier, byte[] data) {
        this();
        this.qualifiedName = qualifiedName;
        this.identifier = identifier;
        this.data = data;
    }
}
