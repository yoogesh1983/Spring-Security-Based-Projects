package com.codetutr.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.codetutr.model.Profile;

@Repository
public class JdbcProfileDaoImpl implements IProfileDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcProfileDaoImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Profile createProfile(Profile profile) {
		return null;
	}

	@Override
	public Profile updateProfile(Long guid, Profile profile) {
		return null;
	}

	@Override
	public boolean deleteProfile(long guid) {
		return false;
	}

	@Override
	public List<Profile> getAllProfile() {
		return null;
	}

	@Override
	public Profile getProfile(Long guid) {
		return null;
	}

	@Override
	public Profile getProfileByUsername(String username) {
		String sql = "SELECT * FROM users";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		System.out.println("========here we go===========" + rows);
		return null;
	}

	@Override
	public boolean ismoreUsernameExists(String username) {
		return false;
	}

	@Override
	public ArrayList<Profile> getProfileByName(Object obj) {
		return null;
	}

}
