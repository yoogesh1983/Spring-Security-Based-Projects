package com.codetutr.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.codetutr.model.Profile;

public class LemonUserDetailsService implements UserDetailsService 
{
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
