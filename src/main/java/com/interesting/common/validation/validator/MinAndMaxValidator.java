package com.interesting.common.validation.validator;




import com.interesting.common.validation.MinAndMax;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinAndMaxValidator implements ConstraintValidator<MinAndMax, Integer> {

    /**
     * 最大值
     */
    private int max;
    /**
     * 最小值
     */
    private int min;

    @Override
    public void initialize(MinAndMax constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (null == integer) {
            return true;
        }
        if (integer <= max && integer >= min) {
            return true;
        }

        return false;
    }
}
