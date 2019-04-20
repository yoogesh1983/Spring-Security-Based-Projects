package com.codetutr.config.springSecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.codetutr.services.LemonUserDetailsService;

public class AuthenticationConfig {

	/**
	 * This class is used to add {@link AuthenticationProvider} and you can add as many as providers you want since this is a List
	 */
	@Bean
	public ProviderManager getAuthenticationManager() {
		List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
		authenticationProviders.add(getDaoauthenticationProvider());
		return new ProviderManager(authenticationProviders);
	}

	@Bean
	public DaoAuthenticationProvider getDaoauthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(getUserDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
		
	@Bean
	public LemonUserDetailsService getUserDetailsService() {
		LemonUserDetailsService userDetailsService = new LemonUserDetailsService();
		return userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	/*
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder manager) throws Exception 
    {
    	manager.authenticationProvider(authenticationProvider());
    	manager.userDetailsService(getUserDetailsService());
    }
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN","DBA");
	}*/
}
