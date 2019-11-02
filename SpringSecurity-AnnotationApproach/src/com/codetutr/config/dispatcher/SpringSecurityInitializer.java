package com.codetutr.config.dispatcher;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * This is done to register the {@link springSecurityFilterChain} with the war.Not suprisingly, Spring Security provides a base class 
 * {@link AbstractSecurityWebApplicationInitializer} that will ensure the {@link springSecurityFilterChain} gets registered for you. <p>
 */
@Order(1)
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	
	public SpringSecurityInitializer(){
		super();
	}
}
