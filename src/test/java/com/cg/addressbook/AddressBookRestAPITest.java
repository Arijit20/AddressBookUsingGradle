package com.cg.addressbook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cg.addressbook.dto.Contacts;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookRestAPITest {

	@Before
	public void initialize() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	
	private Contacts[] getContactList() {
		Response response = RestAssured.get("/addressbook");
		Contacts[] arrOfContact = new Gson().fromJson(response.asString(),  Contacts[].class);
		return arrOfContact;
	}
	
	private Response addContactToJSONServer(Contacts contact) {
		String contactJson = new Gson().toJson(contact);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(contactJson);
		return requestSpecification.post("/addressbook");
	}
	
	@Test
	public void givenAddressBookDataInJsonServer_WhenRetrived_ShouldMatchCount() {
		Contacts[] arrOfContact = getContactList();
		AddressBook addressBook;
		addressBook = new AddressBook(Arrays.asList(arrOfContact));
		long entries = AddressBook.contactList.size();
		Assert.assertEquals(4, entries);
	}
	
	@Test
	public void givenContacts_WhenAdded_ShouldMatchStatusAndCount() throws AddressBookDBException {
		Contacts[] arrOfContact = getContactList();
		AddressBook addressBook;
		addressBook = new AddressBook(Arrays.asList(arrOfContact));
		
		Contacts contact = new Contacts(0, "Rounak","Sikdar","town","durgapur","wb","741456","9856565685","rounak@gmail.com");
		Response response = addContactToJSONServer(contact);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		
		contact = new Gson().fromJson(response.asString(), Contacts.class);
		addressBook.addNewContact(contact);
		long entries = AddressBook.contactList.size();
		Assert.assertEquals(5, entries);
	}
	
	@Test
	public void givenListOfContact_WhenAdded_ShouldMatch201ResponseAndCount() {
	     Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		Contacts[] arrOfContact = getContactList();
		AddressBook addressBook;
		addressBook = new AddressBook(Arrays.asList(arrOfContact));
		
		Contacts[] arrOfContacts = {
			new Contacts(0, "Rahul","Ghosh","sector 4","kalyani","wb","741477","9850005685","rahul@gmail.com"),
			new Contacts(0, "Sagnik","Mitra","ghola","sodepur","wb","742456","7756565685","sagnik@gmail.com")
		};
		for(Contacts contact : arrOfContacts) {
			contactStatusMap.put(contact.hashCode(), false);
			Runnable task = () -> {
			Response response = addContactToJSONServer(contact);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(201, statusCode);	
			Contacts newContact = new Gson().fromJson(response.asString(), Contacts.class);
			try {
				addressBook.addNewContact(newContact);
			} catch (AddressBookDBException e) {
				e.printStackTrace();
			}
			contactStatusMap.put(contact.hashCode(), true);
			};
			Thread thread = new Thread(task);
			thread.start();
		}
		while(contactStatusMap.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long entries = AddressBook.contactList.size();
		Assert.assertEquals(7, entries);
	}
}
