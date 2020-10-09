package com.codetutr.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class swaggerFilter implements Filter{
	
	private static final String APPLICATION_JSON = "application/json";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String path = servletRequest.getRequestURI();
		SwaggerResponseWrapper responseWrapper;
		if (StringUtils.contains(path, "/v3/api-docs"))
		{
			responseWrapper = new SwaggerResponseWrapper((HttpServletResponse) response);
			chain.doFilter(request, responseWrapper);
			
			if (response.getContentType() != null && response.getContentType().contains(APPLICATION_JSON))
			{
				response.setContentType(APPLICATION_JSON);
				response.getWriter().write(responseWrapper.getCaptureAsString());
			}
		} else if (StringUtils.contains(path, "/swagger-ui/swagger-ui.css")) {

			responseWrapper = new SwaggerResponseWrapper((HttpServletResponse) response);
			chain.doFilter(request, responseWrapper);
			
			 String result = responseWrapper.getCaptureAsString();
			 String modifiedResponse = StringUtils
					.replace(result, "swagger-ui .scheme-container{margin", "swagger-ui .scheme-container{display:none;margin")
					.replace(".swagger-ui .info{margin:50px 0}", ".swagger-ui .info{margin-top:20px;margin-bottom:10px;}");
			response.getWriter().write(modifiedResponse);
		}
		else
		{
			chain.doFilter(request, response);
		}
		
	}
	
	@Override
	public void destroy() {
		
	}

}
