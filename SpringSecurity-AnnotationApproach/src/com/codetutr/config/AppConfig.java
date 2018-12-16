package com.codetutr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.codetutr")
@Import(value={InternationalizationConfig.class, ThemeConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter 
{
	
	@Bean
	public ViewResolver viewResolver() 
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
    
    
	@Bean(name="messageSource")
	public ResourceBundleMessageSource resourceBundleMessageSource()
	{
		
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		
		return messageSource;
	}
	
     
//	@Bean(name="sessionLocaleResolver")
//	public SessionLocaleResolver sessionLocaleResolver()
//	{
//		
//		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//		Locale defaultLocale = Locale.forLanguageTag("en_US");
//		sessionLocaleResolver.setDefaultLocale(defaultLocale);
//		
//		return sessionLocaleResolver;
//		
//	}
    
}