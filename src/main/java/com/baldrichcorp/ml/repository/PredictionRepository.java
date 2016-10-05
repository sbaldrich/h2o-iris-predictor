package com.baldrichcorp.ml.repository;

import com.baldrichcorp.ml.domain.Prediction;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Santiago Baldrich.
 */
public interface PredictionRepository extends CrudRepository<Prediction, Long> {
}
