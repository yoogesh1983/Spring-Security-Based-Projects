package com.codetutr.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetutr.config.logging.Log;
import com.codetutr.entity.Authority;
import com.codetutr.entity.User;
import com.codetutr.utility.UtilityHelper;

@Repository
@Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
@Profile("SpringEmJPA")
public class SpringEmJPADaoImpl implements IUserDao {

    @PersistenceContext
    private EntityManager entityManager;
    
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
		entityManager.persist(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		return entityManager.merge(user);
	}

	@Override
	public boolean deleteUser(long guid) {
		entityManager.remove(entityManager.find(User.class, guid));
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		Query query = entityManager.createQuery("SELECT u FROM User u");
		return (List<User>) query.getResultList();
	}

	@Override
	public User getUser(Long guid) {
		return entityManager.find(User.class, guid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByUserName(String username) {
		
		User user = null;
		
		try {
			
			//get user
			Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :firstParam")
							.setParameter("firstParam", username);
			
			user = (User) query.getSingleResult();
			
			query = entityManager.createQuery("SELECT a FROM Authority a WHERE a.user = ?1")
					.setParameter(1, user);
			List<Authority> authorities = (List<Authority>) query.getResultList();
			
			//set authority to user
			user.setAuthorities(authorities);
		}
		catch(NoResultException exception) {
			Log.logInfo(this.getClass().getName(), "getUserByUserName(String)", "Could not find the user " +  username, exception);
		}
		return user;
	}

	@Override
	public boolean ismoreUsernameExists(String username) {
		return getUserByUserName(username) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByName(String name) {
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.firstName = ?1")
				.setParameter(1, name);
		return (List<User>) query.getResultList();
	}
}
