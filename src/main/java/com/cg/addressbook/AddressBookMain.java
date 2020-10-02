package com.cg.addressbook;
import java.util.Scanner;

public class AddressBookMain {
	public static void main(String[] args) {
		AddressBook addressbook = new AddressBook();
		Scanner in = new Scanner(System.in);
		boolean b = false;
		System.out.println("Welcome to Addressbook");
        System.out.println("Enter 1 to add contact");
        System.out.println("Enter 2 to edit contact");
        System.out.println("Enter 3 to see addressbook details");
        System.out.println("Enter 4 to exit");
       int option = in.nextInt();
        while(option!= 4) {
        if(option == 1)
        	addressbook.addNewContact();
        else if(option == 2) {
        	System.out.println("Enter First Name :");
        	String firstName = in.next();
        	System.out.println("Enter Last Name :");
        	String lastName = in.next();
        	addressbook.editContact(firstName, lastName);
        }
        else if (option == 3)
        	addressbook.showDetails();
        else if(option == 4)
        	break;
        else{
        System.out.println("Please Enter Correct Choice");
        
        }
        System.out.println("Enter Choice");
        option = in.nextInt();
        }
		System.out.println("Thank You");
}
}