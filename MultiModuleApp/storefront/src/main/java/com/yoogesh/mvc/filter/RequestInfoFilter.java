package com.yoogesh.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoogesh.common.LoggerHelper.Log;
import com.yoogesh.common.LoggerHelper.TrackingLogger;
import com.yoogesh.common.Properties.CustomProperties;
import com.yoogesh.common.web.request.AuthenticationRequestBean;
import com.yoogesh.common.web.request.HeaderRequestWrapper;
import com.yoogesh.common.web.request.RequestHelper;

public class RequestInfoFilter  extends AbstractBaseFilter implements Filter 
{
	private static final String CLASS_NAME = RequestInfoFilter.class.getName();
	
	public RequestInfoFilter()
	{
		super();
	}
	
	
	@Override
	public void doFilter(ServletRequest baseRequest, ServletResponse baseResponse, FilterChain filterChain) throws IOException, ServletException 
	{
		String methodName = "doFilter(ServletRequest, ServletResponse, FilterChain)";
		Log.entering(CLASS_NAME, methodName);
		
		HttpServletRequest request = (HttpServletRequest) baseRequest;
		HttpServletResponse response = (HttpServletResponse) baseResponse;
		
		HeaderRequestWrapper wrapper = setHeader(request);
		
		Log.logDebug(CLASS_NAME, methodName, "Client IP Address:[" + wrapper.getHeader("ClientIpAddress") + "]" + "   BrowserAgent:[" + wrapper.getHeader("BrowserAgent") + "]");
		
		filterChain.doFilter(wrapper, response);
		
		Log.exiting(CLASS_NAME, methodName);
	}


	private HeaderRequestWrapper setHeader(HttpServletRequest request) 
	{
		String methodName = "setHeader(HttpServletRequest)";
		Log.entering(CLASS_NAME, methodName);
		
		HeaderRequestWrapper wrapper = new HeaderRequestWrapper(request);
		wrapper.addHeader(CustomProperties.getInstance().getTransactionId(), TrackingLogger.getTransactionIdforCurrentRequest());
		wrapper.addHeader(CustomProperties.getInstance().getClientIpAddress(), RequestHelper.getClientIpAddress(request));
		wrapper.addHeader(CustomProperties.getInstance().getBrowserAgent(), RequestHelper.getBrowserAgent(request));
		
		wrapper.setAttribute(CustomProperties.getInstance().getAuthenticationRequestBean(), new AuthenticationRequestBean(wrapper.getHeader(CustomProperties.getInstance().getTransactionId()), wrapper.getHeader(CustomProperties.getInstance().getClientIpAddress()), wrapper.getHeader(CustomProperties.getInstance().getBrowserAgent())));
		
		Log.exiting(CLASS_NAME, methodName);
		return wrapper;
	}

}
