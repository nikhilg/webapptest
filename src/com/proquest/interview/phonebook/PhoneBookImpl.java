package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	private static final String ADD_PERSON_QUERY = "INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES(?,?,?)";

	private static final String FETCH_ALL_PERSONS = "SELECT * FROM PHONEBOOK";

	private static final String FIND_PERSON_QUERY = "SELECT * FROM PHONEBOOK WHERE NAME = ?";
	
	private List people;
	
	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	/* (non-Javadoc)
	 * @see com.proquest.interview.phonebook.PhoneBook#addPerson(com.proquest.interview.phonebook.Person)
	 */
	@Override
	public void addPerson(Person newPerson) {
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(ADD_PERSON_QUERY);
			ps.setString(1, newPerson.getName());
			ps.setString(2, newPerson.getPhoneNumber());
			ps.setString(3, newPerson.getAddress());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResources(null, ps, connection);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.proquest.interview.phonebook.PhoneBook#fetchPhoneBook()
	 */
	@Override
	public List fetchPhoneBook(){
		List phoneBook = new ArrayList();
		try {
			connection = DatabaseUtil.getConnection();
			ps = connection.prepareStatement(FETCH_ALL_PERSONS);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Person p = new Person(rs.getString(1), rs.getString(2), rs.getString(3) );
				phoneBook.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResources(rs, ps, connection);
		}

		return phoneBook;
	}

	/* (non-Javadoc)
	 * @see com.proquest.interview.phonebook.PhoneBook#findPerson(java.lang.String, java.lang.String)
	 */
	@Override
	public Person findPerson(String firstName, String lastName) {
		Person person = null;
		try {
			connection = DatabaseUtil.getConnection();
			ps = connection.prepareStatement(FIND_PERSON_QUERY);
			ps.setString(1, firstName + " " + lastName);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				person = new Person();
				person.setName(rs.getString(1));
				person.setPhoneNumber(rs.getString(2));
				person.setAddress(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResources(rs, ps, connection);
		}
		return person;
	}
	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database
		PhoneBook phoneBook = new PhoneBookImpl();

		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/ 		
		Person person = new Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI");		
		phoneBook.addPerson(person);

		person = new Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI");		
		phoneBook.addPerson(person);
		

		// TODO: print the phone book out to System.out
		List people = phoneBook.fetchPhoneBook();
		System.out.println("************** Task 1 ****************");
		if (!people.isEmpty()) {
			System.out.println("***********         Printing Phone Book    ***********");
			for (int i = 0; i < people.size(); i++) {
				Person p = (Person) people.get(i);
				System.out.format("Information of Person : %d \n", i + 1 );
				System.out.println("Name: " + p.getName());
				System.out.println("Phone Number: " + p.getPhoneNumber());
				System.out.println("Address : " + p.getAddress());
			}
		}else{
			System.out.println("PhoneBook is empty, Pleae add some person.");
		}
		System.out.println();

		
		// TODO: find Cynthia Smith and print out just her entry
		person = phoneBook.findPerson("Cynthia", "Smith");
		System.out.println();
		System.out.println("************** Task 2 ****************");
		System.out.println("Information of Person:");
		System.out.println("Name: " +  person.getName());
		System.out.println("Phone Number: " +  person.getPhoneNumber());
		System.out.println("Address : " +  person.getAddress());

		
		// TODO: insert the new person objects into the database
		// Could not understand this task, already doing similar thing in lines 98-106
	}
}
