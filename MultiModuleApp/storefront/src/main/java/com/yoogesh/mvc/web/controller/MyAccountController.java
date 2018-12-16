package com.yoogesh.mvc.web.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yoogesh.common.Properties.CustomProperties;
import com.yoogesh.common.web.entity.Profile;
import com.yoogesh.service.config.CoreServiceClient;

@Controller
public class MyAccountController 
{
	@Autowired
	private CoreServiceClient coreServiceClient;
	
	 @Autowired
	 UserDetailsService userDetailsService;
	
	@RequestMapping(value = "/my-account-user", method = RequestMethod.GET)
	public String normalUserPage(ModelMap model, HttpServletRequest request) 
	{
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		setdata(model, getPrincipal(), request);
		return "security/myAccountUser";
	}

	@RequestMapping(value = "/my-account-admin", method = RequestMethod.GET)
	public String adminPage(ModelMap model, HttpServletRequest request) 
	{
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		setdata(model, getPrincipal(), request);
		return "security/myAccountAdmin";
	}
	
	
	
	@RequestMapping(value = "/my-account-dba", method = RequestMethod.GET)
	public String databaseAdminpage(ModelMap model, HttpServletRequest request) 
	{
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		setdata(model, getPrincipal(), request);
		return "security/MyAccountDba";
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

	
	private void setdata(ModelMap model, String username, HttpServletRequest request) 
	{
		Profile profile = null;
		  try
		  {
			  profile = coreServiceClient.getProfileByUserName(username);
			  model.addAttribute("username", username);
			  model.addAttribute("profile", profile);
			  model.addAttribute("vehicles", profile.getVehicles());
			  request.getSession().setAttribute(CustomProperties.getInstance().getName(), profile.getFirstName());
			  request.getSession().setAttribute(CustomProperties.getInstance().getProfileId(), profile.getId());
			  
			  UserDetails  userDetails = userDetailsService.loadUserByUsername(username);
			  Collection<? extends GrantedAuthority> ud = userDetails.getAuthorities();
			  request.getSession().setAttribute(CustomProperties.getInstance().getRole(), ud.iterator().next().toString());
			  
		  }
		  catch(Exception e)
		  {
			  
		  }
	}
	
}
