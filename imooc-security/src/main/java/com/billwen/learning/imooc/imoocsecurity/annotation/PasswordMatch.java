package com.billwen.learning.imooc.imoocsecurity.annotation;

import com.billwen.learning.imooc.imoocsecurity.validation.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    String message() default "Password doesn't match";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
