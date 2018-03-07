package com.example.demo.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.example.demo.controller.InputWrapper;
import com.example.demo.model.ExtractTableMapper;
import com.example.demo.model.ExtractsModel;
import com.example.demo.model.ExtractsTable;
@Service
public class ExampleDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ExtractTableMapper dataRowMaper;
	@Autowired
	private ExtractTableMapper extractTableMapper;

	public List<ExtractsModel> getListOfUsers(InputWrapper inw){//initially no params were passed

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("USERAPP").withProcedureName("getUserdetails")
				.returningResultSet("P_EMP_REFCUR", dataRowMaper);
		String text = (inw.getApplications()).get(0);
		for(int i=1;i<((inw.getApplications()).size());i++) {
			text = text+","+((inw.getApplications()).get(i));
		}
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_APP", text);
		inParamMap.put("P_COUNTRY", inw.getCountry());
		inParamMap.put("P_YEAR", inw.getYear());
		inParamMap.put("P_EMP_REFCUR",null);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		System.out.println(simpleJdbcCallResult);

		return (List<ExtractsModel>) simpleJdbcCallResult.get("P_EMP_REFCUR");
	}

	public List<ExtractsTable> getDamProfiles() {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("USERAPP").withProcedureName("getUserdetails")
				.returningResultSet("P_EMP_REFCUR", extractTableMapper);

		SqlParameterSource in = new MapSqlParameterSource();

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		System.out.println(simpleJdbcCallResult);

		return (List<ExtractsTable>) simpleJdbcCallResult.get("P_EMP_REFCUR");

	}
	
		public Object get_User(String app,String country,String year, String user) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("USERAPP").withProcedureName(
						"getUserdetails");

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_APPNAME", app);
		inParamMap.put("P_COUNTRY", country);
		inParamMap.put("P_YEAR", year);
		inParamMap.put("P_EMP_REFCUR", null);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map out = simpleJdbcCall.execute(in);
		return (out.get("REFCUR").getClass());
	}

}
