package com.yoogesh.common.web.request;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HeaderRequestWrapper extends HttpServletRequestWrapper
{
	private Map<String, String> headerMap = new HashMap<String, String>();

	public HeaderRequestWrapper(HttpServletRequest httpServletrequest) 
	{
		super(httpServletrequest);
	}
	
    
    @Override
    public String getHeader(String name) 
    {
        String headerValue = super.getHeader(name);
        if (headerMap.containsKey(name)) 
        {
            headerValue = headerMap.get(name);
        }
        return headerValue;
    }
    


    @Override
    public Enumeration<String> getHeaderNames() 
    {
        List<String> names = Collections.list(super.getHeaderNames());
        for (String name : headerMap.keySet()) 
        {
            names.add(name);
        }
        return Collections.enumeration(names);
    }
    
    

    @Override
    public Enumeration<String> getHeaders(String name) 
    {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            values.add(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }

    
    
    // We call this method from Filter class to add header into a request object
    
    public void addHeader(String name, String value) 
    {
        headerMap.put(name, value);
    }
    
    
    public void resetHeader()
    {
    	headerMap = null;
    }
}