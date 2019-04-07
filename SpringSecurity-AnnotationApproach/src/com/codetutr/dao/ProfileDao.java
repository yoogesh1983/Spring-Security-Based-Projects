package com.codetutr.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.codetutr.model.Profile;

public class ProfileDao {
	
	public Map<Long, Profile> profileTable;
	
	public ProfileDao() {
		profileTable = Database.getProfileTable();
	}

	// Create
	public Profile ADD(Profile profile) {
		profile.setUid((long) profileTable.size() + 1);
		profileTable.put(profile.getUid(), profile);
		return profile;
	}

	
	// Update
	public Profile UPDATE(Profile profile) {
		profileTable.put(profile.getUid(), profile);
		return profile;
	}

	// Delete
	public boolean DELETE(long id) {
		profileTable.remove(id);
		return true;
	}

	// Read All
	public List<Profile> GETALL() {
		return new ArrayList<Profile>(profileTable.values());

	}

	// Read single using primary key
	public Profile GET_BY_ID(Long id) {
		return profileTable.get(id);
	}

	// Read single using other parameter (not primary key)
	public ArrayList<Profile> GET_BY_USERNAME(Object obj) {
		ArrayList<Profile> al = new ArrayList<Profile>();
		Set<Entry<Long, Profile>> set = profileTable.entrySet();
		Iterator<Entry<Long, Profile>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, Profile> me = i.next();
			if (me.getValue().getUsername().equals(obj)) {
				al.add(profileTable.get(me.getKey()));
			}
		}
		return al;
	}

	
	// Read single using other parameter (not primary key)
	public ArrayList<Profile> GET_BY_NAME(Object obj) {
		ArrayList<Profile> al = new ArrayList<Profile>();
		Set<Entry<Long, Profile>> set = profileTable.entrySet();
		Iterator<Entry<Long, Profile>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, Profile> me = i.next();
			if (me.getValue().getFirstName().equals(obj)) {

				al.add(profileTable.get(me.getKey()));
			}
		}
		return al;
	}

	public Set<Entry<Long, Profile>> getEntrySet() {
		return profileTable.entrySet();
	}
}
