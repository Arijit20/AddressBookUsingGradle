package com.cg.addressbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIOService {

	public static String CONTACT_FILE_NAME = "contactsfile.txt";
	public static String CONTACT_SECOND_FILE_NAME = "contactsfile2.txt";

	public List<Contacts> readData() {
		List<Contacts> contactsList = new ArrayList<>();
		try {
			Files.lines(new File(CONTACT_FILE_NAME).toPath()).map(line -> line.trim()).forEach(line -> {
				String[] words = line.split("[\\s,:]+");

				Contacts contact = new Contacts();
				contact.setFirstName(words[1]);
				contact.setLastName(words[3]);
				contact.setAddress(words[5]);
				contact.setCity(words[7]);
				contact.setState(words[9]);
				contact.setZip(words[11]);
				contact.setPhoneNo(words[13]);
				contact.setEmail(words[15]);

				contactsList.add(contact);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contactsList;
	}

	public void writeData(List<Contacts> contactList) {
		StringBuffer empBuffer = new StringBuffer();
		contactList.forEach(contact -> {
			String employeeDataString = contact.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});
		try {
			Files.write(Paths.get(CONTACT_SECOND_FILE_NAME), empBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File(CONTACT_SECOND_FILE_NAME).toPath()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
}
