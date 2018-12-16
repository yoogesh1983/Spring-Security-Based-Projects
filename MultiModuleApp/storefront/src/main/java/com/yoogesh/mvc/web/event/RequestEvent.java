package com.yoogesh.mvc.web.event;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.yoogesh.common.LoggerHelper.TrackingLogger;
import com.yoogesh.common.Utility.UUIDUtility;

@Component
@WebListener
public class RequestEvent implements ServletRequestListener
{
	@Override
	public void requestInitialized(ServletRequestEvent event) 
	{
		TrackingLogger.resetTransactionId();
        TrackingLogger.setTransactionId(((HttpServletRequest) event.getServletRequest()).getSession().getId() + "  ::  " + UUIDUtility.generateFullUUIDWithOutDashes());
	}
	
	
	
	@Override
	public void requestDestroyed(ServletRequestEvent event)
	{
		/**
		 * Some application server might re-use the thread. so it is better to trace it after using
		 */
		TrackingLogger.resetTransactionId();
	}

}
