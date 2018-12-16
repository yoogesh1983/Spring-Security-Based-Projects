package com.yoogesh.common.web.Exception;

import static com.yoogesh.common.web.Exception.handler.LemonExceptionHandler.HTTP_STATUS_KEY;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.yoogesh.common.LoggerHelper.Log;

public class LemonErrorController extends BasicErrorController 
{
	private static final String CLASS_NAME = LemonErrorController.class.getName();
	
    public LemonErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) 
    {
    	super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    	Log.logInfo(CLASS_NAME, "constructor(ErrorAttributes, ServerProperties, List<ErrorViewResolver>)", "Created");
	
	}

	@Override	
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) 
	{
		String methodName = "error(HttpServletRequest) ";
		Log.entering(CLASS_NAME, methodName);
		
		Map<String, Object> body = getErrorAttributes(request,
				isIncludeStackTrace(request, MediaType.ALL));
		
		HttpStatus status =	(HttpStatus) body.get(HTTP_STATUS_KEY);
		
		if (status == null)
			status = getStatus(request);
		else
			body.remove(HTTP_STATUS_KEY);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Log.exiting(CLASS_NAME, methodName);
		return new ResponseEntity<Map<String, Object>>(body, headers, status);
	}
}
