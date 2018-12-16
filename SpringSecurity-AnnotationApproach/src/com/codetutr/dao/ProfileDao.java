package com.codetutr.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.codetutr.model.DatabaseConnection;
import com.codetutr.model.Profile;

public class ProfileDao
{
	public Map<Long, Profile> TableProfile = DatabaseConnection.getProfileTable();
	
	
	//CREAT
		public Profile ADD(Profile profile)
		{
			profile.setUid((long)TableProfile.size()+1);
		
			TableProfile.put(profile.getUid(), profile);
			return profile;
		}
		
		
		
		//UPDATE
		public Profile UPDATE (Profile profile)
		{
			TableProfile.put(profile.getUid(), profile);
			return profile;
		}
			
		
		
		
		//DELETE
		public boolean DELETE(long id)
		{
			TableProfile.remove(id);
		  return true;
		}
			
			 
			 
		
		
		//READ ALL
		public List<Profile> GETALL()
		{
			return new ArrayList<Profile>(TableProfile.values());
			
		}
		
		
		
		//READ SINGLE USING PRIMARY KEY
		public Profile GET_BY_ID (Long id)
		{
			
		  return TableProfile.get(id);
		}
		
		
		//READ SINGLE USING OTHER PARAMETER ( NOT PRIMARY KEY )
		
		public ArrayList<Profile> GET_BY_USERNAME (Object obj)
		{
			ArrayList<Profile> al = new ArrayList<Profile>();
            

			Set<Entry<Long, Profile>> set = TableProfile.entrySet();
			Iterator<Entry<Long, Profile>> i = set.iterator(); 
			while(i.hasNext())
			{
				Entry<Long, Profile> me = i.next(); 
				if( me.getValue().getUsername().equals(obj)) 
				{
					
					 al.add(TableProfile.get(me.getKey()));
				}
			}
					return al;
		}
		
		
		
		//READ SINGLE USING OTHER PARAMETER ( NOT PRIMARY KEY )
		
				public ArrayList<Profile> GET_BY_NAME (Object obj)
				{
					ArrayList<Profile> al = new ArrayList<Profile>();


					Set<Entry<Long, Profile>> set = TableProfile.entrySet();
					Iterator<Entry<Long, Profile>> i = set.iterator();
					while(i.hasNext())
					{
						Entry<Long, Profile> me = i.next();
						if( me.getValue().getFirstName().equals(obj))
						{
							 
							 al.add(TableProfile.get(me.getKey()));
						}
					}	
				return al;
				}
		

}
