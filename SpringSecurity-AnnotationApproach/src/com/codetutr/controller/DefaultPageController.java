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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetutr.config.springSecurity.AppConfig_Security;
import com.codetutr.services.ProfileService;

@Controller
public class DefaultPageController 
{	
	@Autowired
	ProfileService profileservice;
	
	@Autowired
	@Qualifier("springSecurityFilterChain")
	private Filter springSecurityFilterChain;
	
	@Autowired
	private InMemoryClientRegistrationRepository clientRegistrationRepository;
	
	@PostConstruct
	public void init() {
		profileservice.initiateDatabase();
		getActiveFiltersFromFilterChain();
		getActiveOAuth2Providers(clientRegistrationRepository.iterator());
	}
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String homePage(ModelMap model, HttpServletRequest request, HttpServletResponse response) 
	{
		return "security/sign-in";
	}
	
	
	/**
	 * The enableDebugMode() must be commented out at {@link AppConfig_Security} to make this work <p>
	 */
	private void getActiveFiltersFromFilterChain() {
		System.out.println("Following filters are active in the application: ");
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
		System.out.println("Total OAuth2 Client Registered at Resource Provider : " + providers.size());
		for (ClientRegistration clientRegistration : providers) {
			System.out.println("Client Information : " + clientRegistration);
		}		
	}
}
