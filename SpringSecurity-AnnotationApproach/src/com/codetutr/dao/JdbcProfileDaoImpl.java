package com.codetutr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetutr.dao.mapper.AuthorityRowMapper;
import com.codetutr.dao.mapper.ProfileRowMapper;
import com.codetutr.model.Profile;
import com.codetutr.utility.RandomGenerator;

@Repository
@org.springframework.context.annotation.Profile("dev")
public class JdbcProfileDaoImpl implements IProfileDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void initiateDatabase() {
		createProfile(new Profile(1L, "user@gmail.com",passwordEncoder.encode("1234"), "Yoogesh", "Sharma", "ROLE_USER", true));
		createProfile(new Profile(2L, "admin@gmail.com",passwordEncoder.encode("1234"), "Kristy", "Sharma", "ROLE_ADMIN", true));
		createProfile(new Profile(3L, "dba@gmail.com",passwordEncoder.encode("1234"), "Sushila", "Sapkota", "ROLE_DBA", true));
	}
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public Profile createProfile(Profile profile) {
		Object[]  params = {RandomGenerator.generateLong().toString(), profile.getUsername(), profile.getPassword(), profile.getFirstName(), profile.getLastName(), profile.isEnabled()};
        jdbcTemplate.update("INSERT INTO USERS (uid, username, password, firstName, lastName, enabled) VALUES(?,?,?,?,?,?)", params);
		jdbcTemplate.update("INSERT INTO AUTHORITIES (username, authority) VALUES(?,?)", new Object[] {profile.getUsername(), profile.getAuthority()});
		return getProfileByUsername(profile.getUsername());
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public Profile updateProfile(Long guid, Profile profile) {
        Object[]  params = {profile.getUsername(), profile.getPassword(), profile.getFirstName(), profile.getLastName(), profile.isEnabled()};                         
        jdbcTemplate.update("UPDATE USERS SET username = ?, password = ?, firstName = ?, lastName = ?, enabled= ?  where uid = ?", params);
        jdbcTemplate.update("UPDATE AUTHORITIES SET authority = ? where uid = ?", new Object[] {profile.getUid()});
        return getProfileByUsername(profile.getUsername());
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean deleteProfile(long uid) {
		int affectedRows = jdbcTemplate.update("DELETE FROM USERS WHERE uid = ?", new Object[] {uid});
		return affectedRows > 0;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public List<Profile> getAllProfile() {
		return jdbcTemplate.query("SELECT * FROM USERS", new ProfileRowMapper("USERS."));
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public Profile getProfile(Long uid) {
		Profile profile = jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE uid=?", new ProfileRowMapper("USERS."), uid);
		if(profile != null) {
			profile.setAuthority(getAuthorityFromAuthoritiesTable(profile.getUsername()));
		}
		return profile;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public Profile getProfileByUsername(String username) {
		Profile profile = jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE username=?", new ProfileRowMapper("USERS."), username);
		if(profile != null) {
			profile.setAuthority(getAuthorityFromAuthoritiesTable(username));
		}
		return profile;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean ismoreUsernameExists(String username) {
		List<Map<String, Object>> profiles = jdbcTemplate.queryForList("SELECT * FROM USERS WHERE username=?", new Object[] {username});
		if(!profiles.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public List<Profile> getProfileByName(String firstName) {
		return jdbcTemplate.query("SELECT * FROM USERS WHERE firstName=?", new ProfileRowMapper("USERS."), firstName);
	}
	
	private String getAuthorityFromAuthoritiesTable(String username) {
		List<String> authorities = jdbcTemplate.query("SELECT authority FROM AUTHORITIES WHERE username=?", new AuthorityRowMapper("AUTHORITIES."), username);
		return authorities.get(0);
	}
	
}
