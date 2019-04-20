package com.codetutr.config.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import com.codetutr.handler.CustomSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(value={AuthenticationConfig.class, AuthorizationConfig.class})
public class AppConfig_Security extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public ProviderManager providerManager;
	
	@Autowired
	public AccessDecisionManager accessDecisionManager;
	
	@Autowired
	public CustomSuccessHandler customSuccessHandler;
	
	@Autowired
	public UserDetailsService userDetailsService;
	
    public AppConfig_Security(){
    	super();
    }

    
    /**
     * WebSecurity Configuration
     */
    @Override
	public void configure(WebSecurity web) throws Exception {
       disableFiltersOnStaticResources(web);
       enableDebugMode(web);
	}
	
    /**
     * HttpSecurity Configuration</br>
     * Difference between WebSecurity and HttpSecurity
     * {@link https://stackoverflow.com/questions/31995221/correct-use-of-websecurity-in-websecurityconfigureradapter/32056323}
     */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		authentication(http);
		authorization(http);
		logout(http);
		csrf(http);
		exceptionHandling(http);
		sessionManagement(http);
		enableRememberMeServices(http);
	}

	private void authentication(HttpSecurity http) throws Exception {
		http
		.authenticationProvider(providerManager.getProviders().get(0))
		.formLogin()
			.loginPage("/sign-in")
			.loginProcessingUrl("/do-sign-in")
			.successHandler(customSuccessHandler)
			.failureUrl("/sign-in?error=true")
			.usernameParameter("username")
			.passwordParameter("password");
	}

	private void authorization(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.accessDecisionManager(accessDecisionManager)
		  		.antMatchers("/", "/home").permitAll()
		  		.antMatchers("/sign-in", "/sign-up").permitAll()
		  		.antMatchers("/logout").authenticated()
		  		.antMatchers("/my-account-user").access("hasRole('USER')")
		  		.antMatchers("/my-account-admin").access("hasRole('ADMIN')")
		  		/**
		  		 * RememberMe token cannot access the DBA
		  		 */
		  		.antMatchers("/my-account-dba").access("fullyAuthenticated and hasRole('DBA')");
		
		handleUnknownRequests(http);
	}
	
	private void handleUnknownRequests(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.anyRequest()
				.authenticated()
					.accessDecisionManager(accessDecisionManager);
	}
	
	private void csrf(HttpSecurity http) throws Exception{
		http
		.csrf();
	}
	
	private void exceptionHandling(HttpSecurity http) throws Exception {
		http
		.exceptionHandling()
			
			/***********************************************
			 * What happen when an unannonomous user hit the myaccount page URL? for this we set authenticationEntryPoint
			 * 
			 * To prevent redirection to the login page
			 * when someone tries to access a restricted page
			 * 
			 * if you uncomment this, it will go to login page
			 **********************************************/
			//.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
			.accessDeniedPage("/Access_Denied");
	}
	
	private void sessionManagement(HttpSecurity http) throws Exception {
		http
		.sessionManagement()
		.sessionAuthenticationErrorUrl("/user-already-loggedIn-somewhere")
			.maximumSessions(10)
				.maxSessionsPreventsLogin(true)
				.expiredUrl("/user-session-time-out");
			
	}
	
	private void enableDebugMode(WebSecurity web) {
		web.debug(true);
	}

	private void disableFiltersOnStaticResources(WebSecurity web) {
		// XML Configuration => <http pattern="/static/**" security="none"/>
		web
		.ignoring()
			.antMatchers("/static/**");
	}
	
	private void enableRememberMeServices(HttpSecurity http) throws Exception {
		http
		.rememberMe()
			.key("ILoveNepal")
			.userDetailsService(userDetailsService);
	}
	
	private void logout(HttpSecurity http) throws Exception {
		http
		.logout()
			.logoutUrl("/logout")
			.deleteCookies("JSESSIONID", "remember-me");
	}
}
