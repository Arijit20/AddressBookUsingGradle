package com.cg.addressbook;

import java.time.LocalDate;
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
	
	
	@Test
	public void givenAddressBookDB_WhenAddressUpdated_ShouldSync() throws AddressBookDBException {
		addressBookDBService.updatePersonAddress("Partha","street","behala");
		Contacts contact = addressBookDBService.isAddressBookInSyncWithDB("Partha");
		Assert.assertEquals("behala", contact.getAddress());
	}
	
	@Test
	public void givenAddressBookDB_WhenStateUpdated_ShouldSync() throws AddressBookDBException {
		addressBookDBService.updatePersonAddress("Partha","state","kerala");
		Contacts contact = addressBookDBService.isAddressBookInSyncWithDB("Partha");
		Assert.assertEquals("kerala", contact.getState());
	}

	@Test
	public void givenAddressBookDB_WhenRetrivedBasedOnDate_ShouldReturnCount() throws AddressBookDBException {
		LocalDate startDate = LocalDate.of(2017, 01, 01);
		LocalDate endDate = LocalDate.now();
		int noOfContacts= addressBookDBService.getContactsOnDateRange(startDate, endDate);
		Assert.assertEquals(2, noOfContacts);
	}
	
	@Test
	public void givenAddressBookDB_WhenRetrivedBasedOnCity_ShouldReturnCount() throws AddressBookDBException {
		int noOfContacts = addressBookDBService.retriveBasedOnField("city","kolkata");
		Assert.assertEquals(2, noOfContacts);
	}
	
	@Test
	public void givenAddressBookDB_WhenRetrivedBasedOnState_ShouldReturnCount() throws AddressBookDBException {
		int noOfContacts = addressBookDBService.retriveBasedOnField("state","wb");
		Assert.assertEquals(1, noOfContacts);
	}
}
