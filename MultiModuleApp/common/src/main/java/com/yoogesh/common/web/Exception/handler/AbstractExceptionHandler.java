package com.yoogesh.common.web.Exception.handler;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;

import com.yoogesh.common.web.Exception.FieldError;

public abstract class AbstractExceptionHandler<T extends Throwable> implements LemonExceptionHandler<T> {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	private String exceptionName;
	
	public AbstractExceptionHandler(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	@Override
	public String getExceptionName() {
		return exceptionName;
	}
	
	@Override
	public void putErrorDetails(Map<String, Object> errorAttributes, T ex) {
		
		String message = getMessage(ex);
		if (message != null)
			errorAttributes.put("message", message);
		
		Collection<FieldError> errors = getErrors(ex);
		if (errors != null)
			errorAttributes.put(ERRORS_KEY, errors);
		
		HttpStatus status = getStatus(ex);
		if (status != null) {
			errorAttributes.put(HTTP_STATUS_KEY, status);
			errorAttributes.put("status", status.value());
			errorAttributes.put("error", status.getReasonPhrase());
		}
	}

	protected String getMessage(T ex) {
		return null;
	}
	
	protected HttpStatus getStatus(T ex) {
		return null;
	}
	
	protected Collection<FieldError> getErrors(T ex) {
		return null;
	}
}
