package com.yoogesh.common.RestHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MyJacksonMessageConverter extends ObjectMapper 
{
	private static final long serialVersionUID = 1L;

	
	/**
	 * 
	 * While converting java.util.Date into Json, ObjectMapper has really very pain and hence it converts the java.util.Date
	 * into some Unix long value which we don't want. Instead we want to print in a timeStamp. so this converter will help
	 * us to print timestamp as "timeStamp":"2018-03-24T02:58:24.792+0000" instead of "timeStamp":1521859457214
	 * 
	 */
	public MyJacksonMessageConverter()
	{
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

}
