package com.codetutr.controller.securitry;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignIn 
{
	@RequestMapping(value = "/sign-in", method = RequestMethod.GET)
	public ModelAndView SpringLogin() 
	{
		ModelAndView mv = new ModelAndView ("security/sign-in");
		return mv;
	}



}
