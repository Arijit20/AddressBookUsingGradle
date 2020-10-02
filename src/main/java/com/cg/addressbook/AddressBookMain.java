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
        System.out.println("Enter 4 to delete contact");
        System.out.println("Enter 5 to exit");
       int option = in.nextInt();
        while(option!= 5) {
        if(option == 1)
        	addressbook.addNewContact();
        else if(option == 2) 
        	addressbook.editContact();
        else if (option == 3)
        	addressbook.showDetail();
        else if(option == 4) 
        	addressbook.deleteContact();
        else if(option == 5)
        	break;
        else
        System.out.println("Please Enter Correct Choice"); 
        
        System.out.println("Enter Choice");
        option = in.nextInt();
        }
		System.out.println("Thank You");
}
}