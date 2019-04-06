package com.codetutr.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyAccount 
{

	@RequestMapping(value = "/my-account-user", method = RequestMethod.GET)
	public String normalUserPage(ModelMap model) 
	{
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username", getPrincipal());
		return "security/my-account-user";
	}
	
	
	
	@RequestMapping(value = "/my-account-admin", method = RequestMethod.GET)
	public String adminPage(ModelMap model) 
	{
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username", getPrincipal());
		return "security/my-account-admin";
	}
	
	
	
	@RequestMapping(value = "/my-account-dba", method = RequestMethod.GET)
	public String databaseAdminpage(ModelMap model) 
	{
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username", getPrincipal());
		return "security/my-account-dba";
	}
	
	
	private String getPrincipal()
	{
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) 
		{
			userName = ((UserDetails)principal).getUsername();
		} 
		
		else 
		{
			userName = principal.toString();
		}
		return userName;
	}

}
