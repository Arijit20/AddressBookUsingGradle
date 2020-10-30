package com.cg.addressbook;

import java.sql.Connection;
import java.sql.Date;
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

	public int getContactsOnDateRange(LocalDate startDate, LocalDate endDate) throws AddressBookDBException{
		// TODO Auto-generated method stub
		String sql = String.format("SELECT id FROM contacts WHERE start BETWEEN '%s' AND '%s';",
				Date.valueOf(startDate), Date.valueOf(endDate));
		int noOfContacts = 0;
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				noOfContacts++;
			}
		} catch (SQLException e) {
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		return noOfContacts;
	}

	public int retriveBasedOnField(String field, String value) throws AddressBookDBException {
		// TODO Auto-generated method stub
		String sql = String.format("SELECT id FROM address WHERE %s = '%s';",field, value);
		int noOfContacts = 0;
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				noOfContacts++;
			}
		} catch (SQLException e) {
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		return noOfContacts;
	}

	public void addContactToDB(String firstName, String lastName, String address, String city, String state,
			                   String zip, String phoneNo, String email, LocalDate start) throws AddressBookDBException {
		// TODO Auto-generated method stub
		int id = -1;
		Contacts contact = null;
		Connection connection = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		}catch(SQLException e) {
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		try(Statement statement = connection.createStatement()){
			String sql = String.format("INSERT INTO contacts (first_name, last_name, phone, email, book_name, start)"
					+ "VALUES ('%s','%s','%s','%s','book1','%s')",firstName, lastName, phoneNo, email, Date.valueOf(start));
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if(rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next()) id = resultSet.getInt(1);
			}
		}catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
			}
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.INCORRECT_INFO, e.getMessage());
		}
		try(Statement statement = connection.createStatement()){
			String sql = String.format("INSERT INTO address (id, street, city, state, zip)"
					+ "VALUES ('%s','%s','%s','%s','%s')", id, address, city, state, zip);
			int rowAffected = statement.executeUpdate(sql);
			if(rowAffected == 1) {
				 contact = new Contacts(firstName, lastName, address, city, state, zip, phoneNo, email);
			}
		}catch(SQLException e) {
			try {
				connection.rollback();
			}catch(SQLException e1) {
				throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
			}
		}
		try {
			connection.commit();
		}catch(SQLException e) {
			throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		finally {
			if(connection != null)
				try {
					connection.close();
				}catch(SQLException e){
					throw new AddressBookDBException(AddressBookDBException.ExceptionType.CONNECTION_ERROR, e.getMessage());
				}
		}
	}	
}



