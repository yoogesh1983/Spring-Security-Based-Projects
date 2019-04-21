package com.codetutr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codetutr.model.Profile;
import com.codetutr.services.SecuredService;

@RestController
@RequestMapping(value = "/secure")
public class SecuredMethodController {

	@Autowired
	SecuredService securedService;

	@RequestMapping(value = "/dba", method = RequestMethod.GET)
	private String secureDba() {
		return securedService.dbaOnly();
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	private String secureAdmin() {
		return securedService.adminOnly();
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	private String secureUser() {
		return securedService.userOnly();
	}
	
	@RequestMapping(value = "/authenticated", method = RequestMethod.GET)
	private String authenticatedUser() {
		return securedService.authenticatedOnly();
	}
	
	@RequestMapping(value = "/editPermission", method = RequestMethod.GET)
	private String userWithEditPermission() {
		Profile profile = new Profile();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		profile.setUsername(username);
		
		for(GrantedAuthority next: authentication.getAuthorities()) {
			if(next.getAuthority().equals("ROLE_DBA")) {
				profile.setRole("ROLE_DBA");
				break;
			}
		}
		
		return securedService.userWithEditPermission(profile);
	}
}
