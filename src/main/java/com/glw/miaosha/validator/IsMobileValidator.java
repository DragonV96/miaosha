package com.glw.miaosha.validator;

import com.glw.miaosha.util.ValidatorUtil;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Create by glw
 * 2019/5/10 0:03
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String>{
    private boolean required = false;

    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtil.isMobile(s);
        } else {
            if (StringUtils.isEmpty(s)) {
                return false;
            } else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
