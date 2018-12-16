package com.yoogesh.common.web.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yoogesh.common.Properties.CustomProperties;

public final class RequestHelper
{
	private RequestHelper()
	{	
	}
		
	public static String getBrowserAgent(HttpServletRequest request)
	{
		String browserAgent = request.getHeader("User-Agent");
		browserAgent = StringUtils.trimToNull(browserAgent);
		return browserAgent;
	}
		
		
	public static String getClientIpAddress(HttpServletRequest request)
	{
		String clientIpAddress = null;
			
		String forwardedForIpAddressCollection = request.getHeader("X-Forwarded-For");
		
	    if((forwardedForIpAddressCollection = StringUtils.trimToNull(forwardedForIpAddressCollection)) == null)
	      {
	    	   clientIpAddress = request.getRemoteAddr();
	      }
	       
	    else
	      {
		      String[] forwardedForIpAddresses = forwardedForIpAddressCollection.split(",");
		       
		      clientIpAddress = forwardedForIpAddresses[0];
		       
		      clientIpAddress = StringUtils.substringBeforeLast(clientIpAddress, ":");
	      }
	       
	     clientIpAddress = StringUtils.trimToNull(clientIpAddress);
	     return clientIpAddress;
	}
	
	
	public static HttpServletRequest getCurrentRequest() 
	{
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	
	public static AuthenticationRequestBean getCurrentRequestBean(HttpServletRequest request) 
	{
		AuthenticationRequestBean AuthenticationRequestBean = null;
		 if(request != null){
			 AuthenticationRequestBean = (AuthenticationRequestBean) request.getAttribute(CustomProperties.getInstance().getAuthenticationRequestBean());
		 }
		return AuthenticationRequestBean;
	}
	
	public static AuthenticationRequestBean getCurrentRequestBean() 
	{
		AuthenticationRequestBean AuthenticationRequestBean = new AuthenticationRequestBean(null, null, null);
		HttpServletRequest request = getCurrentRequest();
		 if(request != null){
			 AuthenticationRequestBean = (AuthenticationRequestBean) request.getAttribute(CustomProperties.getInstance().getAuthenticationRequestBean());
		 }
		return AuthenticationRequestBean;
	}
	
	
	public static String getTransactionId()
	{
		return getCurrentRequestBean().getTransactionId();
	}
	
	public static String getChoppedTransactionId()
	{
		String transactionId = getCurrentRequestBean().getTransactionId();
		if(StringUtils.isNotBlank(transactionId)){
			transactionId = StringUtils.substringAfterLast(transactionId, "::").trim();
		}
		return transactionId;
	}

}
