package com.codetutr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetutr.config.logging.Log;
import com.codetutr.dao.IUserDao;
import com.codetutr.entity.User;
import com.codetutr.utility.UtilityHelper;

@Service
public class UserService {

	@Autowired
	IUserDao userDao;
	
	// Initialization of database
	public void initiateDatabase() {
		
		Log.logInfo(this.getClass().getName(), UtilityHelper.getMethodName(new Object() {}), "Initialization of database started");
		userDao.initiateDatabase();		
		Log.logInfo(this.getClass().getName(), UtilityHelper.getMethodName(new Object() {}), "Initialization of database completed");
	}


	// CREATE
	public User createUser(User user) {
		return userDao.createUser(user);

	}

	// UPDATE
	public User updateUser(User user) {
		return userDao.updateUser(user);
	}

	// DELETE
	public boolean deleteUser(long guid) {
		return userDao.deleteUser(guid);
	}

	// READ ALL
	public List<User> getAllUsers() {
		return userDao.getAllUsers();

	}

	// READ SINGLE
	public User getUser(Long guid) {
		return userDao.getUser(guid);
	}

	
	// READ SINGLE USING OTHER PARAMETER ( NOT PRIMARY KEY )
	public List<User> getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	// READ SINGLE USING OTHER PARAMETER ( NOT PRIMARY KEY )
	public User getUserByUserName(String username) {
		return userDao.getUserByUserName(username);
	}


	// Check whether more than one username exists into the database
	public boolean ismoreUsernameExists(String username) {
		return userDao.ismoreUsernameExists(username);
	}
}
