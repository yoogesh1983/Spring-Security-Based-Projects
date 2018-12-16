package com.codetutr.controller.securitry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.codetutr.model.DatabaseConnection;
import com.codetutr.model.Profile;
import com.codetutr.services.ProfileService;

@Configuration
public class Role implements UserDetailsService 
{
	Map<Long, Profile> TableProfile = DatabaseConnection.getProfileTable();
	ProfileService profileservice = new ProfileService ();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		if(username == null)
		{
			return null;
		}
		
		Profile profile = profileservice.getProfileByUserName(username);
		
		if(profile == null)
		{
			return null;  // Spring security will handle it
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profile.getRole()));
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(profile.getUsername(), profile.getPassword(), true, true, true, true, authorities);
		
		return userDetails;
	}

}
