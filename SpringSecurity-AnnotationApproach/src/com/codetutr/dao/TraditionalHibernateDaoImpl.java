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
	 * Hibernate now conforms with the JPA specification to not allow flushing updates outside of a transaction boundary from Hibernate ORM 5.2. To restore 5.1 
	 * behavior, allowing flush operations outside of a transaction boundary, set [hibernate.allow_update_outside_transaction=true] in additional Properties.
	 * Otherwise you will get {@link no transaction is in progress} exception.
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
			Long key = (Long) s.save(user);
			User savedUser = s.get(User.class, key);
		t.commit();
		s.close();
		return savedUser;
	}

	@Override
	public User updateUser(User user) {
		hibernateTemplate.update(user);
		return null;
	}

	@Override
	public boolean deleteUser(long guid) {
		User user =  (User) hibernateTemplate.find("from User where uid=?" , guid);
		hibernateTemplate.delete(user);
		return true;
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
		System.out.println("Trying to get a username : " + username);
		List<User> users = (List<User>) hibernateTemplate.find("from User where username=?0" , username);
		return users.get(0);
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
