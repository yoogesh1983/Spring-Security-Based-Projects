package com.yoogesh.security.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yoogesh.common.LoggerHelper.Log;
import com.yoogesh.common.web.security.entity.AbstractUser;
import com.yoogesh.common.web.security.entity.AbstractUserRepository;

/**
 * UserDetailsService, as required by Spring Security.
 * If this implementation does not meet your requirement,
 * just provide your own. Providing the property
 * lemon.enabled.user-details-service: false will
 * suppress this configuration. 
 *
 * @param <U>	The user class
 * @param <ID>	Primary key class, e.g. Long
 */
@Component
public class MyUserDetailsService
	<U extends AbstractUser<U,ID>, ID extends Serializable>
implements UserDetailsService {
	
	private static final String CLASS_NAME = MyUserDetailsService.class.getName();

	@Autowired
	private AbstractUserRepository<U,ID> userRepository;
	
	
	@Override
	public U loadUserByUsername(String username) throws UsernameNotFoundException {
		U user = findUserByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));
		return user.decorate(user);
	}

	
	/**
	 * Finds a user by the given username. Override this if you aren't using email as the username.
	 */
	protected Optional<U> findUserByUsername(String username) {
		Optional<U> user = userRepository.findByEmail(username);
		if(user.equals(Optional.empty())){
			Log.logInfo(CLASS_NAME, "findUserByUsername(String)" ,"Unable to find the username : " + username);
		}
		return user;
	}
}
