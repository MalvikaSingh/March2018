package com.example.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
@Configuration
public class ExtractTableMapper implements RowMapper<ExtractsTable> {
	
	public ExtractsTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExtractsTable model = new ExtractsTable();
		model.setusername(rs.getString("username"));
		model.setuserid(rs.getString("userid"));
		model.setcreationdate(rs.getString("creationdate"));
		model.setlastlogindate(rs.getString("lastlogindate"));
		model.setlastlogintime(rs.getString("lastlogintime"));
		model.setcountry(rs.getString("country"));
		model.setapplication(rs.getString("application"));
		model.setyear(rs.getString("year"));

        return model;
    }

}
