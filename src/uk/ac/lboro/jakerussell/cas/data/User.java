package uk.ac.lboro.jakerussell.cas.data;

import uk.ac.lboro.jakerussell.cas.enums.UserType;

/**
 * User is responsible for representing a user to the system
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public abstract class User {
	private int userID;
	private UserType userType;
	private String username, surname;
	private Address address;


	/**
	 * Constructor is responsible for creating a user from given values. Since User
	 * is an abstract class, this is only used by child classes of User
	 * 
	 * @param userID   the user's userID
	 * @param username the user's username
	 * @param surname  the user's surname
	 * @param address  the user's address
	 * @param userType the user's user type
	 */
	public User(int userID, String username, String surname, Address address, UserType userType) {
		this.userID = userID;
		this.username = username;
		this.surname = surname;
		this.address = address;
		this.userType = userType;
	}


	/**
	 * Returns the user ID of the user
	 * 
	 * @return the user ID of the user
	 */
	public int getUserID() {
		return userID;
	}


	/**
	 * Returns the user type of the user
	 * 
	 * @return the user type of the user
	 */
	public UserType getUserType() {
		return userType;
	}


	/**
	 * Returns the username of the user
	 * 
	 * @return the username of the user
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * Returns the surname of the user
	 * 
	 * @return the surname of the user
	 */
	public String getSurname() {
		return surname;
	}


	/**
	 * Returns the address of the user
	 * 
	 * @return the address of the year
	 */
	public Address getAddress() {
		return address;
	}
}
