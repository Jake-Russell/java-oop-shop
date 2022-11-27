package uk.ac.lboro.jakerussell.cas.data;

import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingBasket is responsible for representing a customer's shopping basket.
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class ShoppingBasket {
	private int numberOfItems;
	private double totalPrice;
	private List<Product> items;


	/**
	 * Constructor is responsible for resetting the shopping basket
	 */
	public ShoppingBasket() {
		this.numberOfItems = 0;
		this.totalPrice = 0.0;
		this.items = new ArrayList<>();
	}


	/**
	 * Returns a String of the shopping basket details, used in the CLI application
	 * 
	 * @return the shopping basket details
	 */
	public String toString() {
		return "Number of Items: " + this.numberOfItems + ", Total Price: " + this.totalPrice;
	}


	/**
	 * Returns the items in the shopping basket
	 * 
	 * @return the List of Products that are in the shopping basket
	 */
	public List<Product> getItems() {
		return this.items;
	}


	/**
	 * Returns the number of items in the shopping basket
	 * 
	 * @return the number of items in the shopping basket
	 */
	public int getNumberOfItems() {
		return this.numberOfItems;
	}


	/**
	 * Returns the total price of the items in the shopping basket
	 * 
	 * @return the total price of the items in the shopping basket
	 */
	public double getTotalPrice() {
		return this.totalPrice;
	}


	/**
	 * Adds a given item to the shopping basket
	 * 
	 * @param the product that is to be added to the shopping basket
	 */
	public void addItem(Product product) {
		items.add(product);
		numberOfItems++;
		totalPrice += product.getRetailCost();
	}


	/**
	 * Removes a given item from the shopping basket
	 * 
	 * @param the product that is to be removed from the shopping basket
	 */
	public void removeItem(Product product) {
		items.remove(product);
		numberOfItems--;
		totalPrice -= product.getRetailCost();
	}


	/**
	 * Returns the quantity of an item in the basket, given its barcode
	 * 
	 * @param barcode the barcode of the item to check the quantity of
	 * @return the quantity of the given item in the basket
	 */
	public int getQuantityOfItem(int barcode) {
		int quantity = 0;

		for (Product product : this.getItems()) {
			if (product.getBarcode() == barcode) {
				quantity++;
			}
		}
		return quantity;
	}
}
