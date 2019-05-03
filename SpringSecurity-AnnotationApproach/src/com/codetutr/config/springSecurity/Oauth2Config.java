package com.codetutr.config.springSecurity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@PropertySource("classpath:oauth2/oauth2.properties")
public class Oauth2Config {
	
	private static List<String> clients = Arrays.asList("google", "facebook");
	
	@Autowired
	private Environment env;

	/**
	 *  This class will save all the information of the providers like google, facebook in a inMemoryMap<p>
	 */
	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
	    return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}
	
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
          .map(c -> getRegistration(c))
          .filter(registration -> registration != null)
          .collect(Collectors.toList());
        return new InMemoryClientRegistrationRepository(registrations);
    }

	private ClientRegistration getRegistration(String client) {
        String clientId = env.getProperty(client + ".client-id");
     
        if (clientId == null) {
            return null;
        }
     
        String clientSecret = env.getProperty(client + ".client-secret");
      
        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
            			.clientId(clientId)
            				.clientSecret(clientSecret)
            					.build();
        }
        if (client.equals("facebook")) {
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
            			.clientId(clientId)
            				.clientSecret(clientSecret)
            					.build();
        }
        return null;
    }
}
