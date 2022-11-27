package uk.ac.lboro.jakerussell.cas.data;

import uk.ac.lboro.jakerussell.cas.enums.Colour;
import uk.ac.lboro.jakerussell.cas.enums.ConnectivityType;
import uk.ac.lboro.jakerussell.cas.enums.MouseType;
import uk.ac.lboro.jakerussell.cas.enums.ProductType;

/**
 * Mouse is responsible for representing a mouse on the system
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class Mouse extends Product {
	private int numberOfButtons;
	private MouseType type;


	/**
	 * Constructor is responsible for creating a mouse from given values
	 * 
	 * @param barcode         the mouse's barcode
	 * @param type            the mouse's type
	 * @param brand           the mouse's brand
	 * @param colour          the mouse's colour
	 * @param connectivity    the mouse's connectivity
	 * @param quantityInStock the mouse's quantitiy in stock
	 * @param retailCost      the mouse's retail cost
	 * @param originalCost    the mouse's original cost
	 * @param numberOfButtons the mouse's number of buttons
	 */
	public Mouse(int barcode, MouseType type, String brand, Colour colour, ConnectivityType connectivity, int quantityInStock, double retailCost, double originalCost, int numberOfButtons) {
		super(barcode, ProductType.MOUSE, brand, colour, connectivity, quantityInStock, retailCost, originalCost);
		this.type = type;
		this.numberOfButtons = numberOfButtons;
	}


	/**
	 * Returns a String of all the mouse details, used in the CLI application
	 * 
	 * @return a String of all the mouse details
	 */
	@Override
	public String toString() {
		return "Barcode: " + this.getBarcode() + ", Product Type: " + this.getProductType() + ", Mouse Type: " + this.type + ", Brand: " + this.getBrand() + ", Colour: " + this.getColour()
				+ ", Connectivity Type: " + this.getConnectivity() + ", Quantity In Stock: " + this.getQuantityInStock() + ", Original Cost: " + this.getOriginalCost() + ", Retail Cost: "
				+ this.getRetailCost() + ", Number Of Buttons: " + this.numberOfButtons;
	}


	/**
	 * Returns a String of all the mouse details, for adding to Stock.txt
	 * 
	 * @return a String of all the mouse details
	 */
	@Override
	public String toFileString() {
		return this.getBarcode() + ", " + this.getProductType() + ", " + this.type + ", " + this.getBrand() + ", " + this.getColour() + ", " + this.getConnectivity() + ", " + this.getQuantityInStock()
				+ ", " + this.getOriginalCost() + ", " + this.getRetailCost() + ", " + this.numberOfButtons;
	}


	/**
	 * Returns a String of all the mouse details that a customer can see, used in
	 * the CLI application
	 * 
	 * @return a String of all the mouse details that a customer can see
	 */
	@Override
	public String toUserString() {
		return "Barcode: " + this.getBarcode() + ", Product Type: " + this.getProductType() + ", Mouse Type: " + this.type + ", Brand: " + this.getBrand() + ", Colour: " + this.getColour()
				+ ", Connectivity Type: " + this.getConnectivity() + ", Quantity In Stock: " + this.getQuantityInStock() + ", Retail Cost: " + this.getRetailCost() + ", Number Of Buttons: "
				+ this.numberOfButtons;
	}


	/**
	 * Returns the number of buttons the mouse has
	 * 
	 * @return the number of buttons
	 */
	public int getNumberOfButtons() {
		return numberOfButtons;
	}


	/**
	 * Returns the mouse type of the mouse
	 * 
	 * @return the mouse type
	 */
	public MouseType getType() {
		return type;
	}

}
