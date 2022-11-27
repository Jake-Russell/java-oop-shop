package uk.ac.lboro.jakerussell.cas.data;

import java.util.ArrayList;

import uk.ac.lboro.jakerussell.cas.enums.PaymentMethod;
import uk.ac.lboro.jakerussell.cas.enums.ProductStatus;
import uk.ac.lboro.jakerussell.cas.enums.UserType;

/**
 * Customer is responsible for representing a customer to the system
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class Customer extends User {
	private ShoppingBasket shoppingBasket;


	/**
	 * Constructor is responsible for creating a customer from given values
	 * 
	 * @param userID         the user ID of the customer
	 * @param username       the username of the customer
	 * @param surname        the surname of the customer
	 * @param address        the address of the customer
	 * @param userType       the user type of the customer
	 * @param shoppingBasket the user's shopping basket
	 */
	public Customer(int userID, String username, String surname, Address address, UserType userType, ShoppingBasket shoppingBasket) {
		super(userID, username, surname, address, userType);
		this.shoppingBasket = shoppingBasket;
	}


	/**
	 * Returns the current user's shopping basket
	 * 
	 * @return shoppingBasket the user's current shopping basket
	 */
	public ShoppingBasket getShoppingBasket() {
		return this.shoppingBasket;
	}


	/**
	 * Adds a given product to the customer's shopping basket
	 * 
	 * @param product the product to be added to the customer's shopping basket
	 * @return true if the addition of the product has been successful, false
	 *         otherwise
	 */
	public boolean addToShoppingBasket(Product product) {
		if (product.getQuantityInStock() != 0) {
			shoppingBasket.addItem(product);
			int newQuantityInStock = product.getQuantityInStock() - 1;
			product.setQuantityInStock(newQuantityInStock);
			return true;
		}
		return false;
	}


	/**
	 * Removes a given product from the customer's shopping basket
	 * 
	 * @param product the product to be removed from the customer's shopping basket
	 * @return true if the removal of the product has been successful, false
	 *         otherwise
	 */
	public boolean removeFromShoppingBasket(Product product) {
		if (this.shoppingBasket.getItems().contains(product)) {
			int newQuantityInStock = product.getQuantityInStock() + 1;
			product.setQuantityInStock(newQuantityInStock);
			shoppingBasket.removeItem(product);
			return true;
		}
		return false;
	}


	/**
	 * Creates the lines for the purchased products which need to be added to
	 * ActivityLog.txt, used in the CLI application
	 * 
	 * @param paymentChoice the payment method which the user has chosen
	 * @return productDetails the ArrayList of lines for the purchased items
	 */
	public ArrayList<String> getPurchasedProductLines(String paymentChoice) {
		ArrayList<String> productDetails = new ArrayList<>();

		for (Product product : this.shoppingBasket.getItems()) {
			String lineToAdd = this.getUserID() + ", " + this.getAddress().getPostcode() + ", " + product.getBarcode() + ", " + product.getRetailCost() + ", "
					+ this.shoppingBasket.getQuantityOfItem(product.getBarcode()) + ", " + ProductStatus.PURCHASED.toString().toLowerCase() + ", " + formatPaymentChoice(paymentChoice);
			if (!productDetails.contains(lineToAdd)) {
				productDetails.add(lineToAdd);
			}
		}

		removeProduct((ArrayList<Product>) this.shoppingBasket.getItems(), ProductStatus.PURCHASED);

		return productDetails;
	}


	/**
	 * Creates the lines for the purchased products which need to be added to
	 * ActivityLog.txt, used in the GUI application
	 * 
	 * @param paymentChoice the payment method which the user has chosen
	 * @return productDetails the ArrayList of lines for the purchased product
	 */
	public ArrayList<String> getPurchasedProductLines(PaymentMethod paymentChoice) {
		ArrayList<String> productDetails = new ArrayList<>();

		for (Product product : this.shoppingBasket.getItems()) {
			String lineToAdd = this.getUserID() + ", " + this.getAddress().getPostcode() + ", " + product.getBarcode() + ", " + product.getRetailCost() + ", "
					+ this.shoppingBasket.getQuantityOfItem(product.getBarcode()) + ", " + ProductStatus.PURCHASED.toString().toLowerCase() + ", " + formatPaymentChoice(paymentChoice);
			if (!productDetails.contains(lineToAdd)) {
				productDetails.add(lineToAdd);
			}
		}

		removeProduct((ArrayList<Product>) this.shoppingBasket.getItems(), ProductStatus.PURCHASED);

		return productDetails;
	}


	/**
	 * Creates the lines for the saved basket which need to be added to
	 * ActivityLog.txt
	 * 
	 * @return saveBasketDetails the ArrayList of lines for the saved basket
	 */
	public ArrayList<String> getSavedProductLines() {
		ArrayList<String> saveBasketDetails = new ArrayList<>();

		for (Product product : this.shoppingBasket.getItems()) {
			String lineToAdd = this.getUserID() + ", " + this.getAddress().getPostcode() + ", " + product.getBarcode() + ", " + product.getRetailCost() + ", "
					+ this.shoppingBasket.getQuantityOfItem(product.getBarcode()) + ", " + ProductStatus.SAVED.toString().toLowerCase() + ", ";

			if (!saveBasketDetails.contains(lineToAdd)) {
				saveBasketDetails.add(lineToAdd);
			}
		}

		removeProduct((ArrayList<Product>) this.shoppingBasket.getItems(), ProductStatus.SAVED);

		return saveBasketDetails;
	}


	/**
	 * Creates the lines for the cancelled basket which need to be added to
	 * ActivityLog.txt
	 * 
	 * @return deletedBasketDetails the ArrayList of lines for the cancelled basket
	 */
	public ArrayList<String> getCancelledProductLines() {
		ArrayList<String> deletedBasketDetails = new ArrayList<>();

		for (Product product : this.shoppingBasket.getItems()) {
			String lineToAdd = this.getUserID() + ", " + this.getAddress().getPostcode() + ", " + product.getBarcode() + ", " + product.getRetailCost() + ", "
					+ this.shoppingBasket.getQuantityOfItem(product.getBarcode()) + ", " + ProductStatus.CANCELLED.toString().toLowerCase() + ", ";

			if (!deletedBasketDetails.contains(lineToAdd)) {
				deletedBasketDetails.add(lineToAdd);
			}
		}

		removeProduct((ArrayList<Product>) this.shoppingBasket.getItems(), ProductStatus.CANCELLED);
		return deletedBasketDetails;
	}


	private String formatPaymentChoice(String paymentChoice) {
		if (paymentChoice.toUpperCase().equals("P")) {
			return "PayPal";
		}
		return "Credit Card";
	}


	private String formatPaymentChoice(PaymentMethod paymentChoice) {
		if (paymentChoice.equals(PaymentMethod.PAYPAL)) {
			return "PayPal";
		}
		return "Credit Card";
	}


	private void removeProduct(ArrayList<Product> productList, ProductStatus status) {
		Product[] productArray = new Product[productList.size()];
		productList.toArray(productArray);

		for (int i = 0; i < productArray.length; i++) {
			if (status.equals(ProductStatus.PURCHASED)) {
				shoppingBasket.removeItem(productArray[i]);
			} else {
				removeFromShoppingBasket(productArray[i]);
			}
		}
	}
}
