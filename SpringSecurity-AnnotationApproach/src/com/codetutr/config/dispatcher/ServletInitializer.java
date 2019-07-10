package com.codetutr.config.dispatcher;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.codetutr.config.database.AppConfig_Persistance;
import com.codetutr.config.logging.Log;
import com.codetutr.config.logging.LogFilter;
import com.codetutr.config.springMvc.AppConfig_Mvc;
import com.codetutr.config.springSecurity.AppConfig_Security;
import com.codetutr.properties.LogProperties;
import com.codetutr.properties.ProfileProperties;
import com.codetutr.utility.UtilityHelper;

/**
 * 
 * The DispatcherServlet class creates
 * {@link org.springframework.context.ApplicationContext}, which is a child of
 * the root ApplicationContext interface. Typically, Spring MVC-specific
 * components are initialized in the ApplicationContext interface of
 * DispatcherServlet, while the rest are loaded by
 * {@link org.springframework.web.context.ContextLoaderListener}. It is
 * important to know that beans in a child ApplicationContext (such as those
 * created by DispatcherServlet) can reference beans of the parent
 * ApplicationContext (such as those created by ContextLoaderListener). However,
 * the parent ApplicationContext interface cannot refer to beans of the child
 * ApplicationContext.
 * <p>
 *
 */
public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
				/**
				 * Initializes SpringSecurity (by parent ApplicationContext via
				 * ContextLoaderListener)
				 */
				AppConfig_Security.class,

				/**
				 * Initializes SpringMVC (by parent ApplicationContext via
				 * ContextLoaderListener)
				 */
				AppConfig_Mvc.class,

				/**
				 * Initializes the Persistence Configuration
				 */
				AppConfig_Persistance.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {
				/**
				 * Initializes SpringMVC (by Child ApplicationContext via DispatcherServlet)
				 * Some how it is not working here, so currently is is being used at
				 * contextLoaderListener level
				 * 
				 * AppConfig_Mvc.class
				 */
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new LogFilter()};
    }

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		
		/**
		 * Initiate Logging
		 */
		new Log().configure(servletContext.getRealPath("/WEB-INF"));
		
		/**
		 * This will set the current Environment Profile
		 */
		servletContext.setInitParameter("spring.profiles.active", ProfileProperties.getInstance().getProfile());
		Log.logInfo(this.getClass().getName(), UtilityHelper.getMethodName(new Object() {}), "Application started with Profile: " + ProfileProperties.getInstance().getProfile());
		
		/**
		 * This Listener needs to be registered to Listen for Max-session Users configured on AppConfig_Security.java
		 * The Servletcontainer will notify Spring Security (through this HttpSessionEventPublisher class)of session life cycle events
		 */
		servletContext.addListener(HttpSessionEventPublisher.class);
	}

}
