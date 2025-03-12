package com.interesting.common.validation.validator;




import com.interesting.common.validation.MinAndMaxWithDouble;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinAndMaxWithDoubleValidator implements ConstraintValidator<MinAndMaxWithDouble, Double> {

    /**
     * 最大值
     */
    private double max;
    /**
     * 最小值
     */
    private double min;

    @Override
    public void initialize(MinAndMaxWithDouble constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }


    @Override
    public boolean isValid(Double integer, ConstraintValidatorContext constraintValidatorContext) {
        if (null == integer) {
            return true;
        }
        if (integer <= max && integer >= min) {
            return true;
        }

        return false;
    }
}
