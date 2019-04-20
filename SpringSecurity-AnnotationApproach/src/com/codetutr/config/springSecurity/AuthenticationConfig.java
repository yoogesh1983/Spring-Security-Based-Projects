package com.codetutr.config.springSecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

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
	
	
	/**
	 * Some times the the customer support person needs to login using the users email but without a password to know the users path behaviour.
	 * In such case, CS person cannot ask users password but can only ask username of the user. This switchUser functionality will
	 * help to CS person to login using customers username but without entering the password. In our case Only DBA has a right to do this.</p>
	 * 
	 */
	@Bean
	public SwitchUserFilter switchUserFilter() {
	    SwitchUserFilter filter = new SwitchUserFilter();
	    filter.setUserDetailsService(getUserDetailsService());
	    filter.setSwitchUserUrl("/impersonate_As_USER");
	    filter.setExitUserUrl("/switch_back_To_DBA");
	    filter.setTargetUrl("/my-account-user");
	    return filter;
	}
}
