package com.codetutr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.codetutr.model.Profile;

@Repository
@org.springframework.context.annotation.Profile("local")
public class MockProfileDaoImpl implements IProfileDao {

	private static Map<Long, Profile> profileTable = new HashMap<Long, Profile>();

	public MockProfileDaoImpl() {
	}
	
	@Override
	public void initiateDatabase() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		profileTable.put(1L, new Profile(1L, "user@gmail.com",passwordEncoder.encode("1234"), "Yoogesh", "Sharma", "ROLE_USER", true));
		profileTable.put(2L, new Profile(2L, "admin@gmail.com",passwordEncoder.encode("1234"), "Kristy", "Sharma", "ROLE_ADMIN", true));
		profileTable.put(3L, new Profile(3L, "dba@gmail.com",passwordEncoder.encode("1234"), "Sushila", "Sapkota", "ROLE_DBA", true));
	}

	@Override
	public Profile createProfile(Profile profile) {
		profile.setUid((long) profileTable.size() + 1);
		profileTable.put(profile.getUid(), profile);
		return profile;
	}

	@Override
	public Profile updateProfile(Long guid, Profile profile) {
		boolean find = false;

		if (guid <= 0) {
			System.out.println("*****PLEASE ENTER CORRECT PROFILE ID*******");
			return new Profile(null, null, null, null, null, null, false);
		}

		Set<Entry<Long, Profile>> set = getEntrySet();
		Iterator<Entry<Long, Profile>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, Profile> me = i.next();
			if (me.getKey() == guid) {
				find = true;
				break;
			}
		}

		if (!find) {
			System.out.println("*****COULD NOT FIND GUID IN A DATABASE*******");
			return new Profile(null, null, null, null, null, null, false);
		} else {
			return UPDATE(new Profile(guid, profile.getUsername(), profile.getPassword(), profile.getFirstName(),
					profile.getLastName(), profile.getAuthority(), false));
		}
	}

	private Profile UPDATE(Profile profile) {
		profileTable.put(profile.getUid(), profile);
		return profile;
	}

	@Override
	public boolean deleteProfile(long guid) {
		boolean find = false;

		if (guid <= 0) {
			System.out.print("*****PLEASE ENTER CORRECT PROFILE ID*******");
			return false;
		}
		Set<Entry<Long, Profile>> set = getEntrySet();
		Iterator<Entry<Long, Profile>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, Profile> me = i.next();
			if (me.getKey() == guid) {
				find = true;
				break;
			}
		}
		if (!find) {
			System.out.println("*****COULD NOT FIND ID IN A DATABASE*******");
			return false;
		} else {
			return DELETE(guid);
		}
	}

	private boolean DELETE(long id) {
		profileTable.remove(id);
		return true;
	}

	@Override
	public List<Profile> getAllProfile() {
		return new ArrayList<Profile>(profileTable.values());

	}

	@Override
	public Profile getProfile(Long guid) {
		boolean find = false;

		if (guid <= 0) {
			System.out.println("*****PLEASE ENTER CORRECT STUDENT ID*******");
			return new Profile(null, null, null, null, null, null, false);
		}

		Set<Entry<Long, Profile>> set = getEntrySet();
		Iterator<Entry<Long, Profile>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, Profile> me = i.next();
			if (me.getKey() == guid) {
				find = true;
				break;
			}
		}
		if (!find) {
			System.out.println("*****COULD NOT FIND ID IN A DATABASE*******");
			return new Profile(null, null, null, null, null, null, false);
		} else {
			return GET_BY_ID(guid);
		}
	}

	public Profile GET_BY_ID(Long id) {
		return profileTable.get(id);
	}

	@Override
	public Profile getProfileByUsername(String username) {
		ArrayList<Profile> al = GET_BY_USERNAME(username);
		if (al != null && al.size() == 1) {
			return al.get(0);
		} else {
			return null;
		}
	}

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
	@Override
	public boolean ismoreUsernameExists(String username) {
		boolean found = false;
		ArrayList<Profile> al = GET_BY_USERNAME(username);
		if (!al.isEmpty()) {
			found = true;
		}
		return found;
	}

	@Override
	public List<Profile> getProfileByName(String name) {
		ArrayList<Profile> al = new ArrayList<Profile>();
		Set<Entry<Long, Profile>> set = profileTable.entrySet();
		Iterator<Entry<Long, Profile>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, Profile> me = i.next();
			if (me.getValue().getFirstName().equals(name)) {

				al.add(profileTable.get(me.getKey()));
			}
		}
		return al;
	}

	public Set<Entry<Long, Profile>> getEntrySet() {
		return profileTable.entrySet();
	}
}
