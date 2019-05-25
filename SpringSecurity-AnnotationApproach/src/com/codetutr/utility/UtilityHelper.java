package com.codetutr.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import com.codetutr.entity.Authority;
import com.codetutr.entity.User;

public class UtilityHelper {

	private UtilityHelper() {
		super();
	}

	public static List<Authority> getUserAuthList(User user) {
		Authority userAuth = new Authority();
		userAuth.setUser(user);
		userAuth.setRole("ROLE_USER");
		List<Authority> userAuthList = new ArrayList<>();
		userAuthList.add(userAuth);
		return userAuthList;
	}

	public static List<Authority> getAdminAuthList(User user) {
		Authority adminAuth = new Authority();
		adminAuth.setUser(user);
		adminAuth.setRole("ROLE_ADMIN");
		List<Authority> adminAuthList = new ArrayList<>();
		adminAuthList.add(adminAuth);
		return adminAuthList;
	}
	
	public static List<Authority> getDbaAuthList(User user) {
		Authority dbaAuth = new Authority();
		dbaAuth.setUser(user);
		dbaAuth.setRole("ROLE_DBA");
		List<Authority> dbaAuthList = new ArrayList<>();
		dbaAuthList.add(dbaAuth);
		return dbaAuthList;
	}

	
	public static Long generateLong() {
		long lowerLimit = 123456712L;
		long upperLimit = 234567892L;
		Long value = UUID.randomUUID().getMostSignificantBits() + (lowerLimit+((long)(new Random().nextDouble()*(upperLimit-lowerLimit))));
		if (value < 0) {
			return Math.abs(value);
		}
		return value;
	}
	
	public static List<Event> addEventsIntoList(List<Event> events) {
		events.add(new Event("dba@gmail.com", ""));
		events.add(new Event("admin@gmail.com", "dba@gmail.com"));
		events.add(new Event("user@gmail.com", "dba@gmail.com"));
		events.add(new Event("unknown@gmail.com", "admin@gmail.com"));
		return events;
	}
	
    public static Properties getAdditionalProperties() {
        final Properties hibernateProperties = new Properties();
        /**
         * It will not create a table (thanks to the "update") here if the security-schema.sql script is already run during a dataSource creation
         * If not, then it will create a table
         */
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.setProperty("hibernate.allow_update_outside_transaction", "true");
        return hibernateProperties;
    }
}
