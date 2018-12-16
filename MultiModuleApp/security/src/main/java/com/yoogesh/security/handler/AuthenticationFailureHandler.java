package com.yoogesh.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoogesh.common.web.security.entity.AbstractUser;
import com.yoogesh.security.service.AbstractUserService;

/**
 * Authentication success handler for sending the response
 * to the client after successful authentication. This would replace
 * the default handler of Spring Security
 * 
 * @author Sanjay Patel
 */
@Component
public class AuthenticationFailureHandler
	extends SimpleUrlAuthenticationFailureHandler {
	
	private static final Log log = LogFactory.getLog(AuthenticationFailureHandler.class);
	
    private ObjectMapper objectMapper;    
    private AbstractUserService<?,?> profileService;
    
	public AuthenticationFailureHandler(ObjectMapper objectMapper, AbstractUserService<?, ?> lemonService) {
		this.objectMapper = objectMapper;
		this.profileService = lemonService;
		
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
	}

	
	
}
