package com.yoogesh.common.web.Exception.handler;

import java.util.Map;

public interface LemonExceptionHandler<T extends Throwable> {
	
	final String ERRORS_KEY = "errors";
	final String HTTP_STATUS_KEY = "httpStatus";
	
	String getExceptionName();
	void putErrorDetails(Map<String, Object> errorAttributes, T ex);
}
