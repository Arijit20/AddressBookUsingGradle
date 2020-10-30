package com.cg.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class AddressBookDBService {
	
	private Connection getConnection() throws AddressBookDBException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_database?allowPublicKeyRetrieval=true&&useSSL=false";
		String userName = "root";
		String password = "arijit123dey";
		try {
			return DriverManager.getConnection(jdbcURL, userName, password);
		} catch (SQLException e) {
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
	}

	public List<Contacts> readContacts() throws AddressBookDBException {
		// TODO Auto-generated method stub
		String sql = "select c.first_name, c.last_name, a.street, a.city, a.state, a.zip, c.phone, c.email"+
		              " from contacts c, address a where c.id = a.id ;";
		//List<Contacts> contactList = new ArrayList<Contacts>();
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			AddressBook.contactList = (LinkedList<Contacts>) getContactData(result);
		}catch(SQLException e) {
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		return AddressBook.contactList;
	}
	
	private List<Contacts> getContactData(ResultSet result) throws AddressBookDBException{
		List<Contacts> tempContactList = new LinkedList<Contacts>();
		try {
			while(result.next()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String address = result.getString("street");
				String city = result.getString("city");
				String state = result.getString("state");
				String zip = result.getString("zip");
				String phoneNo = result.getString("phone");
				String email = result.getString("email");
				tempContactList.add(new Contacts(firstName, lastName, address, city, state, zip, phoneNo, email));
			}
		}catch(SQLException e) {
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		return tempContactList;
		}

	public int updatePersonAddress(String firstName, String column, String value) throws AddressBookDBException {
		// TODO Auto-generated method stub
		String sql =String.format ("update address a,contacts c set a.%s ='%s' where a.id = c.id and c.first_name = '%s';", column, value, firstName);
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		}catch(SQLException e) {
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
	}

	public Contacts isAddressBookInSyncWithDB(String firstName) throws AddressBookDBException {
		// TODO Auto-generated method stub
		List<Contacts> tempList = this.readContacts();
		return tempList.stream()
				.filter(contact -> contact.getFirstName().contentEquals(firstName))
				.findFirst()
				.orElse(null);
	}	
}



