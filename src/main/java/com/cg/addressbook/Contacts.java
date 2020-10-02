package com.cg.addressbook;

public class Contacts {
	private String firstName, lastName, name, address, city, state, zip, phoneNo, email;
	
	ValidateContact validateContact = new ValidateContact();


	public String getFirstName() {
		return firstName;
	}

	public boolean setFirstName(String firstName) {
	     boolean b = validateContact.validateFirstName(firstName);
	    if(b)
		this.firstName = firstName;
		return b;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean setLastName(String lastName) {
		 boolean b = validateContact.validateLastName(lastName);
		    if(b)
			this.lastName = lastName;
			return b;
	}

	public String getAddress() {
		return address;
	}

	public boolean setAddress(String address) {
		 boolean b = validateContact.validateAddress(address);
		    if(b)
			this.address = address;
			return b;
	}

	public String getCity() {
		return city;
	}

	public boolean setCity(String city) {
		 boolean b = validateContact.validateCity(city);
		    if(b)
			this.city = city;
			return b;
	}

	public String getState() {
		return state;
	}

	public boolean setState(String state) {
		 boolean b = validateContact.validateState(state);
		    if(b)
			this.state = state;
			return b;
	}

	public String getZip() {
		return zip;
	}

	public boolean setZip(String zip) {
		 boolean b = validateContact.validateZip(zip);
		    if(b)
			this.zip = zip;
			return b;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public boolean setPhoneNo(String phoneNo) {
		 boolean b = validateContact.validatePhoneNo(phoneNo);
		    if(b)
			this.phoneNo = phoneNo;
			return b;
	}

	public String getEmail() {
		return email;
	}

	public boolean setEmail(String email) {
		 boolean b = validateContact.validateEmail(email);
		    if(b)
			this.email = email;
			return b;
	}

	@Override
	public String toString() {
		return "First Name :" + firstName + "\nLast Name :" + lastName + "\nAddress :" + address + "\nCity :" + city
				+ "\nState :" + state + "\nZip :" + zip + "\nPhone No :" + phoneNo + "\nEmail :" + email;
	}

}
