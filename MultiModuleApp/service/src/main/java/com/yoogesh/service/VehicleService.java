package com.yoogesh.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yoogesh.common.web.entity.Vehicle;
import com.yoogesh.persistence.dao.SpringData.VehicleRepository;

/**
 * This means if we call this directly, then spring will throw exception.
 * A transaction must be open previously before this i.e. request must come via coreserviceClient.java
 */
@Transactional(propagation = Propagation.MANDATORY)
@Service
public class VehicleService 
{
	@Autowired
	private VehicleRepository data;
	
	//Insert
	public Vehicle saveVehicle(Vehicle vehicle)
	{
		Vehicle savedVehicle = null;
		try
		{
			savedVehicle = data.save(vehicle);
		}
		catch(Exception e)
		{
			
		}
		return savedVehicle;
	}
	
	
	//Read
	public List<Vehicle> getVehicleByName(String name)
	{
		List<Vehicle> vehicles = null;
		
		try
		{
			vehicles = data.findByname(name);
		}
		catch(Exception e)
		{
			vehicles = null;
		}
		
		return vehicles;
	}
	
	
	public List<Vehicle> getAllVehicles()
	{
		List<Vehicle> allVehicles = new ArrayList<>();
		try
		{
			allVehicles = data.findAll();
		}
		catch(Exception e)
		{
			
		}
		
		return allVehicles;
	}

	
	public Vehicle getVehicleByVehicleId(long id) 
	{
		Vehicle vehicle = null;
		
		try
		{
			vehicle = data.findOne(id);
		}
		catch(Exception e)
		{
			
		}
		return vehicle;
	}
	
	//Update
	public Vehicle updateVehicle(Vehicle vehicle)
	{
		Vehicle updatedVehicle = null;
		try
		{
			updatedVehicle = data.save(vehicle);
		}
		catch(Exception e)
		{
			
		}
		return updatedVehicle;
	}
	
	
	//Delete 
	public boolean deleteVehicle(Long id)
	{
		boolean deleted = false;
		
		Vehicle vehicle = null;
		try
		{
			vehicle = data.findOne(id);
			
			if(vehicle == null)
			{
				return false;
			}
			else
			{
				vehicle.setProfile(null);
				data.saveAndFlush(vehicle);
			}
			
			data.delete(id);
			deleted = true;
		}
		catch(Exception e)
		{
			deleted = false;
		}
		
		return deleted;
	}
	
}
