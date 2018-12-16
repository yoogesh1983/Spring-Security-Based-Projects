package com.yoogesh.common.RestHelper;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.yoogesh.common.RestHelper.Exception.ErrorType;
import com.yoogesh.common.Utility.ShortString;


public class MySpringResponse<T> implements ShortString
{
	private T data;
	private String transactionId;
	private String hostName;
	private ArrayList<ErrorType> errors;
	
	
	public MySpringResponse()
	{ 
		
	}
	
	public T getData() 
	{
		return data;
	}
	
	public String getTransactionId() 
	{
		return transactionId;
	}

	public void setTransactionId(String transactionId) 
	{
		this.transactionId = transactionId;
	}

	public String getHostName() 
	{
		return hostName;
	}

	public void setHostName(String hostName) 
	{
		this.hostName = hostName;
	}

	public void setData(T data) 
	{
		this.data = data;
	}

	public ArrayList<ErrorType> getErrors() 
	{
		return errors;
	}

	public void setErrors(ArrayList<ErrorType> errors) 
	{
		this.errors = errors;
	}
	

	@Override
	public String toString() 
	{
		return ReflectionToStringBuilder.toString(this);
	}
	
	
	@Override
	public String toShortString() {
		String shortString = null;
		
		if (data == null) {
			shortString = toString();
		} else {
			ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
			builder = builder.setExcludeFieldNames("data");

			if (data instanceof ShortString) {
				// call the toShortString() method for the data object
				shortString = StringUtils.chop(builder.toString()) + ",data=" + ((ShortString) data).toShortString() + "]";
			}
			else {
				// only use the data object className and hashCode
				shortString = StringUtils.chop(builder.toString()) + ",data=" + data.getClass().getName() + "@" + data.hashCode() + "]";
			}
		}
		return shortString;
	}
	
}
