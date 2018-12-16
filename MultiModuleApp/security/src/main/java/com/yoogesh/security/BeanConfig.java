package com.yoogesh.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoogesh.common.entity.genericEntity.LemonAuditorAware;
import com.yoogesh.common.mail.MailSender;
import com.yoogesh.common.mail.MockMailSender;
import com.yoogesh.common.mail.SmtpMailSender;
import com.yoogesh.common.web.LemonProperties;
import com.yoogesh.common.web.Exception.LemonErrorAttributes;
import com.yoogesh.common.web.Exception.LemonErrorController;
import com.yoogesh.common.web.Exception.handler.LemonExceptionHandler;
import com.yoogesh.common.web.security.entity.AbstractUser;
import com.yoogesh.common.web.security.entity.AbstractUserRepository;
import com.yoogesh.common.web.security.form.validation.CaptchaValidator;
import com.yoogesh.common.web.security.form.validation.RetypePasswordValidator;
import com.yoogesh.common.web.security.form.validation.UniqueEmailValidator;
import com.yoogesh.security.filter.LemonCorsFilter;
import com.yoogesh.security.filter.LemonTokenAuthenticationFilter;
import com.yoogesh.security.principalExtractor.DefaultPrincipalExtractor;
import com.yoogesh.security.service.AbstractUserService;

@Configuration
public class BeanConfig  
{
	public static final String REMEMBER_ME_COOKIE = "rememberMe";
	public static final String REMEMBER_ME_PARAMETER = "rememberMe";
	public final static String JSON_PREFIX = ")]}',\n";
	
	@Bean
	public LemonProperties lemonProperties() {
		return new LemonProperties();
	}
	
	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	@ConditionalOnMissingBean(ErrorAttributes.class)
	public ErrorAttributes errorAttributes(List<LemonExceptionHandler<?>> handlers) {
		return new LemonErrorAttributes(handlers);
	}
	
	
	@Bean
	@ConditionalOnMissingBean(ErrorController.class)
	public ErrorController errorController(ErrorAttributes errorAttributes, ServerProperties serverProperties,
			List<ErrorViewResolver> errorViewResolvers) {
		return new LemonErrorController(errorAttributes, serverProperties, errorViewResolvers);
	}
	
	
//	@Bean
//	@ConditionalOnMissingBean(AuthenticationFailureHandler.class)
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//    	return new AuthenticationFailureHandler();
//    }
//	
	
	@Bean
	@ConditionalOnProperty(name = "lemon.enabled.json-prefix", matchIfMissing = true)
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
		converter.setJsonPrefix(JSON_PREFIX);
		return converter;
	}
	
	@Bean
	@ConditionalOnMissingBean(AuditorAware.class)
	public <U extends AbstractUser<U,ID>, ID extends Serializable>
	AuditorAware<U> auditorAware() {
		return new LemonAuditorAware<U, ID>();
	}
	
	@Bean
	@ConditionalOnMissingBean(MailSender.class)
	@ConditionalOnProperty(name="spring.mail.host", havingValue="foo", matchIfMissing=true)
	public MailSender mockMailSender() {
        return new MockMailSender();
	}
    
	@Bean
	@ConditionalOnMissingBean(MailSender.class)
	@ConditionalOnProperty("spring.mail.host")
	public MailSender smtpMailSender(JavaMailSender javaMailSender) {
		return new SmtpMailSender(javaMailSender);
	}
	
	@Bean
	@ConditionalOnMissingBean(PermissionEvaluator.class)
	public PermissionEvaluator permissionEvaluator() {
		return new MethodLevelSecurityConfig();
	}
	
	
	@Bean
	@ConditionalOnMissingBean(RememberMeServices.class)
	public RememberMeServices rememberMeServices(LemonProperties properties, UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices rememberMeServices =
        	new TokenBasedRememberMeServices
        		(properties.getRememberMeKey(), userDetailsService);
        rememberMeServices.setParameter(REMEMBER_ME_PARAMETER); // default is "remember-me" (in earlier spring security versions it was "_spring_security_remember_me")
        rememberMeServices.setCookieName(REMEMBER_ME_COOKIE);
        return rememberMeServices;       
    }
	

	@Bean
	@ConditionalOnProperty(name = "lemon.cors.allowed-origins")
	@ConditionalOnMissingBean(LemonCorsFilter.class)
	public LemonCorsFilter lemonCorsFilter(LemonProperties properties) {
		return new LemonCorsFilter(properties);
	}
	
	@Bean
	@ConditionalOnMissingBean(LemonTokenAuthenticationFilter.class)
	public <U extends AbstractUser<U, ID>, ID extends Serializable> LemonTokenAuthenticationFilter<U, ID> lemonTokenAuthenticationFilter(
			PasswordEncoder passwordEncoder, AbstractUserRepository<U, ID> userRepository,
			AbstractUserService<U, ID> lemonService) {
		return new LemonTokenAuthenticationFilter<U, ID>(passwordEncoder, userRepository, lemonService);
	}
	
	
	@Bean
	@ConditionalOnMissingBean(DefaultPrincipalExtractor.class)
	public <U extends AbstractUser<U, ?>> DefaultPrincipalExtractor<U> defaultPrincipalExtractor() {
		return new DefaultPrincipalExtractor<U>();
	}
	
	@Bean
	@ConditionalOnMissingBean(CaptchaValidator.class)
	public CaptchaValidator captchaValidator(LemonProperties properties, RestTemplateBuilder restTemplateBuilder) {
		return new CaptchaValidator(properties, restTemplateBuilder);
	}
	
	@Bean
	@ConditionalOnMissingBean(RetypePasswordValidator.class)
	public RetypePasswordValidator retypePasswordValidator() {      
		return new RetypePasswordValidator();
	}
	
	@Bean
	public UniqueEmailValidator uniqueEmailValidator(AbstractUserRepository<?, ?> userRepository) {     
		return new UniqueEmailValidator(userRepository);		
	}
	
}
