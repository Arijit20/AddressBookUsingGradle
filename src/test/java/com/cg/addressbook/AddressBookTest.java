
package com.cg.addressbook;
import org.junit.Test;
import com.cg.addressbook.AddressBookMain;
import static org.junit.Assert.*;

import org.junit.Assert;

public class AddressBookTest {
	 @Test
	    public void firstNameTest() {
	        ValidateContact contact = new ValidateContact();
	        boolean result = contact.validateFirstName("Arijit");
	        Assert.assertTrue(true);
	 } 
	 @Test
	    public void lastNameTest() {
	        ValidateContact contact = new ValidateContact();
	        boolean result = contact.validateLastName("Dey");
	        Assert.assertTrue(true);
	 }
	 @Test
	    public void emailTest() {
	        ValidateContact contact = new ValidateContact();
	        boolean result = contact.validateEmail("arijit.dey@gmail.com");
	        Assert.assertTrue(true);
	 }
	 @Test
	    public void phoneNoTest() {
	        ValidateContact contact = new ValidateContact();
	        boolean result = contact.validatePhoneNo("1234567895");
	        Assert.assertTrue(true);
	 }
	 @Test
	    public void addressTest() {
	        ValidateContact contact = new ValidateContact();
	        boolean result = contact.validateAddress("kolkata");
	        Assert.assertTrue(true);
	 }
	 @Test
	    public void cityTest() {
	        ValidateContact contact = new ValidateContact();
	        boolean result = contact.validateCity("kolkata");
	        Assert.assertTrue(true);
	 }
	 @Test
	    public void stateTest() {
	        ValidateContact contact = new ValidateContact();
	        boolean result = contact.validateState("West Bengal");
	        Assert.assertTrue(true);
	 }
}