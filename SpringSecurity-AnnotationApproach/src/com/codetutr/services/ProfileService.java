package com.codetutr.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map.Entry;

import com.codetutr.dao.ProfileDao;
import com.codetutr.model.DatabaseConnection;
import com.codetutr.model.Profile;

public class ProfileService 
{
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	Map<Long, Profile> TableProfile = DatabaseConnection.getProfileTable();
	ProfileDao profiledao = new ProfileDao ();

	
	public ProfileService()
	
	{
		
//		TableProfile.put(1L, new Profile(1L, "syoogesh@gmail.com","Countrr1", "Yoogesh", "Sharma", "ROLE_ADMIN"));
//		TableProfile.put(2L, new Profile(2L, "yoogesh2002@yahoo.com","Countrr1", "Sushila", "Sapkota", "ROLE_USER"));
		
		TableProfile.put(1L, new Profile(1L, "syoogesh@gmail.com",passwordEncoder.encode("Countrr1"), "Yoogesh", "Sharma", "ROLE_USER"));
		TableProfile.put(2L, new Profile(2L, "kristy@gmail.com",passwordEncoder.encode("Countrr1"), "Kristy", "Sharma", "ROLE_ADMIN"));
		TableProfile.put(3L, new Profile(3L, "sushila@gmail.com",passwordEncoder.encode("Countrr1"), "Sushila", "Sapkota", "ROLE_DBA"));
    }
	
	
	

	//CREAT
	public Profile createProfile(Profile profile)
	{
	  return profiledao.ADD(profile);
		
	}
	
	
	//UPDATE
	public Profile updateProfile(Long guid, Profile profile)
	{
			
		boolean find = false;

		if ( guid <= 0)
		{
			System.out.println("*****PLEASE ENTER CORRECT PROFILE ID*******");
			return new Profile(null,null,null,null,null,null);
		}
		
		Set<Entry<Long, Profile>> set = TableProfile.entrySet();
		Iterator<Entry<Long, Profile>> i = set.iterator();
		while(i.hasNext())
		{
			Entry<Long, Profile> me = i.next();
			if( me.getKey() == guid)
			{
				find = true;
				break;
			}
		}
		
			if(!find)
			{
				System.out.println("*****COULD NOT FIND GUID IN A DATABASE*******");
				return new Profile(null,null,null,null,null,null);
			}
			else
			{
				return profiledao.UPDATE(new Profile(guid, profile.getUsername(), profile.getPassword(), profile.getFirstName(), profile.getLastName(), profile.getRole()));
		    }
	 }
		
	
	
	//DELETE
		 public boolean deleteProfile(long guid)
		 {
			 boolean find = false;

				if ( guid <= 0)
				{
					System.out.print("*****PLEASE ENTER CORRECT PROFILE ID*******");
					return false;
				}
				

				Set<Entry<Long, Profile>> set = TableProfile.entrySet();
				Iterator<Entry<Long, Profile>> i = set.iterator();
				while(i.hasNext())
				{
					Entry<Long, Profile> me = i.next();
					if( me.getKey() == guid)
					{
						find = true;
						break;
					}
				}
					if(!find)
					{
						System.out.println("*****COULD NOT FIND ID IN A DATABASE*******");
						return false;
					}
					else
					{
						return profiledao.DELETE(guid);
					}
		 }
		

		 
	
	//READ ALL
	public List<Profile> getallProfile ()
	{
		return profiledao.GETALL();
		
	}
	
	
	
	//READ SINGLE
	public Profile getProfile (Long guid)
	{
		boolean find = false;

		if ( guid <= 0)
		{
			System.out.println("*****PLEASE ENTER CORRECT STUDENT ID*******");
			return new Profile(null,null,null,null,null,null);
		}
		

		Set<Entry<Long, Profile>> set = TableProfile.entrySet();
		Iterator<Entry<Long, Profile>> i = set.iterator();
		while(i.hasNext())
		{
			Entry<Long, Profile> me = i.next();
			if( me.getKey() == guid)
			{
				find = true;
				break;
			}
		}
			if(!find)
			{
				System.out.println("*****COULD NOT FIND ID IN A DATABASE*******");
				return new Profile(null,null,null,null,null,null);
			}
			else
			{
				return profiledao.GET_BY_ID(guid);
			}
	}
	
	
	//READ SINGLE USING OTHER PARAMETER ( NOT PRIMARY KEY )
	
		public List<Profile> getProfileByName (String name)
		{
			
			return profiledao.GET_BY_NAME(name);
		  
		}
		
		
		
		//READ SINGLE USING OTHER PARAMETER ( NOT PRIMARY KEY )
		
		public Profile getProfileByUserName (String name)
		{
			ArrayList<Profile> al = profiledao.GET_BY_USERNAME(name);
			if(al != null && al.size() ==1)
			{
				return al.get(0); 
			}
			else
			{
		       return null;
			}    
		}

		
		
		
    // Check whether more than one username exists into the database
		
		public boolean ismoreUsernameExists(String username)
		{
			boolean found = false;
			ArrayList<Profile> al = profiledao.GET_BY_USERNAME(username); 
			if(!al.isEmpty())
			{
				found = true;
			}
			return found;
		}
}
