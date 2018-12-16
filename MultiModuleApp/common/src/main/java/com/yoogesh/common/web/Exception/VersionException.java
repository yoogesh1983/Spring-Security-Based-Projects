package com.yoogesh.common.web.Exception;

import com.yoogesh.common.web.LemonUtils;

/**
 * Version exception, to be thrown when concurrent
 * updates of an entity is noticed. 
 */
public class VersionException extends RuntimeException {

	private static final long serialVersionUID = 6020532846519363456L;
	
	public VersionException(String entityName) {
		super(LemonUtils.getMessage(
			"com.yoogesh.common.exception.versionException", entityName));
	}
}
