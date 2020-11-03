package com.cg.addressbook;

import java.util.ArrayList;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

import com.cg.addressbook.dto.Contacts;
import com.cg.addressbook.service.CsvDataService;
import com.cg.addressbook.service.FileDataService;
import com.cg.addressbook.service.JsonDataService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class AddressBookFileIOTest {
     
	@SuppressWarnings("deprecation")
	@Test
	public void givenContactsInFileShouldRead() throws AddressBookDBException {
		FileDataService fileIOService = new FileDataService();
		List<Contacts> contactList = new ArrayList<>();
		contactList = fileIOService.readData();
		System.out.println(contactList);
		Assert.assertEquals(2, contactList.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void writeContactsToFile() throws AddressBookDBException {
		FileDataService fileIOService = new FileDataService();
		List<Contacts> contactList = new ArrayList<>();
		Contacts contact1 = new Contacts("Arijit", "Dey", "sodepur", "kolkata", "WB", "123456", "7878787878", "arijiy@yahoo.co.in");
		Contacts contact2 = new Contacts("Partha", "Saha", "NewTown", "BidhanNagar", "WB", "785478", "9856257845", "partha@gmail.com");
		contactList.add(contact1);
		contactList.add(contact2);
		boolean result = fileIOService.writeData(contactList);
		Assert.assertTrue(result);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void givenContactsFromCSVFileShouldRead() throws AddressBookDBException {
		CsvDataService csvIOService = new CsvDataService();
		List<Contacts> contactList = new ArrayList<>();
		contactList = csvIOService.readData();
		System.out.println(contactList);
		Assert.assertEquals(2, contactList.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void writeContactsToCSVFile() throws AddressBookDBException {
		CsvDataService csvIOService = new CsvDataService();
		List<Contacts> contactList = new ArrayList<>();
		Contacts contact1 = new Contacts("Arijit", "Dey", "sodepur", "kolkata", "WB", "123456", "7878787878", "arijiy@yahoo.co.in");
		Contacts contact2 = new Contacts("Partha", "Saha", "NewTown", "BidhanNagar", "WB", "785478", "9856257845", "partha@gmail.com");
		contactList.add(contact1);
		contactList.add(contact2);
		boolean b = csvIOService.writeData(contactList);
		Assert.assertTrue(b);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void writeContactsToJsonFile() throws AddressBookDBException {
		JsonDataService jsonIOService = new JsonDataService();
		List<Contacts> contactList = new ArrayList<>();
		Contacts contact1 = new Contacts("Arijit", "Dey", "sodepur", "kolkata", "WB", "123456", "7878787878", "arijiy@yahoo.co.in");
		Contacts contact2 = new Contacts("Partha", "Saha", "NewTown", "BidhanNagar", "WB", "785478", "9856257845", "partha@gmail.com");
		contactList.add(contact1);
		contactList.add(contact2);
		boolean b = jsonIOService.writeData(contactList);
		Assert.assertTrue(b);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void readContactsFromJsonFile() throws AddressBookDBException {
		JsonDataService jsonIOService = new JsonDataService();
		List<Contacts> contactList = new ArrayList<>();
		Contacts contact1 = new Contacts("Arijit", "Dey", "sodepur", "kolkata", "WB", "123456", "7878787878", "arijiy@yahoo.co.in");
		Contacts contact2 = new Contacts("Partha", "Biswas", "NewTown", "BidhanNagar", "WB", "785478", "9856257845", "partha@gmail.com");
		contactList.add(contact1);
		contactList.add(contact2);
		List<Contacts> contactList1 = jsonIOService.readData();
		Assert.assertEquals(2, contactList1.size());
	}
}
