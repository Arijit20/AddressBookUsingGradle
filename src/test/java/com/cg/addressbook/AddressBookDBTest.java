package com.cg.addressbook;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cg.addressbook.dto.Contacts;
import com.cg.addressbook.service.AddressBookDBService;

import junit.framework.Assert;

public class AddressBookDBTest {
	
	private AddressBookDBService addressBookDBService;
	
	@Before
	public void initialize() {
		addressBookDBService = new AddressBookDBService();
	}

	@Test
	public void givenAddressBookDB_ShouldMatchCount() throws AddressBookDBException {
		List<Contacts> contactList = addressBookDBService.readData();
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
	
	@Test
	public void whenContactAddedToDB_ShouldMatchCount() throws AddressBookDBException {
		addressBookDBService.writeData("Saikat","Sarkar","dunlop","howrah","wb",
				                            "789987","7414585658","saikat@yahoo.co.in", LocalDate.now());
		List<Contacts> contactList = addressBookDBService.readData();
		Assert.assertEquals(3, contactList.size());
	}
	
	@Test
	public void whenAddedMultipleContacts_ShouldMatchCount() throws AddressBookDBException {
		Contacts[] arrOfContacts = {
		new Contacts("Rahul","Roy","town","bankura","wb","458585","8989898989","rah@gmail.com",LocalDate.now()),	
		new Contacts("Pratay","Mukherjee","sector1","noida","up","989652","8525252456","pratay@yahoo.co.in",LocalDate.now()),
		new Contacts("Arjun","Sarkar","sector2","noida","up","780014","7415263258","arjun@gmail.com",LocalDate.now())
		};
		addressBookDBService.writeData(Arrays.asList(arrOfContacts));
		List<Contacts> contactList = addressBookDBService.readData();
		Assert.assertEquals(6, contactList.size());
	}
}
