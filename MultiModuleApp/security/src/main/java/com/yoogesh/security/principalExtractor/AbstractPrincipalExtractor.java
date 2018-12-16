package com.yoogesh.security.principalExtractor;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yoogesh.common.web.LemonUtils;
import com.yoogesh.common.web.security.entity.AbstractUser;
import com.yoogesh.security.service.AbstractUserService;
import com.yoogesh.security.service.MyUserDetailsService;

@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public abstract class AbstractPrincipalExtractor<U extends AbstractUser<U,?>>
		implements LemonPrincipalExtractor {

    protected final Log log = LogFactory.getLog(this.getClass());
    
    private PasswordEncoder passwordEncoder;
	private MyUserDetailsService<U,?> userDetailsService;
    private AbstractUserService<U,?> lemonService;
	private final String provider;
    private String emailKey = "email";
    
	public AbstractPrincipalExtractor(String provider) {
		this.provider = provider;
	}

	@Autowired
    public void createAbstractPrincipalExtractor(PasswordEncoder passwordEncoder,
    		MyUserDetailsService<U,?> userDetailsService,
		    AbstractUserService<?,?> lemonService) {

    	this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
		this.lemonService = (AbstractUserService<U,?>) lemonService;
	}

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
    	
    	U user;
    	
		try {
			
			// Return the user if it already exists
			user = userDetailsService
				.loadUserByUsername((String) map.get(emailKey));
			
		} catch (UsernameNotFoundException e) {
			
			// register a new user
			user = lemonService.newUser();
			user.setEmail((String) map.get(emailKey));
			user.setUsername(user.getEmail());
			user.setPassword(passwordEncoder.encode(LemonUtils.uid()));
			fillAdditionalFields(user, map);
			
			lemonService.forgotPassword(user);
			user.decorate(user);
		}
		
		map.put(AUTHORITIES, user.getAuthorities());		
		return user;		
    }
    
	protected void fillAdditionalFields(U user, Map<String, Object> map) {
	    // Override for filling any additional fields, e.g. name		
	}

	@Override
	public String getProvider() {
		return provider;
	}
	
    protected void setEmailKey(String emailKey) {
		this.emailKey = emailKey;
	}
}