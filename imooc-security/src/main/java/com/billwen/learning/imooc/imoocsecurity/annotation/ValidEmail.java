package com.billwen.learning.imooc.imoocsecurity.annotation;

import com.billwen.learning.imooc.imoocsecurity.validation.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "{ValidEmail.email}";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
