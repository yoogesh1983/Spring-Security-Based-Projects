package com.codetutr.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.codetutr.model.Profile;

public class Database 
{
	private static Map<Long, Profile> profileTable = new HashMap<Long, Profile>();
	
	static {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		profileTable.put(1L, new Profile(1L, "user@gmail.com",passwordEncoder.encode("1234"), "Yoogesh", "Sharma", "ROLE_USER"));
		profileTable.put(2L, new Profile(2L, "admin@gmail.com",passwordEncoder.encode("1234"), "Kristy", "Sharma", "ROLE_ADMIN"));
		profileTable.put(3L, new Profile(3L, "dba@gmail.com",passwordEncoder.encode("1234"), "Sushila", "Sapkota", "ROLE_DBA"));
	}
	
	public static Map<Long, Profile> getProfileTable() 
	{
		return profileTable;
	}
}
