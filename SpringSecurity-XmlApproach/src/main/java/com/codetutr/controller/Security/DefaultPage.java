package com.codetutr.controller.Security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultPage 
{
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView Defaultpage() 
	{
		ModelAndView mv = new ModelAndView ("security/sign-in");
		return mv;
	}
	

}
