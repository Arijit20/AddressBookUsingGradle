package com.cg.addressbook.service;

import java.util.List;

import com.cg.addressbook.AddressBookDBException;
import com.cg.addressbook.dto.Contacts;

public interface ISyncWithDB {

	public List<Contacts> readData() throws AddressBookDBException;
	
	public boolean writeData(List<Contacts> contactList) throws AddressBookDBException;
}
