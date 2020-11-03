package com.cg.addressbook;

public class AddressBookDBException extends Exception{

	public enum ExceptionType{
		CONNECTION_ERROR, INCORRECT_INFO
	}

	ExceptionType type;
	
	public AddressBookDBException(ExceptionType type, String message) {
		super(message);
		this.type = type;
	}
}
