package com.yoogesh.persistence.dao.SpringData;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yoogesh.common.web.entity.Profile;
import com.yoogesh.common.web.security.entity.AbstractUserRepository;

/**
 * This means if we call this directly, then spring will throw exception.
 * A transaction must be open previously before this i.e. request must come via coreserviceClient.java
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface ProfileRepository extends AbstractUserRepository<Profile, Long> {
	
}
