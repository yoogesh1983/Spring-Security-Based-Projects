package com.yoogesh.common.RestHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoogesh.common.LoggerHelper.Log;
import com.yoogesh.common.Properties.CustomProperties;
import com.yoogesh.common.RestHelper.Exception.ExceptionCode;
import com.yoogesh.common.RestHelper.Exception.MyCountryException;
import com.yoogesh.common.web.request.AuthenticationRequestBean;
import com.yoogesh.common.web.request.RequestHelper;

public abstract class AbstractHttpAdapter 
{
	private static final String CLASS_NAME = AbstractHttpAdapter.class.getName();
	
	public <T> String executePost(String url, MySpringRequest<T> request, int readTimeout, BasicAuthenticationCredentials credentials)
	{
		String methodName = "executePost(String, MySpringRequest<T>, int, BasicAuthenticationCredentials)";
		Log.entering(CLASS_NAME, methodName);
		
		String response = null;
		try
		{ 
		   RestTemplate restTemplate = getRestTemplate(readTimeout);
		   HttpHeaders headers = createHeaders(credentials, RequestHelper.getCurrentRequestBean());
		   HttpEntity<MySpringRequest<T>> httpEntity = new HttpEntity<> (request, headers);
		   
		   Log.logInfo(CLASS_NAME, methodName, "Making a post request to : " + url + "--post body" + request);
		   
		   ResponseEntity<String> responseEntity  = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		   response = responseEntity.getBody();
		   
		   Log.logInfo(CLASS_NAME, methodName, "Response code: " + responseEntity.getStatusCode() + "--Response body:" + response);
		
		}
		
		catch(Exception e)
		{
			String errMsg = "--HttpAdapter error: " + e;
			Log.logWarn(CLASS_NAME, methodName, errMsg,e);
			throw new MyCountryException(e.getMessage());
		}
		
		finally
		{
			Log.exiting(CLASS_NAME, methodName);
		}
		
		return response;
		
	}
	
	public String executeGet(String url, int readTimeout, BasicAuthenticationCredentials credentials)
	{
		String methodName = "get(String, int, BasicAuthenticationCredentials)";
		Log.entering(CLASS_NAME, methodName);
		
		String response = null;
		
		try
		{   
			RestTemplate restTemplate = getRestTemplate(readTimeout);
			HttpHeaders headers = createHeaders(credentials, RequestHelper.getCurrentRequestBean());
			HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			
			Log.logInfo(CLASS_NAME, methodName, "Making a Get request to : " + url);
			
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
			response = responseEntity.getBody(); 
			
			Log.logInfo(CLASS_NAME, methodName, "Response code : " + responseEntity.getStatusCode() + "--Response body :" + response);
		}
		
		catch(Exception e)
		{
			String errMsg = " -- HttpAdapter error: " + e;
			Log.logWarn(CLASS_NAME, methodName,  errMsg, e);
			throw new MyCountryException(e.getMessage());
		}
		
		finally
		{
			Log.exiting(CLASS_NAME, methodName);
		}
		
		return response;
	}
	
	
	private HttpHeaders createHeaders(BasicAuthenticationCredentials credentials, AuthenticationRequestBean authenticationRequestBean)
	{
		String methodName = "createHeaders(BasicAuthenticationCredentials)";
		Log.entering(CLASS_NAME, methodName);

		HttpHeaders httpHeaders = new HttpHeaders();
		if(credentials !=null)
		{
			String authorizationHeaderValue = credentials.toAuthorizationHeaderValue();
			
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			
			/**
			 * These two will not take precedence since we have enabled query paramemeter to the url i.e. mediaType=json
			 * However, if you don't pass mediaType=json from url then you must remove this comment otherwise it only
			 * add xml to the header and it cannot convert to xml since @produces is only json
			 */
			//acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
			acceptableMediaTypes.add(MediaType.APPLICATION_XML);
            httpHeaders.setAccept(acceptableMediaTypes);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			httpHeaders.add("Authorization", authorizationHeaderValue);
			passCurrentRequestThread(httpHeaders, authenticationRequestBean);
		}
		
		Log.exiting(CLASS_NAME, methodName);
		return httpHeaders;
	}

	private void passCurrentRequestThread(HttpHeaders httpHeaders,AuthenticationRequestBean authenticationRequestBean) 
	{
		String methodName = "passCurrentRequestThread(HttpHeaders, AuthenticationRequestBean)";
		Log.entering(CLASS_NAME, methodName);
		
		String params = StringUtils.EMPTY;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CustomProperties.getInstance().getTransactionId(), authenticationRequestBean.getTransactionId());
		map.put(CustomProperties.getInstance().getClientIpAddress(), authenticationRequestBean.getClientIpAddress());
		map.put(CustomProperties.getInstance().getBrowserAgent(), authenticationRequestBean.getBrowserAgent());
		
		ObjectMapper mapper = new ObjectMapper();
		try 
		{
			params = mapper.writeValueAsString(map);
		} 
		catch (JsonProcessingException exception) 
		{
			throw new MyCountryException("Error occured during parsing Map while creating Json string", ExceptionCode.GENERAL, exception);
		}
		
		httpHeaders.add(CustomProperties.getInstance().getTransactionHeaderName(), params);
		
		Log.exiting(CLASS_NAME, methodName);
	}

	
	private RestTemplate getRestTemplate(int readTimeout)
	{
		String methodName = "getRestTemplate()";
		Log.entering(CLASS_NAME, methodName);
		
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(readTimeout);
		requestFactory.setConnectTimeout(5000);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		
		Log.exiting(CLASS_NAME, methodName);
		return restTemplate;
	}


}
