package com.yoogesh.persistence.dao.SpringData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yoogesh.common.web.entity.Vehicle;


/**
 * This means if we call this directly, then spring will throw exception.
 * A transaction must be open previously before this i.e. request must come via coreserviceClient.java
 */
@Transactional(propagation = Propagation.MANDATORY)
public interface VehicleRepository extends JpaRepository<Vehicle, Long>
{
	public List<Vehicle> findByname(String name);
} 
