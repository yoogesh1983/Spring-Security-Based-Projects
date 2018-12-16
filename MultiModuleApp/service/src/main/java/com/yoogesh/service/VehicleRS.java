package com.yoogesh.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoogesh.common.LoggerHelper.Log;
import com.yoogesh.common.LoggerHelper.TrackingLogger;
import com.yoogesh.common.Properties.CustomProperties;
import com.yoogesh.common.RestHelper.MySpringResponse;
import com.yoogesh.common.RestHelper.MySpringResponseFactory;
import com.yoogesh.common.RestHelper.Exception.ExceptionCode;
import com.yoogesh.common.RestHelper.Exception.MyCountryException;
import com.yoogesh.common.web.entity.Vehicle;
import com.yoogesh.common.web.request.RequestHelper;
import com.yoogesh.service.config.CoreServiceClient;

@RestController
@RequestMapping(value="/Services")
public class VehicleRS 
{
	private static final String CLASS_NAME = VehicleRS.class.getName();
	
	@Autowired
	CoreServiceClient coreServiceClient;
	
	@RequestMapping(value ="/getAllVehicles", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public  MySpringResponse<ArrayList<Vehicle>> getAllVehicles(@RequestHeader(value="transactionIdentity") String headerValue)
	{
		reSetTransactionIdForRest(headerValue);
		
		String methodName = "getAllVehicles()";
		Log.entering(CLASS_NAME, methodName);
		
		MySpringResponse<ArrayList<Vehicle>> response = null;
		try
		{
			Log.logInfo(CLASS_NAME, methodName, "SVCREQUEST----");
		
			ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) coreServiceClient.getAllVehicles();
			
			checkWhetherMyJacksonMessageConverterWorking(vehicles);
			
			response = MySpringResponseFactory.getResponse(vehicles);
		
			Log.logInfo(CLASS_NAME, methodName, "SVCRESPONSE--: " + response);	
	      }
		
		catch (MyCountryException exception){
			
			String errMsg = "Error calling service" + exception.getMessage();
			Log.logWarn(CLASS_NAME, methodName, "--" + errMsg);
			logAdditionalInfo(headerValue);
			throw exception;
		 }
		
	    catch(Exception e){
	    	
			String errMsg = "Error calling service" + e.getMessage();
			Log.logWarn(CLASS_NAME, methodName, "--" + errMsg, e);
			logAdditionalInfo(headerValue);
			throw new MyCountryException(errMsg);
		}
			
		finally{
			Log.exiting(CLASS_NAME, methodName);
		}
		
		return response;
	}

	
	
	protected void logAdditionalInfo(String transactionId)
	{
		String methodName = "getOtp(String)";
		Log.entering(CLASS_NAME, methodName);
		
		
		// log some additional info about the profile
		String clientIp = "Unknown";
		String browserAgent = "Unknown";
		
		try
		{
			clientIp = RequestHelper.getCurrentRequestBean().getClientIpAddress();
			browserAgent = RequestHelper.getCurrentRequestBean().getBrowserAgent();
		}
		catch(Throwable tt)
		{ 
			// eat it!! 
		}
		
		String errMessage = "Info Dump for Exception:  [cientip = " + clientIp + "],[browserAgent = " + browserAgent + "]";
		Log.logDebug(CLASS_NAME, methodName, errMessage);
		
		Log.exiting(CLASS_NAME, methodName);	
	}
	
	
	
	private void checkWhetherMyJacksonMessageConverterWorking(ArrayList<Vehicle> vehicles) {
		for (Vehicle vehicle : vehicles) {
			vehicle.setTimeStamp(new Date());
		}
	}
	
	
	private void reSetTransactionIdForRest(String headerValue)
	{
	   /**
	    * We are resettig since another request is getting introduced here.this is good if the service layer is completely seperate app.
	    * But since this is in a same module, we are going to trick i.e.reset the transactionId. However we are not re-setting browsing
	    * agent and Ip address though.
	    */
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try 
		{
			map = mapper.readValue(headerValue, new TypeReference<Map<String, String>>(){});
		} 
		catch (IOException exception) 
		{
			throw new MyCountryException("Error occured during Unmarsalling Json String while creating Map", ExceptionCode.UNCAUGHT, exception);
		}
		
		TrackingLogger.setTransactionId((String) map.get(CustomProperties.getInstance().getTransactionId()));
	}

}
