package com.yoogesh.common.web.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import com.yoogesh.common.web.security.form.validation.RetypePasswordValidator;

/**
 * Annotation for retype password constraint
 * 
 * @author Sanjay Patel
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,
		 ElementType.METHOD,
		 ElementType.FIELD,
		 ElementType.CONSTRUCTOR,
		 ElementType.PARAMETER,
		 ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy=RetypePasswordValidator.class)
public @interface RetypePassword {
 
    String message() default "{com.naturalprogrammer.spring.different.passwords}";

    Class[] groups() default {};
    
    Class[] payload() default {};
}
