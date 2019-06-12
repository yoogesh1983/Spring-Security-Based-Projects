package com.codetutr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codetutr.entity.User;

public interface IUserRepository extends JpaRepository<User, Long>{
	
	public User findUserByUid(Long guid);
	public User findUserByUsername(String username);
	public List<User> findUserByFirstName(String firstName);
	public List<User> findUserByUsernameContains(String username);
}
