package com.cg.addressbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.*;

public class AddressBookFileIOService {

	public static String CONTACT_FILE_NAME = "contactsfile.txt";
	public static String CONTACT_SECOND_FILE_NAME = "contactsfile2.txt";

	public static final String SAMPLE_CSV_FILE_PATH = "./demo.csv";
	public static final String SAMPLE_CSV_FILE_PATH2 = "./demo2.csv";

	public static final String SAMPLE_JSON_FILE_PATH = "./demo.json";
	public static final String SAMPLE_JSON_FILE_PATH2 = "./demo2.json";

	public List<Contacts> readData() {
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
		return contactsList;
	}

	public void writeData(List<Contacts> contactList) {
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

	public List<Contacts> readCSVData() {

		List<Contacts> dummyList = new ArrayList<>();
		Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		synchronized (this) {
			contactStatusMap.put(1, false);
			Runnable task = () -> {
				List<Contacts> contactsList = null;
				try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH))) {
					CsvToBean<Contacts> csvToBean = new CsvToBeanBuilder<Contacts>(reader).withType(Contacts.class)
							.withIgnoreLeadingWhiteSpace(true).build();
					contactsList = new ArrayList<Contacts>();
					contactsList = csvToBean.parse();
                     dummyList.addAll(contactsList);
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
		return dummyList;

	}

	public boolean writeCSVData(List<Contacts> contactList) {
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

	public boolean writeJsonData(List<Contacts> contactList) {
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

	public boolean readJsonFile() {
		Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		synchronized (this) {
			contactStatusMap.put(1, false);
			Runnable task = () -> {
				try {
					Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_JSON_FILE_PATH2));
					JsonParser jsonParser = new JsonParser();
					JsonElement obj = jsonParser.parse(reader);
					JsonArray contactList = (JsonArray) obj;
					System.out.println(contactList);
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
