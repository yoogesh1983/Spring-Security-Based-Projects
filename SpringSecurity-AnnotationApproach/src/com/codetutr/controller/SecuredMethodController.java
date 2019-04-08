package com.codetutr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
