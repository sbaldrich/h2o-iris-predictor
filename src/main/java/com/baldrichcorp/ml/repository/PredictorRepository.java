package com.baldrichcorp.ml.repository;

import com.baldrichcorp.ml.domain.Predictor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Santiago Baldrich.
 */
public interface PredictorRepository extends CrudRepository<Predictor, Long> {
    @Override
    @Cacheable("predictors")
    Predictor findOne(Long id);
}
