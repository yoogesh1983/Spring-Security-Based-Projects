package com.codetutr.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetutr.config.logging.Log;
import com.codetutr.services.ProfileService;

@Controller
public class DefaultPageController 
{
	private static final String CLASS_NAME = DefaultPageController.class.getName();
	
	@Autowired
	ProfileService profileservice;
	
	@PostConstruct
	public void init() {
		profileservice.initiateDatabase();
	}
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String homePage(ModelMap model, HttpServletRequest request, HttpServletResponse response) 
	{Log.logInfo(CLASS_NAME, "init()" ,"Initlamoarted" );
		return "security/sign-in";
	}

}
