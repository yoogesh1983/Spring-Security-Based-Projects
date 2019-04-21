package com.codetutr.model;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class LemonPermissionEvaluator implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		boolean result = false;
		if (!(targetDomainObject instanceof Profile)) {
			result = false;
		} else {
			Profile profile = (Profile) targetDomainObject;
			if (profile.getRole() != null && profile.getRole().equals("ROLE_DBA")) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * We need to override this method as well. To keep things simple, Let's not use
	 * this, throwing exception is someone uses it.
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {

		throw new UnsupportedOperationException("hasPermission() by ID is not supported");
	}

}
