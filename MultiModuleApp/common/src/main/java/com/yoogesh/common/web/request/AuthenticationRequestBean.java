package com.yoogesh.common.web.request;

import org.springframework.web.context.annotation.RequestScope;

@RequestScope
public class AuthenticationRequestBean 
{
	private String transactionId;
	private String clientIpAddress;
	private String browserAgent;
	
	
	public AuthenticationRequestBean(String transactionId, String clientIpAddress, String browserAgent) 
	{
		this.transactionId = transactionId;
		this.clientIpAddress = clientIpAddress;
		this.browserAgent = browserAgent;
	}

	public String getTransactionId() 
	{
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) 
	{
		this.transactionId = transactionId;
	}
	
	public String getClientIpAddress()
	{
		return clientIpAddress;
	}
	
	public void setClientIpAddress(String clientIpAddress) 
	{
		this.clientIpAddress = clientIpAddress;
	}
	
	public String getBrowserAgent()
	{
		return browserAgent;
	}
	
	public void setBrowserAgent(String browserAgent) 
	{
		this.browserAgent = browserAgent;
	}

	@Override
	public String toString() {
		return "AuthenticationRequestBean [transactionId=" + transactionId + ", clientIpAddress=" + clientIpAddress
				+ ", browserAgent=" + browserAgent + "]";
	}
}
