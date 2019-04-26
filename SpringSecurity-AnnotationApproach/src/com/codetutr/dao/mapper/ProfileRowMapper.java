package com.codetutr.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.codetutr.model.Profile;

public class ProfileRowMapper implements RowMapper<Profile> {

	private final String columnLabelPrefix;
	
	public ProfileRowMapper(String columnLabelPrefix) {
		this.columnLabelPrefix = columnLabelPrefix;
	}

	@Override
	public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Profile profile = new Profile();
		profile.setUid(rs.getLong(columnLabelPrefix + "uid"));
		profile.setUsername(rs.getString(columnLabelPrefix + "username"));
		profile.setPassword(rs.getString(columnLabelPrefix + "password"));
		profile.setFirstName(rs.getString(columnLabelPrefix + "firstName"));
		profile.setLastName(rs.getString(columnLabelPrefix + "lastName"));
		profile.setEnabled(rs.getBoolean(columnLabelPrefix + "enabled"));
        return profile;
	}

}
  