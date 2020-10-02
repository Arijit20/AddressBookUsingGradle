package com.cg.addressbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateContact {

	public boolean validateFirstName(String firstName) {
		Pattern pattern = Pattern.compile("^([A-Z])[a-zA-Z]{2,}$");
		Matcher matcher = pattern.matcher(firstName);
		boolean b = matcher.find();
		return b;
	}

	public boolean validateLastName(String lastName) {
		Pattern pattern = Pattern.compile("^([A-Z])[a-zA-Z]{2,}$");
		Matcher matcher = pattern.matcher(lastName);
		Boolean b = matcher.find();
		return b;
	}


	
	public boolean validateAddress(String address) {
			return true;
	}

	public boolean validateCity(String city) {
		return true;
	}

	

	public boolean validateZip(String zip) {
		Pattern pattern = Pattern.compile("^([0-9]*){6}$");
		Matcher matcher = pattern.matcher(zip);
		Boolean b = matcher.find();
		return b;
	}

	public boolean validatePhoneNo(String phoneNo) {
		Pattern pattern = Pattern.compile("^(\\d){10}$");
		Matcher matcher = pattern.matcher(phoneNo);
		Boolean b = matcher.find();
		return b;
	}

	public boolean validateEmail(String email) {
		Pattern pattern = Pattern
				.compile("^([a-z]){1,}[a-z0-9]*([.+_-]){0,1}[0-9a-z]+(@){1}([0-9a-z]+)(\\.([a-z]){2,}){1}(\\.[a-z]{2,})?$");
		Matcher matcher = pattern.matcher(email);
		Boolean b = matcher.find();
		return b;
	}

	public boolean validateState(String state) {
		
		return true;
	}
	

}
