package com.billwen.learning.imooc.imoocsecurity.annotation;

import com.billwen.learning.imooc.imoocsecurity.validation.PasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "Invalid Password";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
