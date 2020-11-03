package com.cg.addressbook.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.addressbook.AddressBookDBException;
import com.cg.addressbook.dto.Contacts;

public class FileDataService implements ISyncWithDB{
	
	public static String CONTACT_FILE_NAME = "contactsfile.txt";
	public static String CONTACT_SECOND_FILE_NAME = "contactsfile2.txt";
	AddressBookDBService addressBookDBService = new AddressBookDBService();

	@Override
	public List<Contacts> readData() throws AddressBookDBException {
		Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		List<Contacts> contactsList = new ArrayList<>();
		synchronized (this) {

			contactStatusMap.put(1, false);
			Runnable task = () -> {

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

				contactStatusMap.put(1, true);
			};
			Thread thread = new Thread(task);
			thread.start();

		}
		while (contactStatusMap.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		addressBookDBService.writeData(contactsList);
		return contactsList;
	}

	@Override
	public boolean writeData(List<Contacts> contactList) throws AddressBookDBException{
		Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		synchronized (this) {
			contactStatusMap.put(1, false);
			Runnable task = () -> {
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
				contactStatusMap.put(1, true);
			};
			Thread thread = new Thread(task);
			thread.start();
		}
		while (contactStatusMap.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
