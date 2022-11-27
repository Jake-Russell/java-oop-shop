package uk.ac.lboro.jakerussell.cas.data;

import uk.ac.lboro.jakerussell.cas.enums.Colour;
import uk.ac.lboro.jakerussell.cas.enums.ConnectivityType;
import uk.ac.lboro.jakerussell.cas.enums.KeyboardLayout;
import uk.ac.lboro.jakerussell.cas.enums.KeyboardType;
import uk.ac.lboro.jakerussell.cas.enums.ProductType;

/**
 * Keyboard is responsible for representing a keyboard on the system
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class Keyboard extends Product {
	private KeyboardLayout layout;
	private KeyboardType type;


	/**
	 * Constructor is responsible for creating a keyboard from given values
	 * 
	 * @param barcode         the keyboard's barcode
	 * @param type            the keyboard's type
	 * @param brand           the keyboard's brand
	 * @param colour          the keyboard's colour
	 * @param connectivity    the keyboard's connectivity
	 * @param quantityInStock the keyboard's quantity in stock
	 * @param retailCost      the keyboard's retail cost
	 * @param originalCost    the keyboard's original cost
	 * @param layout
	 */
	public Keyboard(int barcode, KeyboardType type, String brand, Colour colour, ConnectivityType connectivity, int quantityInStock, double retailCost, double originalCost, KeyboardLayout layout) {
		super(barcode, ProductType.KEYBOARD, brand, colour, connectivity, quantityInStock, retailCost, originalCost);
		this.type = type;
		this.layout = layout;
	}


	/**
	 * Returns a String of all the keyboard details, used in the CLI application
	 * 
	 * @return a String of all the keyboard details
	 */
	@Override
	public String toString() {
		return "Barcode: " + this.getBarcode() + ", Product Type: " + this.getProductType() + ", Keyboard Type: " + this.type + ", Brand: " + this.getBrand() + ", Colour: " + this.getColour()
				+ ", Connectivity Type: " + this.getConnectivity() + ", Quantity In Stock: " + this.getQuantityInStock() + ", Original Cost: " + this.getOriginalCost() + ", Retail Cost: "
				+ this.getRetailCost() + ", Keyboard Layout: " + this.layout;
	}


	/**
	 * Returns a String of all the keyboard details, for adding to Stock.txt
	 * 
	 * @return a String of all the keyboard details
	 */
	@Override
	public String toFileString() {
		return this.getBarcode() + ", " + this.getProductType() + ", " + this.type + ", " + this.getBrand() + ", " + this.getColour() + ", " + this.getConnectivity() + ", " + this.getQuantityInStock()
				+ ", " + this.getOriginalCost() + ", " + this.getRetailCost() + ", " + this.layout;
	}


	/**
	 * Returns a String of all the keyboard details that a customer can see, used in
	 * the CLI application
	 * 
	 * @return a String of all the keyboard details that a customer can see
	 */
	@Override
	public String toUserString() {
		return "Barcode: " + this.getBarcode() + ", Product Type: " + this.getProductType() + ", Keyboard Type: " + this.type + ", Brand: " + this.getBrand() + ", Colour: " + this.getColour()
				+ ", Connectivity Type: " + this.getConnectivity() + ", Quantity In Stock: " + this.getQuantityInStock() + ", Retail Cost: " + this.getRetailCost() + ", Keyboard Layout: "
				+ this.layout;
	}


	/**
	 * Returns the keyboard layout
	 * 
	 * @return the keyboard layout
	 */
	public KeyboardLayout getLayout() {
		return layout;
	}


	/**
	 * Returns the keyboard type
	 * 
	 * @return the keyboard type
	 */
	public KeyboardType getType() {
		return type;
	}
}
