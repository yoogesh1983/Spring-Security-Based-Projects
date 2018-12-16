package com.codetutr.model;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection 
{
	
	private static Map<Long, Profile> profileTable = new HashMap<Long, Profile>();
	
	

	public static Map<Long, Profile> getProfileTable() 
	{
		return profileTable;
	}





}
