package com.example.demo.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.ExampleDAO;
import com.example.demo.model.ExtractsModel;

@Controller
public class UserController {
	
	@Autowired
	private ExampleDAO data;

		@PostMapping("/getOutput")
		@ResponseBody
		public Map<String, Object> home1(@RequestBody InputWrapper inw) {
			Map<String, Object> model = new HashMap<String, Object>();
			List<ExtractsModel> user = data.getListOfUsers(inw);
			model.put("Data",user);
			String[] stringArray = user.toArray(new String[0]);
			return model;
		}
}
