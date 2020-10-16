package com.cg.addressbook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class AddressBookFileIOTest {
     
	@Test
	public void givenContactsInFileShouldRead() {
		AddressBookFileIOService addressBookFileIOService = new AddressBookFileIOService();
		List<Contacts> contactList = new ArrayList<>();
		contactList = addressBookFileIOService.readData();
		System.out.println(contactList);
		Assert.assertEquals(2, contactList.size());
	}
	
	@Test
	public void writeContactsToFile() {
		AddressBookFileIOService addressBookFileIOService = new AddressBookFileIOService();
		List<Contacts> contactList = new ArrayList<>();
		Contacts contact1 = new Contacts("Arijit", "Dey", "sodepur", "kolkata", "WB", "123456", "7878787878", "arijiy@yahoo.co.in");
		Contacts contact2 = new Contacts("Partha", "Saha", "NewTown", "BidhanNagar", "WB", "785478", "9856257845", "partha@gmail.com");
		contactList.add(contact1);
		contactList.add(contact2);
		addressBookFileIOService.writeData(contactList);
		Assert.assertEquals(2, addressBookFileIOService.countEntries());
	}
}