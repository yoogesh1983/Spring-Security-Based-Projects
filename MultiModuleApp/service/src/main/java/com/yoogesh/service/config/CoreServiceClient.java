package com.yoogesh.service.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yoogesh.common.LoggerHelper.Log;
import com.yoogesh.common.RestHelper.AbstractServiceClient;
import com.yoogesh.common.RestHelper.BasicAuthenticationCredentials;
import com.yoogesh.common.RestHelper.HttpAdapter;
import com.yoogesh.common.RestHelper.MySpringResponse;
import com.yoogesh.common.web.entity.Profile;
import com.yoogesh.common.web.entity.Vehicle;
import com.yoogesh.service.ProfileService;
import com.yoogesh.service.VehicleService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CoreServiceClient extends AbstractServiceClient
{
	private static final String CLASS_NAME = CoreServiceClient.class.getName();
	
    @Autowired private Environment env;
    @Autowired ProfileService profileService;
	@Autowired VehicleService vehicleService;
	
	private String serviceBaseUrl;
	private int serviceReadTimeout;
	private BasicAuthenticationCredentials serviceCredentials;
	
	public CoreServiceClient(){}
	
	@PostConstruct
	private void setup() throws Exception
	{
		serviceBaseUrl = env.getProperty("service.base.url");
		serviceReadTimeout = Integer.parseInt(env.getProperty("service.read.timeout"));
		serviceCredentials = new BasicAuthenticationCredentials();
		
		String serviceUsername = env.getProperty("service.username");
		String servicePassword = env.getProperty("service.password");
		
		serviceCredentials.setUsername(serviceUsername);
		serviceCredentials.setPassword(servicePassword);
	}


	/**
	 * Profile
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = {Exception.class,Throwable.class}, timeout=10, isolation = Isolation.SERIALIZABLE)public Profile saveProfile(Profile profile) throws Exception{return profileService.saveProfile(profile);}
	public Profile updateProfile(Profile profile){return profileService.updateProfile(profile);}
	public boolean deleteProfile(Long id){return profileService.deleteProfile(id);}
	@Transactional(readOnly=true)public List<Profile> getAllProfiles(){return profileService.getAllProfiles();}
	@Transactional(readOnly=true)public Profile getProfileByProfileId(long id){return profileService.getProfileByProfileId(id);}
	@Transactional(readOnly=true)public Profile getProfileByUserName(String username){return profileService.fetchUserByEmail(username);}

	
	/**
	 * Vehicle
	 */	
	public Vehicle saveVehicle(Vehicle vehicle){return vehicleService.saveVehicle(vehicle);}
	public Vehicle updateVehicle(Vehicle vehicle){return vehicleService.updateVehicle(vehicle);}
	public boolean deleteVehicle(Long id){return vehicleService.deleteVehicle(id);}
	@Transactional(readOnly=true)public List<Vehicle> getAllVehicles(){return vehicleService.getAllVehicles();}
	@Transactional(readOnly=true)public List<Vehicle> getVehicleByName(String name){return vehicleService.getVehicleByName(name);}
	@Transactional(readOnly=true)public Vehicle getVehicleByVehicleId(long id) {return vehicleService.getVehicleByVehicleId(id);}
	
	
	
	
	public List<Vehicle> getAllVehicles_Rest()
	{
		String methodName = "getAllVehicles_Rest()";
		Log.entering(CLASS_NAME, methodName);
	
		HttpAdapter httpAdapter = new HttpAdapter();
		String response = httpAdapter.get(serviceBaseUrl + env.getProperty("rest.get.All.Vehicles"), serviceReadTimeout, serviceCredentials);
		
		MySpringResponse<ArrayList<Vehicle>> mcResponse = unmarshalList(response, Vehicle.class);
		ArrayList<Vehicle> vehicles = mcResponse.getData();
		
		Log.exiting(CLASS_NAME, methodName);
		return vehicles;
	}
	
	
}
