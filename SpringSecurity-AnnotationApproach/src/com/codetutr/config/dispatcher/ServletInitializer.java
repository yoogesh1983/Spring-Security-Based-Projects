package com.codetutr.config.dispatcher;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.codetutr.config.springMvc.AppConfig_Mvc;
import com.codetutr.config.springSecurity.AppConfig_Security;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] 
		{ 
			/**
			 * Initializes SpringMVC
			 */
			AppConfig_Mvc.class,
			
			/**
			 *  Initializes SpringSecurity
			 */
			AppConfig_Security.class
		};
	}
 
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
 
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
