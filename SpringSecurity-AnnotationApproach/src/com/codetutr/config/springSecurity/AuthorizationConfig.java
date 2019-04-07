package com.codetutr.config.springSecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

public class AuthorizationConfig {

	@Bean
	public RoleHierarchy getRoleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_DBA > ROLE_ADMIN \n ROLE_DBA > ROLE_USER \n ROLE_ADMIN > ROLE_USER");
		return roleHierarchy;
	}
	
	@Bean
    public AccessDecisionManager getAccessDecisionManager()
    {
    	DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
    	expressionHandler.setRoleHierarchy(getRoleHierarchy());
    	
    	WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
    	webExpressionVoter.setExpressionHandler(expressionHandler);
    	
    	List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
    	
    	voters.add(webExpressionVoter);
    	return new AffirmativeBased(voters);
    }

}
