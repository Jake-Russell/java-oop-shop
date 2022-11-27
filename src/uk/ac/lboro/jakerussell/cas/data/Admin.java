package uk.ac.lboro.jakerussell.cas.data;

import uk.ac.lboro.jakerussell.cas.enums.UserType;

/**
 * Admin is responsible for representing an admin to the system
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class Admin extends User {

	/**
	 * Constructor is responsible for creating an admin from given values
	 * 
	 * @param userID   the user ID of the admin
	 * @param username the username of the admin
	 * @param surname  the surname of the admin
	 * @param address  the address of the admin
	 * @param userType the user type of the admin
	 */
	public Admin(int userID, String username, String surname, Address address, UserType userType) {
		super(userID, username, surname, address, userType);
	}
}
