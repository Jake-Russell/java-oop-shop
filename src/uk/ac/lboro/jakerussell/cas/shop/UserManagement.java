package uk.ac.lboro.jakerussell.cas.shop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import uk.ac.lboro.jakerussell.cas.data.Address;
import uk.ac.lboro.jakerussell.cas.data.Admin;
import uk.ac.lboro.jakerussell.cas.data.Customer;
import uk.ac.lboro.jakerussell.cas.data.ShoppingBasket;
import uk.ac.lboro.jakerussell.cas.data.User;
import uk.ac.lboro.jakerussell.cas.enums.UserType;

/**
 * UserManagement is responsible for reading in UserAccounts.txt, and creating
 * an instance of either an Admin or a Customer for each user type in the file
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class UserManagement {
	private Set<User> users = new HashSet<>();


	/**
	 * Constructor reads in UserAccounts.txt, and converts the text into objects
	 * 
	 * @throws FileNotFoundException if UserAccounts.txt cannot be found
	 */
	public UserManagement() throws FileNotFoundException {
		File inputFile = new File("UserAccounts.txt");
		Scanner fileScanner = new Scanner(inputFile);

		int lineNumber = 0;

		while (fileScanner.hasNextLine()) {
			lineNumber++;
			try {
				String line = fileScanner.nextLine();
				String[] tempArray = line.split(", ");

				if (tempArray[6].toUpperCase().equals(UserType.ADMIN.toString())) {
					try {
						Address address = new Address(Integer.parseInt(tempArray[3]), tempArray[4], tempArray[5]);
						Admin admin = new Admin(Integer.parseInt(tempArray[0]), tempArray[1], tempArray[2], address, UserType.valueOf(tempArray[6].toUpperCase()));
						users.add(admin);
					} catch (Exception e) {
						String exceptionString = e.toString();
						throw new Exception("Address." + exceptionString.substring(exceptionString.lastIndexOf(": ") + 2));
					}
				}

				else if (tempArray[6].toUpperCase().equals(UserType.CUSTOMER.toString())) {
					Address address = new Address(Integer.parseInt(tempArray[3]), tempArray[4], tempArray[5]);
					ShoppingBasket shoppingBasket = new ShoppingBasket();
					Customer customer = new Customer(Integer.parseInt(tempArray[0]), tempArray[1], tempArray[2], address, UserType.valueOf(tempArray[6].toUpperCase()), shoppingBasket);

					users.add(customer);
				}

				else {
					throw new Exception("UserType." + line.substring(line.lastIndexOf(", ") + 2));
				}
			}

			catch (Exception e) {
				String exceptionString = e.toString();
				if (exceptionString.contains("UserType")) {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". There is no such User Type: " + exceptionString.substring(exceptionString.lastIndexOf('.') + 1));
				} else if (exceptionString.contains("ArrayIndexOutOfBounds")) {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". There are not enough fields (" + exceptionString.substring(exceptionString.lastIndexOf(" ") + 1)
							+ ") for the record. It should have " + exceptionString.substring(exceptionString.indexOf("Index ") + 6, exceptionString.indexOf("Index ") + 7) + " fields.");
				} else {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". The Address format is incorrect. " + exceptionString.substring(exceptionString.lastIndexOf('.') + 1));
				}
			}
		}
	}


	/**
	 * Returns the set of all users in UserAccounts.txt
	 * 
	 * @return the set of all users in UserAccounts.txt
	 */
	public Set<User> getUsers() {
		return users;
	}


	/**
	 * Gets a user by their userID
	 * 
	 * @param userID the user's ID
	 * @return the matching user or null if not found
	 */
	public User getUserByUserID(int userID) {
		for (User user : this.getUsers()) {
			if (user.getUserID() == userID) {
				return user;
			}
		}
		return null;
	}


	/**
	 * Creates a customer from the given user ID
	 * 
	 * @param userID the user ID to create the customer from
	 * @return the created customer
	 */
	public Customer createCustomerFromUserID(int userID) {
		User user = getUserByUserID(userID);
		Customer customer = new Customer(userID, user.getUsername(), user.getSurname(), user.getAddress(), user.getUserType(), new ShoppingBasket());
		return customer;
	}
}
