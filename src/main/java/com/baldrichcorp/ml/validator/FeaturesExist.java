package com.baldrichcorp.ml.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Santiago Baldrich.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FeatureValidator.class)
public @interface FeaturesExist {
    String message() default "A Model must declare at least one feature and all corresponding feature extractors must exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
