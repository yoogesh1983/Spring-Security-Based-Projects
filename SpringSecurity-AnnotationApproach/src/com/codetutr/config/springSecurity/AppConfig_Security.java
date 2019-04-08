package com.codetutr.config.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.codetutr.handler.CustomSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(value={AuthenticationConfig.class, AuthorizationConfig.class})
public class AppConfig_Security extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public CustomSuccessHandler customSuccessHandler;
	
	@Autowired
	public AccessDecisionManager accessDecisionManager;
	
    public AppConfig_Security(){
    	super();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		authorizeRequests(http);     // authorize requests
		handleUnknownRequests(http); // authorize requests
		login(http);                 // login configuration
		csrf(http);                  // csrf configuration
		exceptionHandling(http);     //exception handling
	}

	private void authorizeRequests(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.accessDecisionManager(accessDecisionManager)
		  			.antMatchers("/", "/home", "/static/**").permitAll()
		  			.antMatchers("/sign-in", "/sign-out" , "/sign-up").permitAll()
		  			.antMatchers("/my-account-user").access("hasRole('USER')")
		  			.antMatchers("/my-account-admin").access("hasRole('ADMIN')")
		  			.antMatchers("/my-account-dba").access("hasRole('ADMIN') and hasRole('DBA')");
	}
	
	private void handleUnknownRequests(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest()
					.authenticated()
						.accessDecisionManager(accessDecisionManager);
	}
	
	private void login(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.loginPage("/sign-in")
				.loginProcessingUrl("/do-sign-in")
				.successHandler(customSuccessHandler)
				.failureUrl("/sign-in?error=true")
				.usernameParameter("username")
				.passwordParameter("password");
	}
	
	
	private void csrf(HttpSecurity http) throws Exception{
		http.csrf();
	}
	
	private void exceptionHandling(HttpSecurity http) throws Exception {
		http
			.exceptionHandling()
			
			/***********************************************
			 * To prevent redirection to the login page
			 * when someone tries to access a restricted page
			 **********************************************/
			//.authenticationEntryPoint(new Http403ForbiddenEntryPoint());
			.accessDeniedPage("/Access_Denied");
	}
}
