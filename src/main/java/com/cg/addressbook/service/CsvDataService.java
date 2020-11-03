package com.cg.addressbook.service;

import java.io.IOException;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.addressbook.AddressBookDBException;
import com.cg.addressbook.dto.Contacts;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CsvDataService implements ISyncWithDB {

	public static final String SAMPLE_CSV_FILE_PATH = "./demo.csv";
	public static final String SAMPLE_CSV_FILE_PATH2 = "./demo2.csv";

	AddressBookDBService addressBookDBService = new AddressBookDBService();

	@Override
	public List<Contacts> readData() throws AddressBookDBException {
		List<Contacts> contactsList = new ArrayList<>();
		Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		synchronized (this) {
			contactStatusMap.put(1, false);
			Runnable task = () -> {
				List<Contacts> dummyList = null;
				try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH))) {
					CsvToBean<Contacts> csvToBean = new CsvToBeanBuilder<Contacts>(reader).withType(Contacts.class)
							.withIgnoreLeadingWhiteSpace(true).build();
					dummyList = new ArrayList<Contacts>();
					dummyList = csvToBean.parse();
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
	public boolean writeData(List<Contacts> contactList) throws AddressBookDBException {
		Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		synchronized (this) {
			contactStatusMap.put(1, false);
			Runnable task = () -> {
				try (Writer writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE_PATH2))) {
					StatefulBeanToCsv<Contacts> beanToCsv = new StatefulBeanToCsvBuilder<Contacts>(writer)
							.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

					beanToCsv.write(contactList);
					contactStatusMap.put(1, true);
				} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
					e.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
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
