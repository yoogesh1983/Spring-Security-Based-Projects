package com.codetutr.config.springMvc;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class InternationalizationConfig implements WebMvcConfigurer
{
	@Bean 
	public LocaleResolver localeResolver()
	{
		//SessionLocaleResolver resolver = new SessionLocaleResolver();
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("en_US"));
		resolver.setCookieName("myLocaleCookie");
		resolver.setCookieMaxAge(4800);
		return resolver;
	}
	
	/*
	@Bean(name="sessionLocaleResolver")
	public SessionLocaleResolver sessionLocaleResolver()
	{
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		Locale defaultLocale = Locale.forLanguageTag("en_US");
		sessionLocaleResolver.setDefaultLocale(defaultLocale);
		return sessionLocaleResolver;
	}
	 */
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor()
	{
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("mylocale");
		return interceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(localeChangeInterceptor());
	}

}
