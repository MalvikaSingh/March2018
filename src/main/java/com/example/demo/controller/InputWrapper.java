package com.example.demo.controller;

import java.util.List;

public class InputWrapper {
	public List<String> applications;
	public String country;
	public String year;
	public List<String> getApplications() {
		return applications;
	}
	public void setApplications(List<String> applications) {
		this.applications = applications;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
