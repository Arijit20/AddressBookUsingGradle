package com.cg.addressbook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class AddressBookDBTest {
	
	private AddressBookDBService addressBookDBService;
	
	@Before
	public void initialize() {
		addressBookDBService = new AddressBookDBService();
	}

	@Test
	public void givenAddressBookDB_ShouldMatchCount() throws AddressBookDBException {
		List<Contacts> contactList = addressBookDBService.readContacts();
		Assert.assertEquals(2, contactList.size());
	}
}
