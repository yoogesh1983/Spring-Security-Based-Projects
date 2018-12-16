package com.yoogesh.mvc.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yoogesh.common.web.entity.Vehicle;
import com.yoogesh.service.config.CoreServiceClient;

@RestController
public class VehicleController 
{
	@Autowired
	private CoreServiceClient coreServiceClient;
	
	
	//Insert Vehicle
	@RequestMapping(value="/vehicle",method=RequestMethod.POST)
	public Vehicle newVehicle(Vehicle vehicle)
	{
		Vehicle newVehicle = coreServiceClient.saveVehicle(vehicle);
		return newVehicle;
	}
	
	
	
	
	//Get All Vehicles
	@RequestMapping(value="/vehicle", method=RequestMethod.GET)	
	public List<Vehicle> getAllVehicles()
	{
		List<Vehicle> allVehicles = coreServiceClient.getAllVehicles_Rest();
		//List<Vehicle> allVehicles = coreServiceClient.getAllVehicles();
		return allVehicles;
	}
	
	
	
	//Get a Vehicle by Name
	@RequestMapping(value="/vehicle/name/{name}")
	public List<Vehicle> showVehicleByName(@PathVariable("name") String name)
	{
		List<Vehicle> vehicles = coreServiceClient.getVehicleByName(name);
		return vehicles;
	}
	
	
	//GetVehiclebyProfileId
	@RequestMapping(value="/vehicle/{id}", method=RequestMethod.GET)	
	public Vehicle getVehicleByVehicleId(@PathVariable Map<Object, Object>  myparams)
	{
		long id = 0L;
		try
		{
			id = Long.parseLong((String)myparams.get("id"));
		}
		catch(Exception e)
		{
			System.out.println("could not parse input variable to long." + "Exception is ===========> " + e);
			//throw new MyCountryException("could not parse input variable to long.", ExceptionCode.GENERAL, e);
		}
		
		Vehicle vehicle = coreServiceClient.getVehicleByVehicleId(id);
		return vehicle;
	}
	
	
	//UpdateVehicle
	@RequestMapping(value="/vehicle/UpdateVehicle", method=RequestMethod.POST)	
	public Vehicle UpdateVehicle(HttpServletRequest request, @ModelAttribute("vehicle") Vehicle vehicle)
	{
		Vehicle updatedVehicle = coreServiceClient.updateVehicle(vehicle);
		return updatedVehicle;
	}
	
	
	//DeleteVehicle
	@RequestMapping(value="/vehicle/DeleteVehicle/{id}", method=RequestMethod.GET)
	public Boolean DeleteVehicle(@PathVariable Map<Object, Object>  myparams)
	{
		long id = 0L;
		try
		{
			id = Long.parseLong((String)myparams.get("id"));
		}
		catch(Exception e)
		{
			System.out.println("could not parse input variable to long." + "Exception is ===========> " + e);
			//throw new MyCountryException("could not parse input variable to long.", ExceptionCode.GENERAL, e);
		}
		
		return coreServiceClient.deleteVehicle(id);
	}
	

}
