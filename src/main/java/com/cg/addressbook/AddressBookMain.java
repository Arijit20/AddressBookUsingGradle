package com.cg.addressbook;
import java.util.Scanner;

public class AddressBookMain {
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		boolean b = false;
		System.out.println("Welcome to Addressbook");	
		
		Contacts person = new Contacts();
		System.out.print("First Name: ");
		while(!b) {
		b = person.setFirstName(in.next());
		}
		b = false;
		System.out.print("Last Name: ");
		while(!b) {
		b = person.setLastName(in.next());
		}
		b = false;
		System.out.print("Address: ");
		while(!b) {
		b = person.setAddress(in.next());
		}
		b = false;
		System.out.print("City: ");
		while(!b) {
		b = person.setCity(in.next());
		}
		b = false;
		System.out.print("State: ");
		while(!b) {
		b = person.setState(in.next());
		}
		b = false;
		System.out.print("Zip: ");
		while(!b) {
		b = person.setZip(in.next());
		}
		b = false;
		System.out.print("Phone Number: ");
		while(!b) {
		b = person.setPhoneNo(in.next());
		}
		b = false;
		System.out.print("Email: ");
		while(!b) {
		b = person.setEmail(in.next());
		}
		System.out.println("Thank You");
	} 
}
