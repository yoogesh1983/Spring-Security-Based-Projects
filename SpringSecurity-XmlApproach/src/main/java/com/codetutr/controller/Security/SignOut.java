package com.codetutr.controller.Security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignOut 
{
	
	@RequestMapping(value = "/sign-out", method = RequestMethod.GET)
	public ModelAndView logout()
	{
		ModelAndView mv = new ModelAndView ("security/sign-out");
		return mv;
	}

	

}
