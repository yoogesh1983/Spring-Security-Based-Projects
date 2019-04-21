package com.codetutr.model;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class LemonPermissionEvaluator implements PermissionEvaluator{

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		for(GrantedAuthority next: authentication.getAuthorities()) {
			if(next.getAuthority().equals("ROLE_DBA")) {
              return true;
			}
		}
		return false;
	}
	

	/**
	 * We need to override this method as well. To keep things simple,
	 * Let's not use this, throwing exception is someone uses it.
	 */
	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		
		throw new UnsupportedOperationException("hasPermission() by ID is not supported");		
	}

}
