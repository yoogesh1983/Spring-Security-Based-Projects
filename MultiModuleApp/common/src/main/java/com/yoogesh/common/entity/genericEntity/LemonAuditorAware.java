package com.yoogesh.common.entity.genericEntity;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.AuditorAware;

import com.yoogesh.common.web.LemonUtils;
import com.yoogesh.common.web.security.entity.AbstractUser;

/**
 * Needed for auto-filling of the
 * AbstractAuditable columns of AbstractUser
 *  
 * @author Sanjay Patel
 *
 * @param <U>	The User class
 * @param <ID>	The Primary key type of User class 
 */
public class LemonAuditorAware
	<U extends AbstractUser<U,ID>,
	 ID extends Serializable>
implements AuditorAware<U> {
	
    private static final Log log = LogFactory.getLog(LemonAuditorAware.class);
    
	public LemonAuditorAware() {
		log.info("Created");
	}

	@Override
	public U getCurrentAuditor() {
		return LemonUtils.getUser();
	}	
}
