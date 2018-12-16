package com.yoogesh.common.RestHelper;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.yoogesh.common.LoggerHelper.Log;
import com.yoogesh.common.RestHelper.Exception.MyCountryException;

public abstract class AbstractServiceClient
{
    private static final String CLASS_NAME = AbstractServiceClient.class.getName();
    
	public static String marshal(Object object) throws Exception
	{
		String methodName = "marshal(Object)";
        Log.entering(CLASS_NAME, methodName);
        
		ObjectMapper OBJECT_MAPPER = new ObjectMapper();
		String response = null;
		
		try
		{
			ObjectWriter objectWriter = OBJECT_MAPPER.writer();
			response = objectWriter.writeValueAsString(object);
		}
		catch(Exception e)
		{
			String errMsg = "Error with Object marshal to JSON:" + e.getMessage() + " - Java Object = " + object;
			Log.logWarn(CLASS_NAME, methodName, errMsg, e);
            throw new MyCountryException(errMsg);
		}
	    finally
	    {
	    	 Log.exiting(CLASS_NAME, methodName);
	    }
		
		return response;
	}

  
   public  <T>MySpringResponse<T> unmarshal(String input, Class<T> objectClass)
    {
        String methodName = "unmarshal(String, Class<T>)";
        Log.entering(CLASS_NAME, methodName);

       ObjectMapper mapper = new ObjectMapper();
       MySpringResponse<T> mcResponse = null;

       try
       {
            JavaType javaType = mapper.getTypeFactory().constructParametrizedType(MySpringResponse.class, MySpringResponse.class, objectClass);
            mcResponse = mapper.readValue(input, javaType);
        }
       catch(Exception e)
       {
            String errMsg = "Error with JSON unmarshal:" + e.getMessage() + " - Raw JSON response = " + input;
            Log.logWarn(CLASS_NAME, methodName, errMsg, e);
            throw new MyCountryException(errMsg);
         }
       finally
       {
    	   Log.exiting(CLASS_NAME, methodName);
       }
       
       return mcResponse;
    }
   
   
   public <T>MySpringResponse<ArrayList<T>> unmarshalList(String input, Class<T> objectClass)
   {
	   String methodName = "unmarshalList(String, Class<T>)";
	   Log.entering(CLASS_NAME, methodName);
	   
	   ObjectMapper mapper = new ObjectMapper();
	   MySpringResponse<ArrayList<T>> mcResponse = null;
	   
	   try
	   {
		   // unmarshall MySpringResponse only
		   JavaType javaType = mapper.getTypeFactory().constructParametrizedType(MySpringResponse.class, MySpringResponse.class, Object.class);
		   MySpringResponse<Object> response = mapper.readValue(input, javaType);
		   
		   // get the data attribute as JSON
		   String dataJson = mapper.writeValueAsString(response.getData());
		   
		   // unmarshall data as a list
		   JavaType listType = mapper.getTypeFactory().constructParametrizedType(ArrayList.class, ArrayList.class, objectClass);
		   ArrayList<T> listResponse = mapper.readValue(dataJson, listType);
		   
		   // map the full response
		   mcResponse = new MySpringResponse<ArrayList<T>>();
		   mcResponse.setTransactionId(response.getTransactionId());
		   mcResponse.setHostName(response.getHostName());
		   mcResponse.setErrors(response.getErrors());
		   mcResponse.setData(listResponse);
		     
	   }
	   
	   catch(Exception e)
	   {
		   String errMsg = "Error with JSON unmarshal:" + e.getMessage() + " -Raw JSON response = " + input;
		   Log.logWarn(CLASS_NAME, methodName, errMsg, e);
		   throw new MyCountryException(errMsg);
	   }
	   
	   finally
	   {
		   Log.exiting(CLASS_NAME, methodName);
	   }
	   
	   return mcResponse;
   }

}
