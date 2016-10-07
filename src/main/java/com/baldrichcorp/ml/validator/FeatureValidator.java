package com.baldrichcorp.ml.validator;

import com.baldrichcorp.ml.domain.Predictor;
import com.baldrichcorp.ml.feature.FlowerFeature;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

/**
 * @author Santiago Baldrich.
 */
public class FeatureValidator implements ConstraintValidator<FeaturesExist, Predictor> {
    @Override
    public void initialize(FeaturesExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(Predictor predictor, ConstraintValidatorContext context) {
        if (!isNull(predictor.getFeatures()) && !predictor.getFeatures().isEmpty()) {
            Set<String> validFeatures = Arrays.asList(FlowerFeature.values()).stream().map(FlowerFeature::name).collect(toSet());
            for (String feature : predictor.getFeatures())
                if (!validFeatures.contains(feature))
                    return false;
            return true;
        }
        return false;
    }
}
