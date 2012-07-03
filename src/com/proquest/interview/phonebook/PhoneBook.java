package com.proquest.interview.phonebook;

import java.util.List;

public interface PhoneBook {
	/**
	 * find the specific person info given its name
	 * @param firstName
	 * @param lastName
	 * @return Person object
	 */
	public Person findPerson(String firstName, String lastName);
	
	/**
	 * Add new person info into database
	 * @param newPerson
	 */
	public void addPerson(Person newPerson);
	
	/**
	 * Get list of persons in the phonebook
	 * @return phonebook
	 */
	List fetchPhoneBook();
}
