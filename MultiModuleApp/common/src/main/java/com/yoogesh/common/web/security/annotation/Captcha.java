package com.yoogesh.common.web.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import com.yoogesh.common.web.security.form.validation.CaptchaValidator;

/**
 * Captcha validation constraint annotation
 * 
 * Reference
 *   http://www.captaindebug.com/2011/07/writng-jsr-303-custom-constraint_26.html#.VIVhqjGUd8E
 *   http://www.captechconsulting.com/blog/jens-alm/versioned-validated-and-secured-rest-services-spring-40-2?_ga=1.71504976.2113127005.1416833905
 * 
 * @author Sanjay Patel
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,
		 ElementType.FIELD,
		 ElementType.CONSTRUCTOR,
		 ElementType.PARAMETER,
		 ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy=CaptchaValidator.class)
public @interface Captcha {
 
    String message() default "{com.naturalprogrammer.spring.wrong.captcha}";

    Class[] groups() default {};
    
    Class[] payload() default {};
}
