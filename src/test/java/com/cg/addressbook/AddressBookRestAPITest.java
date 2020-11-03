package com.cg.addressbook;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	public void givenContacts_WhenAdded_ShouldMatchStatusAndCount() {
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
		Contacts[] arrOfContact = getContactList();
		AddressBook addressBook;
		addressBook = new AddressBook(Arrays.asList(arrOfContact));
		
		Contacts[] arrOfContacts = {
			new Contacts(0, "Rahul","Ghosh","sector 4","kalyani","wb","741477","9850005685","rahul@gmail.com"),
			new Contacts(0, "Sagnik","Mitra","ghola","sodepur","wb","742456","7756565685","sagnik@gmail.com")
		};
		for(Contacts contact : arrOfContacts) {
			Response response = addContactToJSONServer(contact);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(201, statusCode);
			
			contact = new Gson().fromJson(response.asString(), Contacts.class);
			addressBook.addNewContact(contact);
		}
		long entries = AddressBook.contactList.size();
		Assert.assertEquals(7, entries);
	}
}
