package com.codetutr.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import com.codetutr.controller.securitry.CustomSuccessHandler;
import com.codetutr.controller.securitry.Role;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomSuccessHandler customSuccessHandler;
	
    @Autowired
    Role role;
    
    private RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    
    public SpringSecurityConfig()
    {
    	roleHierarchy.setHierarchy("ROLE_DBA > ROLE_ADMIN \n ROLE_DBA > ROLE_USER \n ROLE_ADMIN > ROLE_USER");
    }
    
    public AffirmativeBased getAccessDecisionManager()
    {
    	DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
    	expressionHandler.setRoleHierarchy(roleHierarchy);
    	
    	WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
    	webExpressionVoter.setExpressionHandler(expressionHandler);
    	
    	List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
    	
    	voters.add(webExpressionVoter);
    	return new AffirmativeBased(voters);
    }
    
    
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception 
    {
        auth.userDetailsService(role);
        auth.authenticationProvider(authenticationProvider());
    }
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(role);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    
	
//	@Autowired
//	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
//		auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN","DBA");
//	}
    
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  
	  http.authorizeRequests()
	    .accessDecisionManager(getAccessDecisionManager())
	  	.antMatchers("/", "/home", "/static/**").permitAll()
	  	.antMatchers("/sign-in", "/sign-out" , "/sign-up").permitAll()
	  	.antMatchers("/my-account-user").access("hasRole('USER')")
	  	.antMatchers("/my-account-admin").access("hasRole('ADMIN')")
	  	.antMatchers("/my-account-dba").access("hasRole('ADMIN') and hasRole('DBA')")
	  	.and().formLogin().loginPage("/sign-in").loginProcessingUrl("/do-sign-in").successHandler(customSuccessHandler).failureUrl("/sign-in?error=true")
	  	.usernameParameter("username").passwordParameter("password")
	  	.and().csrf()
	  	.and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}
}
