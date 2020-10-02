package com.cg.addressbook;

import java.util.Scanner;

public class AddressBookMain {
	public static void main(String[] args) {

		AddressBookList addressBookList = new AddressBookList();
		Scanner in = new Scanner(System.in);
		boolean b = false;
		System.out.println("Welcome to Addressbook");

		System.out.println("Enter 1 to open existing addressbook");
		System.out.println("Enter 2 to open new addressbook");
		System.out.println("Enter 3 to exit");
		int option = in.nextInt();
		while (option != 3) {
			if (option == 1)
				addressBookList.openAddressBook();
			else if (option == 2)
				addressBookList.newAddressBook();
			else if (option == 3)
				break;
			else
				System.out.println("Enter Correct option");
			System.out.println("Enter Choice");
			option = in.nextInt();
		}
		System.out.println("Thank You");
	}
}