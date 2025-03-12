package com.interesting.common.validation.validator;




import com.interesting.common.validation.CheckingMobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guoshicheng
 * @date 2021/03/15
 */
public class CheckingMobileValidator implements ConstraintValidator<CheckingMobile, String> {
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (null == string || string.trim().length() == 0) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.matches();
    }
}
