package com.codetutr.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codetutr.dao.Event;
import com.codetutr.model.Profile;
import com.codetutr.services.SecuredMethodService;

@RestController
@RequestMapping(value = "/secure")
public class SecuredMethodController {

	@Autowired
	SecuredMethodService securedService;

	@RequestMapping(value = "/dba", method = RequestMethod.GET)
	public String secureDba() {
		return securedService.dbaOnly();
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String secureAdmin() {
		return securedService.adminOnly();
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String secureUser() {
		return securedService.userOnly();
	}
	
	@RequestMapping(value = "/authenticated", method = RequestMethod.GET)
	public String authenticatedUser() {
		return securedService.authenticatedOnly();
	}
	
	
	/**
	 * You may notice that Spring MVC can automatically obtain the Authentication object for us. This is because Spring Security maps our current 
	 * Authentication object to the HttpServletRequest.getPrincipal() method
	 */
	@RequestMapping(value = "/editPermission", method = RequestMethod.GET)
	public String userWithEditPermission(Authentication authentication) {
		Profile profile = new Profile();
		
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
				profile.setRole(next.getAuthority());
				break;
			}
		}
		
		return securedService.userWithEditPermission(profile);
	}
	
	@RequestMapping(value = "/events-preFilter", method = RequestMethod.GET)
	public List<Event> saveEvents() {
		List<Event> events = new LinkedList<>();
		events.add(new Event("dba@gmail.com", ""));
		events.add(new Event("admin@gmail.com", "dba@gmail.com"));
		events.add(new Event("user@gmail.com", "dba@gmail.com"));
		events.add(new Event("unknown@gmail.com", "admin@gmail.com"));
		List<Event> lists = securedService.saveEvents(events);
		return lists;
	}
	
	@RequestMapping(value = "/events-postFilter", method = RequestMethod.GET)
	public List<Event> getEvents() {
		 return securedService.getEvents();
	}
	
}
