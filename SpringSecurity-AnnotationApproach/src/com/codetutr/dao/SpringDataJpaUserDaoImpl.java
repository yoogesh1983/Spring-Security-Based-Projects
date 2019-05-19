package com.codetutr.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetutr.entity.User;
import com.codetutr.utility.UtilityHelper;

@Profile("SpringDataJPA")
@Repository
@Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
public class SpringDataJpaUserDaoImpl implements IUserDao {

	private IUserRepository userRepository;
	private IAuthorityRepository authorityRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public SpringDataJpaUserDaoImpl(IUserRepository userRepository, IAuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}
	

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
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public boolean deleteUser(long guid) {
		userRepository.deleteById(guid);
		return true;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(Long guid) {
		return userRepository.findUserByUid(guid);
	}

	@Override
	public User getUserByUserName(String username) {
		User user = userRepository.findUserByUsername(username);
		user.setAuthorities(authorityRepository.findByUser(user));
		return user;
	}

	@Override
	public boolean ismoreUsernameExists(String username) {
		return userRepository.findUserByUsername(username) != null;
	}

	@Override
	public List<User> getUserByName(String name) {
		return userRepository.findUserByFirstName(name);
	}

}
