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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import com.codetutr.handler.CustomSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(value={AuthenticationConfig.class, AuthorizationConfig.class, SessionStrategyConfig.class, Oauth2Config.class})
public class AppConfig_Security extends WebSecurityConfigurerAdapter {
	
	private ProviderManager providerManager;
	private AccessDecisionManager accessDecisionManager;
	private CustomSuccessHandler customSuccessHandler;
	private UserDetailsService userDetailsService;
	private SwitchUserFilter switchUserFilter;
	private SessionRegistry sessionRegistry;
	private ClientRegistrationRepository clientRegistrationRepository;
	private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
	private OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver;
	private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository;
	
	@Autowired
	public AppConfig_Security(ProviderManager providerManager, AccessDecisionManager accessDecisionManager, CustomSuccessHandler customSuccessHandler, 
			UserDetailsService userDetailsService, SwitchUserFilter switchUserFilter,SessionRegistry sessionRegistry, 
			ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
			OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver, AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository) {
		this.providerManager = providerManager;
		this.accessDecisionManager = accessDecisionManager;
		this.customSuccessHandler= customSuccessHandler;
		this.userDetailsService = userDetailsService;
		this.switchUserFilter = switchUserFilter;
		this.sessionRegistry = sessionRegistry;
		this.clientRegistrationRepository = clientRegistrationRepository;
		this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
		this.oAuth2AuthorizationRequestResolver = oAuth2AuthorizationRequestResolver;
		this.authorizationRequestRepository = authorizationRequestRepository;
	}
		
    public AppConfig_Security(){
    	super();
    }

    
    /**
     * WebSecurity Configuration
     */
    @Override
	public void configure(WebSecurity web) throws Exception {
       disableFiltersOnStaticResources(web);
       //enableDebugMode(web);
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
		header(http);
		exceptionHandling(http);
		sessionManagement(http);
		rememberMe(http);
	}

	private void authentication(HttpSecurity http) throws Exception {
		formBasedLogin(http);
		oauth2Login(http);
	}

	private void authorization(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.accessDecisionManager(accessDecisionManager)
		  		.antMatchers("/", "/home").permitAll()
		  		.antMatchers("/sign-in", "/sign-up", "/oauth_login").permitAll()
		  		.antMatchers("/logout").authenticated()
		  		.antMatchers("/my-account-user").access("hasRole('USER')")
		  		.antMatchers("/my-account-admin").access("hasRole('ADMIN')")
		  		/**
		  		 * RememberMe token cannot access the DBA
		  		 */
		  		.antMatchers("/my-account-dba").access("isFullyAuthenticated() and hasRole('DBA')");
		
		configureSwitchUserFunctionality(http);
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
		
		//Session fixation
		http
			.sessionManagement()
				.sessionFixation()
					.migrateSession();
		
		//Per user per session. This will enable the ConcurrentSessionFilter
		http
			.sessionManagement()
				.maximumSessions(-1)
					.sessionRegistry(sessionRegistry)
						.maxSessionsPreventsLogin(true)
							.expiredUrl("/user-session-time-out");
		
		//Session authentication error
		http
			.sessionManagement()
				.sessionAuthenticationErrorUrl("/user-already-loggedIn-somewhere");
			
	}
	
	private void enableDebugMode(WebSecurity web) {
		/**
		 * This will enable the debugFilter
		 */
		web.debug(true);
	}

	private void disableFiltersOnStaticResources(WebSecurity web) {
		
		// XML Configuration => <http pattern="/static/**" security="none"/>
		web
		.ignoring()
			.antMatchers("/static/**");
	}
	
	private void rememberMe(HttpSecurity http) throws Exception {
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
	
	private void configureSwitchUserFunctionality(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(switchUserFilter, FilterSecurityInterceptor.class)
		.authorizeRequests()
			.accessDecisionManager(accessDecisionManager)
				.antMatchers("/switch_back_To_DBA").access("hasRole('DBA', 'ROLE_PREVIOUS_ADMINISTRATOR')");
	}
	
	private void formBasedLogin(HttpSecurity http) throws Exception {
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
	
	private void header(HttpSecurity http) throws Exception {
		http
			.headers();
	}

	
	private void oauth2Login(HttpSecurity http) throws Exception {
		initOauth2(http);
		OAuth2AuthorizationRequestRedirectFilterRequirementSetup(http);
		OAuth2LoginAuthenticationFilterRequirementSetup(http);
		AbstractAuthenticationProcessingFilterRequirementSetup(http);
	}
	
	private void initOauth2(HttpSecurity http) throws Exception {
		http
			.oauth2Login()
				/**
				 * This is where you save clientId, Client Secret and other required information provided from google and FaceBook while registering as a client
				 */
      			.clientRegistrationRepository(clientRegistrationRepository);
	}

	private void OAuth2AuthorizationRequestRedirectFilterRequirementSetup(HttpSecurity http) throws Exception {
		http
			.oauth2Login() 
				.authorizationEndpoint()
				    /**
				     * This will resolve OAuth2AuthorizationRequestRedirectFilterInterceptorUri and redirectUriTemplate defined at oauth2.properties
				     * This is where the URI for Leg1 call is created as an Object i.e. OAuth2AuthorizationRequest So that it can be used later for validation purpose
				     * OAuth2AuthorizationRequest is actually saved inside AuthorizationRequestRepository which is a member variable of OAuth2LoginAuthenticationFilter
				     */
					.authorizationRequestResolver(oAuth2AuthorizationRequestResolver);
	}
	
	private void OAuth2LoginAuthenticationFilterRequirementSetup(HttpSecurity http) throws Exception {
		http
			.oauth2Login()
			    /**
			     * This is where an information of Resource Owner after the successful Leg2 process is saved
			     */
	      		.authorizedClientService(oAuth2AuthorizedClientService)
	      		.authorizationEndpoint()
	      			.authorizationRequestRepository(authorizationRequestRepository);
	}
	
	private void AbstractAuthenticationProcessingFilterRequirementSetup(HttpSecurity http) throws Exception {
		http
			.oauth2Login()
      			.loginPage("/oauth_login")
      			.successHandler(customSuccessHandler)
      			.failureUrl("/oauth_login_Failure");
	}
	
}
