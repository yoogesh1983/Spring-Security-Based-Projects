package com.yoogesh.security.principalExtractor;

import com.yoogesh.common.web.security.entity.AbstractUser;

public class DefaultPrincipalExtractor<U extends AbstractUser<U,?>> extends AbstractPrincipalExtractor<U> {
	
	public DefaultPrincipalExtractor() {
		
		super(LemonPrincipalExtractor.DEFAULT);
		log.info("Created");
	}
}
