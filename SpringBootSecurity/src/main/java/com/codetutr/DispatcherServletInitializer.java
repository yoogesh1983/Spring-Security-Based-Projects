package com.codetutr;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;

import com.codetutr.config.event.ContextEvent;
import com.codetutr.config.springMvc.AppConfig_Mvc;
import com.codetutr.config.springSecurity.AppConfig_Security;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
@AutoConfigureBefore({WebMvcAutoConfiguration.class,ErrorMvcAutoConfiguration.class, SecurityAutoConfiguration.class})
public class DispatcherServletInitializer extends SpringBootServletInitializer
{
	private final static Class<?>[] CONFIGS = {
		DispatcherServletInitializer.class,
		MyDispatcher.class,
		AppConfig_Mvc.class,
		AppConfig_Security.class
	};

	public static void main(String... args) {
		final SpringApplication springApplication = new SpringApplication(CONFIGS);
        springApplication.addInitializers(new ContextEvent());
        springApplication.run(args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CONFIGS).initializers(new ContextEvent());
	}
}


@PropertySource({ 
"classpath:properties/persistence/persistence-${envTarget:local}.properties", 
"classpath:properties/custom/custom-${envTarget:local}.properties" 
})
class MyDispatcher
{
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}
	
	@Bean
	public ServletRegistrationBean<DispatcherServlet> dispatcherServletRegistration() {
		final ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<DispatcherServlet>(dispatcherServlet(), "/");
		
		final Map<String, String> params = new HashMap<String, String>();
		params.put("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		params.put("contextConfigLocation", "org.spring.sec2.spring");
		params.put("dispatchOptionsRequest", "true");
		registration.setInitParameters(params);
		
		registration.setLoadOnStartup(1);
		return registration;
	}
}
