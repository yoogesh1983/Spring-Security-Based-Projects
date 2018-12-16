package com.yoogesh.persistence.dao.Hibernate;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yoogesh.common.web.entity.Profile;


/**
 * This means if we call this directly, then spring will throw exception.
 * A transaction must be open previously before this i.e. request must come via coreserviceClient.java
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class ProfileRepositoryOldWay 
{
    @PersistenceContext
    private EntityManager entityManager;
    
	public Profile save(Profile profile) throws Exception 
	{
		Profile savedProfile = null;
		try
		{
			entityManager.persist(profile);
			savedProfile = profile;
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
		return savedProfile;
	}

	
    public Profile getProfileByProfileId(long id) {
        return entityManager.find(Profile.class, id);
    }
    

    @SuppressWarnings("unchecked")
	public Collection<Profile> findAllProfiles() {
    	Query query = entityManager.createQuery("SELECT profile FROM Profile profile");
    	return (Collection<Profile>) query.getResultList();
    }
}
