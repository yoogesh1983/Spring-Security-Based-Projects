package com.codetutr.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyAccountController 
{
	@RequestMapping(value = "/my-account-user", method = RequestMethod.GET)
	public String normalUserPage(ModelMap model) {
		return prepareModelForView(model, "security/my-account-user");
	}
	
	@RequestMapping(value = "/my-account-admin", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {
		return prepareModelForView(model, "security/my-account-admin");
	}
	
	@RequestMapping(value = "/my-account-dba", method = RequestMethod.GET)
	public String databaseAdminpage(ModelMap model) {
		return prepareModelForView(model, "security/my-account-dba");
	}
	
	private String prepareModelForView(ModelMap model, String url)
	{
		String userName = null;
		boolean switchUserMode = false;
		
		SecurityContext context = SecurityContextHolder.getContext(); 

		for(GrantedAuthority role: context.getAuthentication().getAuthorities())
		{
			if(role instanceof SwitchUserGrantedAuthority) {
				switchUserMode = true;
				break;
			}
		}
		
		Object principal = context.getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		model.addAttribute("username", userName);
		model.addAttribute("switchUserMode", switchUserMode);
		
		return url;
	}

}
