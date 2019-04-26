package com.codetutr.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AuthorityRowMapper implements RowMapper<String> {

	private final String columnLabelPrefix;
	
	public AuthorityRowMapper(String columnLabelPrefix) {
		this.columnLabelPrefix = columnLabelPrefix;
	}

	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(columnLabelPrefix + "authority");
	}
}
  