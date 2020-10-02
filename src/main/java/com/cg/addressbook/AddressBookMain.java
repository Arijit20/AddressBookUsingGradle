package com.cg.addressbook;

import java.util.Scanner;

public class AddressBookMain {
	public static void main(String[] args) {
         AddressBook addressBook = new AddressBook();
		AddressBookList addressBookList = new AddressBookList();
		Scanner in = new Scanner(System.in);
		boolean b = false;
		System.out.println("Welcome to Addressbook");

		System.out.println("Enter 1 to open existing addressbook");
		System.out.println("Enter 2 to open new addressbook");
		System.out.println("Enter 3 to search persons in a city");
		System.out.println("Enter 4 to search persons in a state");
		System.out.println("Enter 5 to exit");
		int option = in.nextInt();
		while (option != 4) {
			if (option == 1)
				addressBookList.openAddressBook();
			else if (option == 2)
				addressBookList.newAddressBook();
			else if(option == 3) {
				System.out.println("Enter City");
				String city = in.next();
				System.out.println(addressBook.searchPersonsByCity(city));
			}
			else if(option == 4) {
				System.out.println("Enter State");
				String state = in.next();
				System.out.println(addressBook.searchPersonsByState(state));
			}
			else if (option == 5)
				break;
			else
				System.out.println("Enter Correct option");
			System.out.println("Enter Choice");
			option = in.nextInt();
		}
		System.out.println("Thank You");
	}
}