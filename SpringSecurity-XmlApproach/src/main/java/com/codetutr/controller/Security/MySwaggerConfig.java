package com.codetutr.controller.Security;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class MySwaggerConfig {

	  @Bean
	  public Docket swaggerCustomization() {
	      return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                	.apis(RequestHandlerSelectors.basePackage("com.codetutr.controller"))
	                	.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	                	.paths(PathSelectors.any()) //.paths(PathSelectors.ant("/api/*"))
	                .build()
	                .apiInfo(apiDetails());
	  }
	  
	  
	  private ApiInfo apiDetails() {
		  return new ApiInfo(
				  "Yoogesh Rest API", 
				  "<span style='color:red; font-size:15px; font-weight:bold'>A Rest API For Yoogesh</span>", 
				  "1.0", 
				  "Free to use", 
				  new springfox.documentation.service.Contact("Yoogesh Sharma", "https://www.google.com/", "y@gmail.com"), 
				  "Api lincence", 
				  "https://www.google.com/", 
				  Collections.emptyList());
	  }


    @Bean
    public WebMvcConfigurer webMvcConfigurer()
    {
        return new WebMvcConfigurerAdapter()
        {
            @Override
            public void addResourceHandlers( ResourceHandlerRegistry registry )
            {
                registry.addResourceHandler( "swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
                registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
            }
        };
    }

}
