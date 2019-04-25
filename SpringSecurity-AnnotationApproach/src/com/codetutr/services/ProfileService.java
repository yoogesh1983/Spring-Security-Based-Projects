package com.codetutr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.codetutr.dao.IProfileDao;
import com.codetutr.model.Profile;

@Service
public class ProfileService {

	@Autowired
	//@Qualifier("mockProfileDaoImpl")
	@Qualifier("jdbcProfileDaoImpl")
	IProfileDao profiledao;

	// CREATE
	public Profile createProfile(Profile profile) {
		return profiledao.createProfile(profile);

	}

	// UPDATE
	public Profile updateProfile(Long guid, Profile profile) {
		return profiledao.updateProfile(guid, profile);
	}

	// DELETE
	public boolean deleteProfile(long guid) {
		return profiledao.deleteProfile(guid);
	}

	// READ ALL
	public List<Profile> getallProfile() {
		return profiledao.getAllProfile();

	}

	// READ SINGLE
	public Profile getProfile(Long guid) {
		return profiledao.getProfile(guid);
	}

	
	// READ SINGLE USING OTHER PARAMETER ( NOT PRIMARY KEY )
	public List<Profile> getProfileByName(String name) {
		return profiledao.getProfileByName(name);
	}

	// READ SINGLE USING OTHER PARAMETER ( NOT PRIMARY KEY )
	public Profile getProfileByUserName(String username) {
		return profiledao.getProfileByUsername(username);
	}


	// Check whether more than one username exists into the database
	public boolean ismoreUsernameExists(String username) {
		return profiledao.ismoreUsernameExists(username);
	}
}
