package com.yoogesh.LoggerHelper;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class StringAppender implements Appender
{
	private StringBuffer output = new StringBuffer();
	private String name;
	
	
	@Override
	public void doAppend(LoggingEvent event){
		output.append(event.getRenderedMessage()+ "\n");
	}
	
	@Override
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	
	@Override
	public String toString()
	{
		return output.toString();
	}
	
	
	
	//leave it as it is
	
	@Override
	public ErrorHandler getErrorHandler(){
	    return null;
	}

	@Override
	public Filter getFilter(){
		return null;
	}

	@Override
	public Layout getLayout(){
		return null;
	}


	@Override
	public boolean requiresLayout(){
		return false;
	}

	@Override
	public void setErrorHandler(ErrorHandler arg0){ }

	@Override
	public void setLayout(Layout arg0){ }
	
	@Override
	public void addFilter(Filter arg0){ }

	@Override
	public void clearFilters(){ }

	@Override
	public void close(){ }

}
