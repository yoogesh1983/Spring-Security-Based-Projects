package com.yoogesh.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yoogesh.common.Properties.CustomProperties;
import com.yoogesh.common.web.request.AuthenticationStateBean;

public class AuthenticationStateBeanHandlerInterceptor extends HandlerInterceptorAdapter
{
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)throws Exception
	{
	    AuthenticationStateBean authenticationStateBean = new AuthenticationStateBean();
	    authenticationStateBean.setAuthenticated(false);
	    
	    if (request.getSession(false) != null) 
	    {
	    	authenticationStateBean.setName((String) request.getSession().getAttribute(CustomProperties.getInstance().getName()));
	    	authenticationStateBean.setRole((String) request.getSession().getAttribute(CustomProperties.getInstance().getRole())); 
	    	authenticationStateBean.setProfileId((Long) request.getSession().getAttribute(CustomProperties.getInstance().getProfileId()));
	    	if(StringUtils.isNotBlank((String) request.getSession().getAttribute(CustomProperties.getInstance().getName()))){
	    		authenticationStateBean.setAuthenticated(true);
	    	}
	    }
	    request.setAttribute(CustomProperties.getInstance().getAuthenticationStateBean(), authenticationStateBean);
	}
}
