package com.yoogesh.common.RestHelper;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.yoogesh.common.LoggerHelper.Log;


public class HttpAdapter extends AbstractHttpAdapter
{
	private static final String CLASS_NAME = HttpAdapter.class.getName();
	
	
	public <T> String post(String url, MySpringRequest<T> request, int readTimeout, BasicAuthenticationCredentials credentials)
	{
		String methodName = "post(String, MySpringRequest<T>, int, BasicAuthenticationCredentials)";
		Log.entering(CLASS_NAME, methodName);
		
		String response = executePost(url, request, readTimeout, credentials);
		
		Log.exiting(CLASS_NAME, methodName);
		return response;
	}
	
	
	public <T> String post(String url, String id,  MySpringRequest<T> request, int readTimeout, BasicAuthenticationCredentials credentials)
	{
		String methodName = "post(String, String, MySpringRequest<T>, int, BasicAuthenticationCredentials)";
		Log.entering(CLASS_NAME, methodName);
		
		String fullUrl = StringUtils.replace(url, "{id}", id);
		String response = executePost(fullUrl, request, readTimeout, credentials);
		
		Log.exiting(CLASS_NAME, methodName);
		return response;
	}
	
		
	public String get(String url, String id, int readTimeout, BasicAuthenticationCredentials credentials)
	{
		String methodName = "get(String, String, int, BasicAuthenticationCredentials)";
		Log.entering(CLASS_NAME, methodName);
		
		String fullUrl = StringUtils.replace(url, "{id}", id);
		String response = executeGet(fullUrl, readTimeout, credentials);
		
		Log.exiting(CLASS_NAME, methodName);
		return response;
	}
	
	
	public String get(String url, int readTimeout, BasicAuthenticationCredentials credentials)
	{
		String methodName = "get(String, int, BasicAuthenticationCredentials)";
		Log.entering(CLASS_NAME, methodName);
		
		String response = executeGet(url, readTimeout, credentials);
		
		Log.exiting(CLASS_NAME, methodName);
		return response;
	}

		
	
	public String get(String url, ArrayList<Pair<String, String>> nameValuePairs, int readTimeout, BasicAuthenticationCredentials credentials)
	{
		String methodName = "get(String, ArrayList<Pair<String, String>>, int, BasicAuthenticationCredentials)";
		Log.entering(CLASS_NAME, methodName);
		
		String fullUrl = url;
		
		for(Pair<String, String> nameValuePair : nameValuePairs)
		{
			/**
			 * have to uri encode the values ( not urlencode) in case they have special uri/url chars
			 * urlencode will change spaces to + instead of %20. + works goeed in querystring values, but since we are
			 * sending these up as part of the uri and not querystring need them to be %20 otherwise the jax-rs pathparam 
			 * annotation will not decode them
			 */
			String value = nameValuePair.getRight();
			fullUrl = StringUtils.replace(fullUrl, nameValuePair.getLeft(), value);
		}
		
		String response = executeGet(fullUrl, readTimeout, credentials);
		
		Log.exiting(CLASS_NAME, methodName);
		return response;
	}
	
}
