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
		profileTable.put(1L, new Profile(1L, "syoogesh@gmail.com",passwordEncoder.encode("Countrr1"), "Yoogesh", "Sharma", "ROLE_USER"));
		profileTable.put(2L, new Profile(2L, "kristy@gmail.com",passwordEncoder.encode("Countrr1"), "Kristy", "Sharma", "ROLE_ADMIN"));
		profileTable.put(3L, new Profile(3L, "sushila@gmail.com",passwordEncoder.encode("Countrr1"), "Sushila", "Sapkota", "ROLE_DBA"));
	}
	
	public static Map<Long, Profile> getProfileTable() 
	{
		return profileTable;
	}
}
