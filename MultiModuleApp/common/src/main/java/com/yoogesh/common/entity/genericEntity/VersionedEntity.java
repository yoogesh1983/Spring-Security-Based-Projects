package com.yoogesh.common.entity.genericEntity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.yoogesh.common.web.security.entity.AbstractUser;


/**
 * Base class for all entities with optimistic locking.
 * 
 * @author Sanjay Patel
 *
 * @param <U>	The User class
 * @param <ID>	The Primary key type of User class 
 */
@MappedSuperclass
public abstract class VersionedEntity<U extends AbstractUser<U,ID>, ID extends Serializable> extends LemonEntity<U, ID> {

	private static final long serialVersionUID = 4310555782328370192L;
	
	@Version
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
