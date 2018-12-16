package com.yoogesh.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.yoogesh.mvc.config.ServletConfig;
import com.yoogesh.mvc.web.event.ContextEvent;
import com.yoogesh.persistence.config.PersistenceConfig;
import com.yoogesh.security.config.SecurityMvcConfig;
import com.yoogesh.service.config.ServiceConfig;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
@AutoConfigureBefore({WebMvcAutoConfiguration.class,ErrorMvcAutoConfiguration.class, SecurityAutoConfiguration.class})
public class SpringBootInitialSetup extends SpringBootServletInitializer {

	   private final static Object[] CONFIGS = {
			   									 SpringBootInitialSetup.class,
			   									 ServletConfig.class,
			   									 ServiceConfig.class,
			   									 PersistenceConfig.class,
			   									 SecurityMvcConfig.class
			   									 };

	   
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CONFIGS).initializers(new ContextEvent());
	}
	
    public static void main(final String... args) {
        final SpringApplication springApplication = new SpringApplication(CONFIGS);
        springApplication.addInitializers(new ContextEvent());
        springApplication.run(args);
    }

}
