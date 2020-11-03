package com.cg.addressbook;

import java.util.*;

import java.util.stream.Collectors;

import com.cg.addressbook.dto.Contacts;
import com.cg.addressbook.service.AddressBookDBService;

public class AddressBook {
	Scanner in = new Scanner(System.in);
	public static LinkedList<Contacts> contactList = new LinkedList<>();
	Map<String, Contacts> contactMap = new TreeMap<>();

	public AddressBook() {
		contactMap = new TreeMap<>();
	}
	
	public AddressBook(List<Contacts> contactList) {
		this();
		AddressBook.contactList = new LinkedList<>(contactList);
	}

	public Map<String, Contacts> getContactMap() {
		return contactMap;
	}
	
	public void addNewContact(Contacts contact) throws AddressBookDBException {
		contactList.add(new Contacts(contact.getId(), contact.getFirstName(), contact.getLastName(),contact.getAddress(), contact.getCity(),
				contact.getState(),contact.getZip(), contact.getPhoneNo(), contact.getEmail()));
		AddressBookDBService addressBookDBService = new AddressBookDBService();
		addressBookDBService.writeData(contactList);
	}

	public void addNewContact() {
		boolean b = false;
		Contacts person = new Contacts();
		System.out.print("First Name: ");
		while (!b) {
			b = person.setFirstName(in.next());
		}
		b = false;
		System.out.print("Last Name: ");
		while (!b) {
			b = person.setLastName(in.next());
		}
		b = false;
		System.out.print("Address: ");
		while (!b) {
			b = person.setAddress(in.next());
		}
		b = false;
		System.out.print("City: ");
		while (!b) {
			b = person.setCity(in.next());
		}
		b = false;
		System.out.print("State: ");
		while (!b) {
			b = person.setState(in.next());
		}
		b = false;
		System.out.print("Zip: ");
		while (!b) {
			b = person.setZip(in.next());
		}
		b = false;
		System.out.print("Phone Number: ");
		while (!b) {
			b = person.setPhoneNo(in.next());
		}
		b = false;
		System.out.print("Email: ");
		while (!b) {
			b = person.setEmail(in.next());
		}
		String name = person.getFirstName() + " " + person.getLastName();
		Boolean keyPresent = contactMap.containsKey(name);
		if (keyPresent) {
			System.out.println("This name is already present\n");
		} else {
			contactMap.put(name, person);
			contactList.add(person);
			AddressBookDBService addressBookDBService = new AddressBookDBService();
			try {
				addressBookDBService.writeData(contactList);
			} catch (AddressBookDBException e) {
				e.printStackTrace();
			}
		}
	}

	public void editContact() {
		System.out.println("Enter First Name :");
		String firstName = in.next();
		System.out.println("Enter Last Name :");
		String lastName = in.next();
		String name = firstName + " " + lastName;
		boolean b = false;
		Boolean keyPresent = contactMap.containsKey(name);
		if (keyPresent) {
			System.out.println("What do you want to update?");
			String s = in.next().toLowerCase();
			switch (s) {
			case ("address"):
				System.out.println("Enter address");
				while (!b) {
					b = contactMap.get(name).setAddress(in.next());
				}
				break;
			case ("city"):
				System.out.println("Enter city");
				while (!b) {
					b = contactMap.get(name).setCity(in.next());
				}
				break;
			case ("state"):
				System.out.println("Enter state");
				while (!b) {
					b = contactMap.get(name).setState(in.next());
				}
				break;
			case ("zip"):
				System.out.println("Enter zip");
				while (!b) {
					b = contactMap.get(name).setZip(in.next());
				}
				break;
			case ("phoneno"):
				System.out.println("Enter phone no");
				while (!b) {
					b = contactMap.get(name).setPhoneNo(in.next());
				}
				break;
			case ("email"):
				System.out.println("Enter email");
				while (!b) {
					b = contactMap.get(name).setEmail(in.next());
				}
				break;
			}
		} else {
			System.out.println("This name is not present in the address book.");
		}

	}

	public void showDetail() {
		if (contactMap.size() == 0)
			System.out.println("No contacts to show");
		else {
			Set set = contactMap.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				System.out.println("---------------");
				System.out.println(entry.getValue());
			}
		}
	}

	public void deleteContact() {
		System.out.println("Enter First Name :");
		String firstName = in.next();
		System.out.println("Enter Last Name :");
		String lastName = in.next();
		String name = firstName + " " + lastName;
		Boolean keyPresent = contactMap.containsKey(name);
		if (keyPresent) {
			contactMap.remove(name);
			Contacts c = contactMap.get(name);
			contactList.remove(c);

		} else {
			System.out.println("This name is not present in address book.");
		}
	}

	public List<Contacts> searchPersonsByCity(String city) {
		return contactList.stream().filter(person -> person.getCity().equals(city)).collect(Collectors.toList());
	}

	public List<Contacts> searchPersonsByState(String state) {
		return contactList.stream().filter(person -> person.getState().equals(state)).collect(Collectors.toList());
	}

	public int countPersonsByCity(String city) {
		return contactList.stream().filter(person -> person.getCity().equals(city)).collect(Collectors.toList()).size();
	}

	public int countPersonsByState(String state) {
		return contactList.stream().filter(person -> person.getState().equals(state)).collect(Collectors.toList())
				.size();
	}

	public List<Contacts> sortPersonsByCity() {
		return contactList.stream().sorted((a, b) -> a.getCity().compareTo(b.getCity())).collect(Collectors.toList());
	}

	public List<Contacts> sortPersonsByState() {
		return contactList.stream().sorted((a, b) -> a.getState().compareTo(b.getState())).collect(Collectors.toList());
	}

	public List<Contacts> sortPersonsByZip() {
		return contactList.stream().sorted((a, b) -> a.getZip().compareTo(b.getZip())).collect(Collectors.toList());
	}
}
