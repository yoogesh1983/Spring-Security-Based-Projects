package com.codetutr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetutr.services.ProfileService;

@Controller
public class DefaultPageController 
{
	@Autowired
	ProfileService profileservice;
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String homePage(ModelMap model, HttpServletRequest request, HttpServletResponse response) 
	{
		if(StringUtils.isBlank((CharSequence) request.getServletContext().getAttribute("initDatabase"))) {
			profileservice.initiateDatabase();
			request.getServletContext().setAttribute("initDatabase", "true");
		}
		return "security/sign-in";
	}

}
