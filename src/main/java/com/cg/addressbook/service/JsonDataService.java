package com.cg.addressbook.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.addressbook.AddressBookDBException;
import com.cg.addressbook.dto.Contacts;
import com.google.gson.Gson;

public class JsonDataService implements ISyncWithDB {

	public static final String SAMPLE_JSON_FILE_PATH = "./demo.json";
	public static final String SAMPLE_JSON_FILE_PATH2 = "./demo2.json";

	AddressBookDBService addressBookDBService = new AddressBookDBService();

	@Override
	public List<Contacts> readData() throws AddressBookDBException {
		Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		List<Contacts> contactsList = new ArrayList<>();
		synchronized (this) {
			contactStatusMap.put(1, false);
			Runnable task = () -> {
				try {
					Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_JSON_FILE_PATH2));
					Gson gson = new Gson();
					Contacts[] contactsArray = gson.fromJson(reader, Contacts[].class);
					List<Contacts> dummyList = new ArrayList<Contacts>(Arrays.asList(contactsArray));
					contactsList.addAll(dummyList);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
				Gson gson = new Gson();
				String json = gson.toJson(contactList);
				try {
					FileWriter fileWriter = new FileWriter(SAMPLE_JSON_FILE_PATH);
					fileWriter.write(json);
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
