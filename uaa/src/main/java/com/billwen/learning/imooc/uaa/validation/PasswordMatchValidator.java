package com.billwen.learning.imooc.uaa.validation;

import com.billwen.learning.imooc.uaa.annotation.PasswordMatch;
import com.billwen.learning.imooc.uaa.domain.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserDto> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().matches(user.getMatchPassword());
    }
}