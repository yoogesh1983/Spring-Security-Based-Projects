package com.codetutr.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codetutr.model.Profile;

@Service
public class LemonUserDetailsService implements UserDetailsService 
{
	@Autowired
	ProfileService profileservice;

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
