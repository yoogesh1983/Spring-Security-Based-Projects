package com.codetutr.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetutr.entity.User;
import com.codetutr.utility.UtilityHelper;

@Repository
@Transactional(transactionManager = "hibernateTransactionManager", propagation = Propagation.REQUIRED)
@Profile("Hibernate")
public class TraditionalHibernateDaoImpl implements IUserDao{

	@Autowired
    private SessionFactory sessionFactory;
	
	/**
	 * HibernateTemplate currently is not being used since it is giving some error.SessionFactory is being is used instead.
	 */
	@Autowired
	private HibernateTemplate hibernateTemplate;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	@Override
	public void initiateDatabase() {
		
		User user = new User(null, "user@gmail.com",passwordEncoder.encode("1234"), "Yoogesh", "Sharma", true, null);
		user.setAuthorities(UtilityHelper.getUserAuthList(user));
		createUser(user);
		
		User admin = new User(null, "admin@gmail.com",passwordEncoder.encode("1234"), "Kristy", "Sharma", true, null);
		admin.setAuthorities(UtilityHelper.getAdminAuthList(admin));
		createUser(admin);
		
		User dba = new User(null, "dba@gmail.com",passwordEncoder.encode("1234"), "Sushila", "Sapkota", true, null);
		dba.setAuthorities(UtilityHelper.getDbaAuthList(dba));
		createUser(dba);
	}
	
	@Override
	public User createUser(User user) {
		Session s = sessionFactory.openSession();
		Transaction t = s.beginTransaction();
			s.saveOrUpdate(user);
		t.commit();
		s.close();
		return user;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(long guid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(Long guid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUserName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ismoreUsernameExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
