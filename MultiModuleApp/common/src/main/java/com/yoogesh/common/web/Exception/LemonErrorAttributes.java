package com.yoogesh.common.web.Exception;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.context.request.RequestAttributes;

import com.yoogesh.common.LoggerHelper.Log;
import com.yoogesh.common.web.Exception.handler.LemonExceptionHandler;

public class LemonErrorAttributes extends DefaultErrorAttributes 
{
	private static final String CLASS_NAME = LemonErrorAttributes.class.getName();
	
	private final Map<String, LemonExceptionHandler<?>> handlers;
	
	
	public LemonErrorAttributes(List<LemonExceptionHandler<?>> handlers) 
	{
		String methodName = "constructor(List<LemonExceptionHandler<?>>) ";
        Log.entering(CLASS_NAME, methodName);	
        
        
		this.handlers = handlers.stream().collect(
	            Collectors.toMap(LemonExceptionHandler::getExceptionName,
	            		Function.identity(), (handler1, handler2) -> {
	            			
	            			return AnnotationAwareOrderComparator
	            					.INSTANCE.compare(handler1, handler2) < 0 ?
	            					handler1 : handler2;
	            		}));
		
		
		Log.logInfo(CLASS_NAME, methodName, "Created");
		Log.exiting(CLASS_NAME, methodName);
	}

	
	@Override
	public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,
			boolean includeStackTrace) {
			
		Map<String, Object> errorAttributes =
				super.getErrorAttributes(requestAttributes, includeStackTrace);
		
		addLemonErrorDetails(errorAttributes, requestAttributes);
		return errorAttributes;
	}

	
	@SuppressWarnings("unchecked")
	protected <T extends Throwable> void addLemonErrorDetails(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) 
	{
		String methodName = "addLemonErrorDetails(Map<String, Object>, RequestAttributes)";
		Log.entering(CLASS_NAME, methodName);
		
		Throwable ex = getError(requestAttributes);
		
		LemonExceptionHandler<T> handler = null;
		
        // find a handler for the exception
        // if no handler is found,
        // loop into for its cause (ex.getCause())

		while (ex != null) {
			
			handler = (LemonExceptionHandler<T>) handlers.get(ex.getClass().getSimpleName());
			
			if (handler != null) // found a handler
				break;
			
			ex = ex.getCause();			
		}
        
        if (handler != null) { // a handler is found
        	
        	Log.logWarn(CLASS_NAME, methodName, "Handling exception", ex);
        	
	        // Use the handler to add errors and update status
	        handler.putErrorDetails(errorAttributes, (T) ex);
        }
        
        Log.exiting(CLASS_NAME, methodName);
	}
}
