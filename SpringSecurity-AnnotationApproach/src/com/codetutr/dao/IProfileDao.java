package com.codetutr.dao;

import java.util.List;

import com.codetutr.model.Profile;

public interface IProfileDao {

	Profile createProfile(Profile profile);

	// Update
	Profile updateProfile(Long guid, Profile profile);

	// Delete
	boolean deleteProfile(long guid);

	// Read All
	List<Profile> getAllProfile();

	// Read single using primary key
	Profile getProfile(Long guid);

	// Read single using other parameter (not primary key)
	Profile getProfileByUsername(String username);

	// Read single using other parameter (not primary key)
	boolean ismoreUsernameExists(String username);

	List<Profile> getProfileByName(String name);

	void initiateDatabase();

}