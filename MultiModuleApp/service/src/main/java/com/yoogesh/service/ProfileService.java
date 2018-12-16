package com.yoogesh.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yoogesh.common.web.entity.Profile;
import com.yoogesh.common.web.entity.Vehicle;
import com.yoogesh.persistence.dao.Hibernate.ProfileRepositoryOldWay;
import com.yoogesh.persistence.dao.SpringData.ProfileRepository;
import com.yoogesh.security.service.AbstractUserService;

/**
 * This means if we call this directly, then spring will throw exception.
 * A transaction must be open previously before this i.e. request must come via coreserviceClient.java
 */
@Transactional(propagation = Propagation.MANDATORY)
@Service
public class ProfileService extends AbstractUserService<Profile, Long> 
{
	@Autowired
	private ProfileRepositoryOldWay dao;
	
	@Autowired
	ProfileRepository springDataJpa;
	
	
	@Override
	public Profile newUser() {
		return new Profile();
	}

	@Override
	public Long parseId(String id) {
		return Long.valueOf(id);
	}
	
	
	public Profile saveProfile(Profile profile) throws Exception
	{
		Profile savedProfile = null;
		try
		{
			savedProfile = dao.save(profile);
		}
		catch(Exception e)
		{
			throw new Exception("Saving of the Profile failed.");
		}
		return savedProfile;
	}
	
	
	public Profile getProfileByProfileId(long id)
	{
		Profile profile = null;
		
		try
		{
			profile = dao.getProfileByProfileId(id);
		}
		catch(Exception e)
		{
			profile = null;
		}
		
		return profile;
	}

	
	public List<Profile> getAllProfiles() 
	{
		List<Profile>  profiles = new ArrayList<>();
		profiles = (List<Profile>) dao.findAllProfiles();
		return profiles;
	}
	
	
	//Update
	public Profile updateProfile(Profile profile)
	{
		Profile updatedProfile = null;
		try
		{
			updatedProfile = springDataJpa.save(profile);
		}
		catch(Exception e)
		{
			
		}
		return updatedProfile;
	}
	
	
	//Delete
	public boolean deleteProfile(Long id)
	{
		boolean deleted = false;
		
		try
		{
			springDataJpa.delete(id);
			deleted = true;
		}
		catch(Exception e)
		{
			deleted = false;
		}
		
		return deleted;
	}
	
	
	
	
	//Startup
	@Override
	protected Profile createAdminUser() 
	{
		Profile profile = super.createAdminUser();
		profile.setVehicles(addVehicles(profile));
		return profile;
		
	}

	private List<Vehicle> addVehicles(Profile profile) {
		
		profile.setFirstName("Yoogesh");
		profile.setLastName("Sharma");
		
		Vehicle vehicle = new Vehicle();
		vehicle.setName("Honda");
		vehicle.setChassisNumber("123");
		vehicle.setCurrentDriver("Yoogesh");
		vehicle.setLatLong("125646");
		vehicle.setOdometer(2564);
		vehicle.setProfile(profile);
		vehicle.setStatus("Active");
		vehicle.setTimeStamp(new Date());
		List<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(vehicle);
		return vehicles;
	}

}
