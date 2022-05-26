package com.billwen.learning.imooc.imoocsecurity.validation;

import com.billwen.learning.imooc.imoocsecurity.annotation.PasswordMatch;
import com.billwen.learning.imooc.imoocsecurity.domain.dto.UserDto;

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
