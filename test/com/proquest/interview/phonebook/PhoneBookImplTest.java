package com.proquest.interview.phonebook;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImplTest extends TestCase {
	
	public PhoneBook pb = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		pb = new PhoneBookImpl();
		DatabaseUtil.initDB();		
	};
	
	@Test
	public void testFetchPhoneBook(){
		// Phone book has two person so it should return two
		assertEquals(2, pb.fetchPhoneBook().size());
	}
	
	@Test
	public void testAddPerson() {
		List initialPhoneBook = pb.fetchPhoneBook();		
		assertEquals(2, initialPhoneBook.size());
		
		Person p = new Person("Proquest", "(734) 734 - 734", "Ann Arbor, MI");
		pb.addPerson(p);
		
		List postTransactionPhoneBook = pb.fetchPhoneBook();
		assertEquals(3, postTransactionPhoneBook.size());
	}
	
	@Test
	public void testFindPerson(){
		// Test 'Chris Johnson','(321) 231-7876', '452 Freeman Drive, Algonac, MI'
		Person person = pb.findPerson("Chris", "Johnson");
		
		assertEquals("Chris Johnson", person.getName());
		assertEquals("(321) 231-7876", person.getPhoneNumber());
		assertEquals("452 Freeman Drive, Algonac, MI", person.getAddress());
		
		// Nikhil Gupta in not in proquest phone book
		person = pb.findPerson("Nikhil", "Gupta");
		assertNull(person);
	}
	
	@Test
	public void testShouldAddNewPersonAndFindAddedPerson() {
		// Adding 'Nikhil Gupta','(513) 675-675', '452 Freeman Drive, Algonac, OH'
		Person person = new Person("Nikhil Gupta","(513) 675-675","452 Freeman Drive, Algonac, OH");
		pb.addPerson(person);
		
		Person personDB = pb.findPerson("Nikhil", "Gupta");
		
		assertEquals("Nikhil Gupta", personDB.getName());
		assertEquals("(513) 675-675", personDB.getPhoneNumber());
		assertEquals("452 Freeman Drive, Algonac, OH", personDB.getAddress());
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		DatabaseUtil.clearTestDB();
	}
}
