package com.codetutr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.codetutr.entity.User;
import com.codetutr.utility.UtilityHelper;

@Profile("local")
@Repository
public class MockUserDaoImpl implements IUserDao {

	private static Map<Long, User> userTable = new HashMap<Long, User>();

	public MockUserDaoImpl() {
	}
	
	@Override
	public void initiateDatabase() {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userTable.put(1L, new User(1L, "user@gmail.com",passwordEncoder.encode("1234"), "Yoogesh", "Sharma", true, UtilityHelper.getUserAuthList()));
		userTable.put(2L, new User(2L, "admin@gmail.com",passwordEncoder.encode("1234"), "Kristy", "Sharma", true, UtilityHelper.getAdminAuthList()));
		userTable.put(3L, new User(3L, "dba@gmail.com",passwordEncoder.encode("1234"), "Sushila", "Sapkota", true, UtilityHelper.getDbaAuthList()));
	}

	@Override
	public User createUser(User user) {
		user.setUid((long) userTable.size() + 1);
		userTable.put(user.getUid(), user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		if (user.getUid() <= 0) {
			System.out.println("*****PLEASE ENTER CORRECT USER ID*******");
			return new User(null, null, null, null, null, false, null);
		}
		return UPDATE(new User(user.getUid(), user.getUsername(), user.getPassword(), user.getFirstName(),
				user.getLastName(), false, user.getAuthorities()));
	}

	private User UPDATE(User user) {
		userTable.put(user.getUid(), user);
		return user;
	}

	@Override
	public boolean deleteUser(long guid) {
		boolean find = false;

		if (guid <= 0) {
			System.out.print("*****PLEASE ENTER CORRECT USER ID*******");
			return false;
		}
		Set<Entry<Long, User>> set = getEntrySet();
		Iterator<Entry<Long, User>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, User> me = i.next();
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
		userTable.remove(id);
		return true;
	}

	@Override
	public List<User> getAllUsers() {
		return new ArrayList<User>(userTable.values());

	}

	@Override
	public User getUser(Long guid) {
		boolean find = false;

		if (guid <= 0) {
			System.out.println("*****PLEASE ENTER CORRECT STUDENT ID*******");
			return new User(null, null, null, null, null, false, null);
		}

		Set<Entry<Long, User>> set = getEntrySet();
		Iterator<Entry<Long, User>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, User> me = i.next();
			if (me.getKey() == guid) {
				find = true;
				break;
			}
		}
		if (!find) {
			System.out.println("*****COULD NOT FIND ID IN A DATABASE*******");
			return new User(null, null, null, null, null, false, null);
		} else {
			return GET_BY_ID(guid);
		}
	}

	public User GET_BY_ID(Long id) {
		return userTable.get(id);
	}

	@Override
	public User getUserByUserName(String username) {
		ArrayList<User> al = GET_BY_USERNAME(username);
		if (al != null && al.size() == 1) {
			return al.get(0);
		} else {
			return null;
		}
	}

	public ArrayList<User> GET_BY_USERNAME(Object obj) {
		ArrayList<User> al = new ArrayList<User>();
		Set<Entry<Long, User>> set = userTable.entrySet();
		Iterator<Entry<Long, User>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, User> me = i.next();
			if (me.getValue().getUsername().equals(obj)) {
				al.add(userTable.get(me.getKey()));
			}
		}
		return al;
	}

	// Read single using other parameter (not primary key)
	@Override
	public boolean ismoreUsernameExists(String username) {
		boolean found = false;
		ArrayList<User> al = GET_BY_USERNAME(username);
		if (!al.isEmpty()) {
			found = true;
		}
		return found;
	}

	@Override
	public List<User> getUserByName(String name) {
		ArrayList<User> al = new ArrayList<User>();
		Set<Entry<Long, User>> set = userTable.entrySet();
		Iterator<Entry<Long, User>> i = set.iterator();
		while (i.hasNext()) {
			Entry<Long, User> me = i.next();
			if (me.getValue().getFirstName().equals(name)) {

				al.add(userTable.get(me.getKey()));
			}
		}
		return al;
	}

	public Set<Entry<Long, User>> getEntrySet() {
		return userTable.entrySet();
	}
}
