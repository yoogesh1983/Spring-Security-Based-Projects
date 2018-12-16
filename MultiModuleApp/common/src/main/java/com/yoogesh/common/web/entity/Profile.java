package com.yoogesh.common.web.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoogesh.common.web.security.entity.AbstractUser;

@Entity
@Table(name = "profile")
public class Profile extends AbstractUser<Profile, Long> 
{
	private static final long serialVersionUID = 2716710947175132319L;
	
    @Column(name="firstName")
    private String firstName;
    
    @Column(name="lastName")
    private String lastName;
    
	@OneToMany(mappedBy="profile", cascade={CascadeType.ALL})
	@JsonIgnore
	private List<Vehicle> vehicles;
	
	
	/**
	 * Getters and Setters
	 */
	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Profile [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + ", unverified=" + unverified + ", blocked=" + blocked + ", admin=" + admin + ", goodUser="
				+ goodUser + ", goodAdmin=" + goodAdmin + ", editable=" + editable + ", rolesEditable=" + rolesEditable
				+ ", authorities=" + authorities + "]";
	}
	
	

}