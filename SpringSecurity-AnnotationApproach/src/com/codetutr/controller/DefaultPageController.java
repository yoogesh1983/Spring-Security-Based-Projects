package com.codetutr.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.debug.DebugFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetutr.config.springSecurity.AppConfig_Security;
import com.codetutr.properties.ProfileProperties;
import com.codetutr.services.UserService;

@Controller
public class DefaultPageController 
{	
	@Autowired
	UserService userService;
	
	@Autowired
	@Qualifier("springSecurityFilterChain")
	private Filter springSecurityFilterChain;
	
	@Autowired
	private InMemoryClientRegistrationRepository clientRegistrationRepository;
	
	@PostConstruct
	public void init() {
		userService.initiateDatabase();
		printFilterChain();
		getActiveOAuth2Providers(clientRegistrationRepository.iterator());
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String homePage(ModelMap model, HttpServletRequest request, HttpServletResponse response) 
	{
		return "security/sign-in";
	}
	
	
	private void printFilterChain() {
		if(springSecurityFilterChain instanceof DebugFilter) {
			System.out.println("Unable to print the filters in the filterchain since DebugFilter is currently Set to On. Filterchain: " + springSecurityFilterChain);
		}
		else {
			getActiveFiltersFromFilterChain();
		}
	}
	
	/**
	 * The enableDebugMode() must be commented out at {@link AppConfig_Security} to make this work <p>
	 */
	private void getActiveFiltersFromFilterChain() {
		System.out.println("With a " + ProfileProperties.getInstance().getProfile() +" environment, Following filters are active in the application: ");
	    FilterChainProxy filterChainProxy = (FilterChainProxy) springSecurityFilterChain;
	    List<SecurityFilterChain> list = filterChainProxy.getFilterChains();
	    list.stream()
	      .flatMap(chain -> chain.getFilters().stream()) 
	      .forEach(filter -> System.out.println(filter.getClass()));
	}
	
	private void getActiveOAuth2Providers(Iterator<ClientRegistration> iterator) {
		List<ClientRegistration> providers = new ArrayList<>();
		while(iterator.hasNext()){
			providers.add(iterator.next());
		}
		
		System.out.println("*********************************************************************************************");
		System.out.println("                       Currently Registred OAuth2 venders : " + providers.size());
		System.out.println("*********************************************************************************************\n");
		
		for (ClientRegistration clientRegistration : providers) {
			
			System.out.println("***********");
			System.out.println(clientRegistration.getClientName() + ":");
			System.out.println("*********** \n");
			
						System.out.println("     *******************");
						System.out.println("     client information:");
						System.out.println("     *******************");
						System.out.println("                          registrationId = " + clientRegistration.getRegistrationId());
						System.out.println("                          clientName = " + clientRegistration.getClientName());
						System.out.println("                          clientId = " + clientRegistration.getClientId());
						System.out.println("                          clientSecret = " + clientRegistration.getClientSecret());
						System.out.println("                          clientAuthenticationMethod = " + clientRegistration.getClientAuthenticationMethod().getValue());
						System.out.println("                          authorizationGrantType = " + clientRegistration.getAuthorizationGrantType().getValue());
						System.out.println("                          redirectUriTemplate = " + clientRegistration.getRedirectUriTemplate());
						System.out.println("                          redirectUriTemplate to be register at " + clientRegistration.getClientName() + "-client-API side as Authorized-redirect-URI = http://localhost:8080/SpringSecurity-AnnotationApproach/login/oauth2/code/" + clientRegistration.getRegistrationId());
						System.out.println("                          scopes = " + clientRegistration.getScopes());

						System.out.println("     *******************");
						System.out.println("     providerDetails:");
						System.out.println("     *******************");			
						System.out.println("                          AuthorizationUri = " + clientRegistration.getProviderDetails().getAuthorizationUri());			
						System.out.println("                          JwkSetUri = " + clientRegistration.getProviderDetails().getJwkSetUri());			
						System.out.println("                          TokenUri = " + clientRegistration.getProviderDetails().getTokenUri());			
						System.out.println("                          ConfigurationMetadata = " + clientRegistration.getProviderDetails().getConfigurationMetadata());	
						
						System.out.println("     *******************");
						System.out.println("     UserInfoEndpoint:");
						System.out.println("     *******************");			
						System.out.println("                          URI = " + clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri());			
						System.out.println("                          UserNameAttributeName = " + clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());							
						System.out.println("                          AuthenticationMethod = " + clientRegistration.getProviderDetails().getUserInfoEndpoint().getAuthenticationMethod().getValue());							
			
			System.out.println("\n\n");
		}
	}
}
