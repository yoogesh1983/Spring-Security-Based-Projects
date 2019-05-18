package com.codetutr.config.springSecurity;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class ResourceServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private ProviderManager providerManager;
 
    @Override
    public void configure(
      AuthorizationServerSecurityConfigurer oauthServer) 
      throws Exception {
        oauthServer
          .tokenKeyAccess("permitAll()")
          .checkTokenAccess("isAuthenticated()");
    }
 
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) 
      throws Exception {
        clients.jdbc(dataSource())
          .withClient("sampleClientId")
          .authorizedGrantTypes("implicit")
          .scopes("read")
          .autoApprove(true)
          .and()
          .withClient("clientIdPassword")
          .secret("secret")
          .authorizedGrantTypes(
            "password","authorization_code", "refresh_token")
          .scopes("read");
    }
 
    private DataSource dataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void configure(
      AuthorizationServerEndpointsConfigurer endpoints) 
      throws Exception {
  
        endpoints
          .tokenStore(tokenStore())
          .authenticationManager((AuthenticationManager) providerManager.getProviders().get(0));
    }
 
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource());
    }
}
