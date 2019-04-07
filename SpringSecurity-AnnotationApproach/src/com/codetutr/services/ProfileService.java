package com.codetutr.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.codetutr.dao.ProfileDao;
import com.codetutr.model.Profile;

public class ProfileService 
{
	ProfileDao profiledao;

	public ProfileService()
	{
		profiledao = new ProfileDao ();
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
		
		Set<Entry<Long, Profile>> set = profiledao.getEntrySet();
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
				

				Set<Entry<Long, Profile>> set = profiledao.getEntrySet();
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
		

		Set<Entry<Long, Profile>> set = profiledao.getEntrySet();
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
