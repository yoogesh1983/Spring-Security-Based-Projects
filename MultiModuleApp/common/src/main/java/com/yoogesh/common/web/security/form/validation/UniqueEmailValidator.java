package com.yoogesh.common.web.security.form.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yoogesh.common.web.security.annotation.UniqueEmail;
import com.yoogesh.common.web.security.entity.AbstractUserRepository;


/**
 * Validator for unique-email
 * 
 * @author Sanjay Patel
 */
public class UniqueEmailValidator
implements ConstraintValidator<UniqueEmail, String> {

	private static final Log log = LogFactory.getLog(UniqueEmailValidator.class);

	private AbstractUserRepository<?,?> profileRepository;

	public UniqueEmailValidator(AbstractUserRepository<?, ?> profileRepository) {
		
		this.profileRepository = profileRepository;
		log.info("Created");
	}


	@Override
	public void initialize(UniqueEmail constraintAnnotation) {
		log.debug("UniqueEmailValidator initialized");
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		
		log.debug("Validating whether email is unique: " + email);
		return !profileRepository.findByEmail(email).isPresent();
	}

}
