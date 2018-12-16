package com.codetutr.controller.securitry;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccessDenied
{
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) 
	{
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    model.addAttribute("username", username);
		return "security/accessDenied";
	}

}
