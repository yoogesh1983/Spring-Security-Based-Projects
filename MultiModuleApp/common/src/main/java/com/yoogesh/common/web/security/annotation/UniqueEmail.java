package com.yoogesh.common.web.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.yoogesh.common.web.security.entity.AbstractUser;
import com.yoogesh.common.web.security.form.validation.UniqueEmailValidator;

/**
 * Annotation for unique-email constraint,
 * ensuring that the given email id is not already
 * used by a user.  
 * 
 * @author Sanjay Patel
 */
@NotBlank(message = "{com.naturalprogrammer.spring.blank.email}")
@Size(min=AbstractUser.EMAIL_MIN, max=AbstractUser.EMAIL_MAX,
	message = "{com.naturalprogrammer.spring.invalid.email.size}")
@Email(message = "{com.naturalprogrammer.spring.invalid.email}")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy=UniqueEmailValidator.class)
public @interface UniqueEmail {
 
    String message() default "{com.naturalprogrammer.spring.duplicate.email}";

    Class[] groups() default {};
    
    Class[] payload() default {};
}
