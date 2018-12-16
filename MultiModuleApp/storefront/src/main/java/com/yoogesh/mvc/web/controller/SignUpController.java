package com.yoogesh.mvc.web.controller;
 
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoogesh.common.LoggerHelper.Log;
import com.yoogesh.common.Properties.CustomProperties;
import com.yoogesh.common.web.entity.Profile;
import com.yoogesh.common.web.entity.Vehicle;
import com.yoogesh.service.config.CoreServiceClient;
 
@Controller
public class SignUpController 
{
	private static final String CLASS_NAME = SignUpController.class.getName();
	
	@Autowired
	private CoreServiceClient coreServiceClient;

	
	//Sign-up
	@RequestMapping(value="/Sign-Up.do",method=RequestMethod.GET)
	public ModelAndView renderNewProfileForm(HttpServletRequest request, HttpServletResponse response)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		Profile newProfile = new Profile();
		return new ModelAndView("security/signUp","profile",newProfile);
	} 
	
	
	@RequestMapping(value="/Sign-Up.do",method=RequestMethod.POST)
	public String newProfile(HttpServletRequest request, @ModelAttribute("profile") Profile profile)
	{
		String methodName = "newProfile(HttpServletRequest, Profile)";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		profile.setPassword(passwordEncoder.encode(profile.getPassword()));
		Set<String> roles = new HashSet<String>();
		roles.add("ROLE_USER");
		profile.setRoles(roles);
		
		try 
		{
			coreServiceClient.saveProfile(profile);
		} 
		catch (Exception exception) 
		{
			Log.logFatal(CLASS_NAME, methodName, "Saving profile into a database failed", exception);
		}
		
		return "redirect:/";
	}
	
	
	//Save vehicle Using ajax
	@RequestMapping(value="/save-vehicle-using-ajax",method=RequestMethod.GET)
	public String saveVehicleUsingAjax(HttpServletRequest request, HttpServletResponse response)
	{

		Vehicle vehicle = new Vehicle();
		request.setAttribute("vehicle", vehicle);
		return "security/saveVehicle";
	} 
	
	//Save vehicle Using ajax
	@RequestMapping(value="/save-vehicle-using-ajax",method=RequestMethod.POST)
	public String saveVehicleUsingAjaxPost(HttpServletRequest request, @ModelAttribute("vehicle") Vehicle vehicle)
	{
		vehicle.setName(vehicle.getName().toUpperCase());
		Long profileId = (Long) request.getSession().getAttribute(CustomProperties.getInstance().getProfileId());
		if(profileId != null){
			Profile profile = coreServiceClient.getProfileByProfileId(profileId);
			if(profile != null)
			{
				vehicle.setProfile(profile);
			}
		}
		coreServiceClient.saveVehicle(vehicle);
		return "redirect:/my-account-user";
	}
 
}