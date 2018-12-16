package com.yoogesh.mvc.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.yoogesh.mvc.filter.RequestInfoFilter;
import com.yoogesh.mvc.interceptor.AuthenticationStateBeanHandlerInterceptor;

@Configuration
@ComponentScan({"com.yoogesh.mvc.web","com.yoogesh.common.web"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
        super();
    }

    
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
    
	
	@Bean
	public SessionLocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		Locale defaultLocale = Locale.forLanguageTag("en_US");
		sessionLocaleResolver.setDefaultLocale(defaultLocale);
		return sessionLocaleResolver;
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	
	
	
	//Adding Interceptors
    @Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		// Register a handler intercepter to add the authenticate state bean to each request before passing control to the view
		AuthenticationStateBeanHandlerInterceptor authenticationSessionBeanHandlerInterceptor = new AuthenticationStateBeanHandlerInterceptor();
		registry.addInterceptor(authenticationSessionBeanHandlerInterceptor);
	}
    
   
    

	//Adding Filter
    @Bean
	public FilterRegistrationBean log4jFilterBean()
	{
    	FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    	RequestInfoFilter requestInfoFilter = new RequestInfoFilter(); 
	    filterRegistrationBean.setFilter(requestInfoFilter);
	        
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		    
	     filterRegistrationBean.setOrder(1);
	     return filterRegistrationBean;
	 }
    
  
}
