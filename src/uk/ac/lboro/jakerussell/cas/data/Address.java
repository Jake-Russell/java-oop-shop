package uk.ac.lboro.jakerussell.cas.data;

/**
 * Address is responsible for representing a user's address
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class Address {

	private int houseNumber;
	private String postcode, city;


	/**
	 * Constructor is responsible for creating an address from given values
	 * 
	 * @param houseNumber the house number of the address
	 * @param postcode    the postcode of the address
	 * @param city        the city of the address
	 */
	public Address(int houseNumber, String postcode, String city) {
		this.houseNumber = houseNumber;
		this.postcode = postcode;
		this.city = city;
	}


	/**
	 * Returns the postcode of the address
	 * 
	 * @return postcode the postcode of the address
	 */
	public String getPostcode() {
		return this.postcode;
	}
}
