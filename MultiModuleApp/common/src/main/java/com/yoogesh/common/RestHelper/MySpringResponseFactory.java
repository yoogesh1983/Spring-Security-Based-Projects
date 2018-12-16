package com.yoogesh.common.RestHelper;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.yoogesh.common.RestHelper.Exception.ErrorType;
import com.yoogesh.common.Utility.HostnameHelper;
import com.yoogesh.common.web.request.RequestHelper;


public class MySpringResponseFactory {
	
	public static <T>MySpringResponse<T> getResponse(T data)
	{
		MySpringResponse<T> response = new MySpringResponse<>();
		response.setData(data);
		
		String transactionId = RequestHelper.getTransactionId();
		response.setTransactionId(transactionId);
		
		String hostname = getHostname();
		response.setHostName(hostname);
		
		return response;
	}
	
	
	public static <T>MySpringResponse<T> getResponse(T data, ArrayList<ErrorType> error)
	{
		MySpringResponse<T> response = new MySpringResponse<>();
		response.setData(data);
		
		String transactionId = RequestHelper.getTransactionId();
		response.setTransactionId(transactionId);
		
		String hostname = getHostname();
		response.setHostName(hostname);
		
		response.setErrors(error);
		
		return response;
	}
	
	
	private static String getHostname()
	{
		String hostname = HostnameHelper.getInstance().getLocalHostname();
		if(StringUtils.isBlank(hostname))
		{
			hostname = "N/A";
		}
		return hostname;
	}
	
}
