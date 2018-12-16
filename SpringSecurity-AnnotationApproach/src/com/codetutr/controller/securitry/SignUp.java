package com.codetutr.controller.securitry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codetutr.model.Profile;
import com.codetutr.services.ProfileService;
import com.codetutr.validation.ProfileBeanValidator;

@Controller
public class SignUp 
{
	
	ProfileService profileservice = new ProfileService();
	
	@InitBinder
	public void initBinder (WebDataBinder binder)
	{
			binder.setDisallowedFields(new String[] {"uid"});
	} 
	
	
	@Autowired
	@Qualifier("profileBeanValidator")
	private ProfileBeanValidator profileBeanValidator;

	
	@RequestMapping(value = "/sign-up", method = RequestMethod.GET)
	public ModelAndView Signup(HttpServletRequest request, @ModelAttribute("profile") Profile profile) 
	{
		ModelAndView mv = new ModelAndView ("security/sign-up");
		return mv;
	}
	
	
	@RequestMapping(value ="/sign-up", method = RequestMethod.POST)
	public String submitform(HttpServletRequest request, @ModelAttribute ("profile") Profile profile,BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelMap model)
	{

		String redirectpath=null;
		
		profileBeanValidator.validate(profile, bindingResult);
		
		if(bindingResult.hasErrors())
		{
			// Forward errors back to the view
			redirectpath = "security/sign-up";
		}
		
		else if(profileservice.ismoreUsernameExists(profile.getUsername()))
		{
			bindingResult.reject("validation.username.already.exists");
		    redirectpath =  "security/sign-up";
		}
		else
		{
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			profile.setRole("ROLE_USER");
			profile.setPassword(passwordEncoder.encode(profile.getPassword()));
			
			profile.setRole("ROLE_USER");
		    Profile profile1 = profileservice.createProfile(profile);
		    request.setAttribute("profile1",  profile1);
		    model.addAttribute("profile",new Profile());   // to reset table
		    redirectpath =  "security/sign-up";
		}
		
		return redirectpath;
	}
	

}
