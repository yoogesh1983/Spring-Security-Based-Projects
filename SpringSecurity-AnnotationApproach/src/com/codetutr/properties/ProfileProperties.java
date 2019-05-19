package com.codetutr.properties;

import com.codetutr.utility.AbstractProperties;

public class ProfileProperties extends AbstractProperties{

	public static final String PROPERTY_FILE_NAME = "profile/profile.properties";
	private static ProfileProperties instance;
	
	private final String profile;
	
	protected ProfileProperties() 
	{
		super(PROPERTY_FILE_NAME);
		
		profile = getString("spring.profiles.active", "Mock");
	}

	public static synchronized ProfileProperties getInstance()
	{
		if(instance == null)
		{
			instance = new ProfileProperties();
		}
		return instance;
	}
	
	public String getProfile() {
		return profile;
	}

}
