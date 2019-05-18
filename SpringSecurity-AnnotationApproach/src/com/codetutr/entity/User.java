package com.codetutr.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="uid", nullable=false, length=50)
	private Long uid;
	
	@Column(name="username", nullable=false, length=50)
	private String username;
	
	@Column(name="password", nullable=false, length=256)
	private String password;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="enabled", nullable=false)
	private boolean enabled;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Authority> authorities;

	public User() {
		super();
	}

	
	/**
	 * @param uid
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param enabled
	 * @param authorities
	 */
	public User(Long uid, String username, String password, String firstName, String lastName, boolean enabled,
			List<Authority> authorities) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;
		this.authorities = authorities;
	}


	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}


	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}


	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * @return the authorities
	 */
	public List<Authority> getAuthorities() {
		return authorities;
	}


	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (enabled != other.enabled)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", enabled=" + enabled + "]";
	}

}
