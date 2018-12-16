package com.yoogesh.common.web.Exception.handler;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.yoogesh.common.web.LemonUtils;
import com.yoogesh.common.web.Exception.FieldError;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class ConstraintViolationExceptionHandler extends AbstractExceptionHandler<ConstraintViolationException> {

	public ConstraintViolationExceptionHandler() {
		
		super(ConstraintViolationException.class.getSimpleName());
		log.info("Created");
	}
	
	@Override
	protected HttpStatus getStatus(ConstraintViolationException ex) {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}
	
	@Override
	protected Collection<FieldError> getErrors(ConstraintViolationException ex) {
		return FieldError.getErrors(ex.getConstraintViolations());
	}
	
	@Override
	protected String getMessage(ConstraintViolationException ex) {
		return LemonUtils.getMessage("com.yoogesh.common.error.validationError");
	}
}
