package uk.ac.lboro.jakerussell.cas.cli;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import uk.ac.lboro.jakerussell.cas.common.LogFileUtils;
import uk.ac.lboro.jakerussell.cas.data.Admin;
import uk.ac.lboro.jakerussell.cas.data.Customer;
import uk.ac.lboro.jakerussell.cas.data.ShoppingBasket;
import uk.ac.lboro.jakerussell.cas.data.Product;
import uk.ac.lboro.jakerussell.cas.data.User;
import uk.ac.lboro.jakerussell.cas.enums.UserType;
import uk.ac.lboro.jakerussell.cas.shop.ShopManagement;
import uk.ac.lboro.jakerussell.cas.shop.UserManagement;

/**
 * CommandLineApplication is the main entry point for the CLI application, and
 * is responsible for displaying a log in screen in the command line and logging
 * the user into the system
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class CommandLineApplication {
	private ShopManagement shopManagement;
	private UserManagement userManagement;


	/**
	 * The main method which invokes the creation of a new CommandLineApplicaiton
	 * instance
	 * 
	 * @param args unused
	 * @throws FileNotFoundException if either the Stock.txt or UserAccounts.txt
	 *                               files cannot be found when creating
	 *                               ShopManagement and UserManagement instances
	 */
	public static void main(String[] args) throws FileNotFoundException {
		CommandLineApplication commandLineApplication = new CommandLineApplication();
	}


	/**
	 * Constructor outputs login screen text to the command line, and gets user
	 * input for the user to login as
	 * 
	 * @throws FileNotFoundException if either the Stock.txt or UserAccounts.txt
	 *                               files cannot be found when creating
	 *                               ShopManagement and UserManagement instances
	 */
	public CommandLineApplication() throws FileNotFoundException {
		shopManagement = new ShopManagement();
		userManagement = new UserManagement();

		System.out.println("Welcome to the Computer Accessories Shop.\nPlease choose a User ID from the below list to login as.");
		for (User user : userManagement.getUsers()) {
			System.out.println("User ID: " + user.getUserID());
		}

		User user = getUserLogin();
		System.out.println("Welcome back " + user.getUsername() + ". You are a " + user.getUserType().toString() + " of the Computer Accessories Shop.");

		if (user.getUserType() == UserType.ADMIN) {
			Admin admin = new Admin(user.getUserID(), user.getUsername(), user.getSurname(), user.getAddress(), user.getUserType());
			adminMenu(admin);
		} else {
			Customer customer = new Customer(user.getUserID(), user.getUsername(), user.getSurname(), user.getAddress(), user.getUserType(), new ShoppingBasket());
			customerMenu(customer);
		}
	}


	private void adminMenu(Admin admin) {
		System.out.println("\nWhat would you like to do with the system? Please select an option from the list below: ");
		System.out.println("1 - View All Products and their Attributes \n2 - Add Product to Current Stock List");

		int menuChoice = new Scanner(System.in).nextInt();
		switch (menuChoice) {
		case (1):
			productOutput(shopManagement.getProducts(), admin.getUserType());
			break;
		case (2):
			Product productToAdd = createProductToAdd();
			shopManagement.addProduct(productToAdd);
			shopManagement.saveProductsToStockFile();
			break;
		default:
			System.out.println("This is an invalid input. Please select a menu option from 1 to 2.\n");
		}
		adminMenu(admin);
	}


	private void customerMenu(Customer customer) {
		System.out.println("\nWhat would you like to do with the system? Please select an option from the list below: ");
		System.out.println("1 - View All Products and their Attributes \n2 - Search For an Item \n3 - Add Item to Shopping Basket "
				+ "\n4 - Remove Item from Shopping Basket \n5 - View Current Shopping Basket \n6 - Pay For Items in Shopping Basket " + "\n7 - Cancel Shopping Basket \n8 - Save For Later");

		int menuChoice = new Scanner(System.in).nextInt();
		int basketSize = customer.getShoppingBasket().getNumberOfItems();

		switch (menuChoice) {
		case (1):
			productOutput(shopManagement.getProducts(), customer.getUserType());
			break;
		case (2):
			productOutput(searchForItem(), customer.getUserType());
			break;
		case (3):
			getUserInputForAddingToBasket(customer);
			shopManagement.saveProductsToStockFile();
			break;
		case (4):
			getUserInputForRemovingFromBasket(customer);
			shopManagement.saveProductsToStockFile();
			break;
		case (5):
			System.out.println("Your current shopping basket is as follows: ");
			System.out.println(customer.getShoppingBasket().toString());
			for (Product product : customer.getShoppingBasket().getItems()) {
				System.out.println(product.toString());
			}
			break;
		case (6):
			boolean validPaymentInput = false;
			String paymentChoice = null;

			if (basketSize == 0) {
				System.out.println("You are unable to purchase a shopping basket with 0 items in it.");
			} else {
				System.out.print(
						"Your basket has " + basketSize + " items in it.\nAre you sure you wish to purchase these items at a total price of " + customer.getShoppingBasket().getTotalPrice() + "? ");
				String purchaseConfirmation = new Scanner(System.in).next();
				if (purchaseConfirmation.toUpperCase().equals("Y")) {
					while (!validPaymentInput) {
						System.out.print("\nWould you like to pay for your shopping basket via PayPal or Credit Card?\nEnter P for Paypal or C for Credit Card: ");
						paymentChoice = new Scanner(System.in).next();

						if (paymentChoice.toUpperCase().equals("P")) {
							PaymentUtils.doPayPalPayment();
							validPaymentInput = true;
						} else if (paymentChoice.toUpperCase().equals("C")) {
							PaymentUtils.doCreditCardPayment();
							validPaymentInput = true;
						} else {
							System.out.println("This is an invalid input. Please choose either PayPal or Credit Card.");
						}
					}

					ArrayList<String> purchasedProductLines = customer.getPurchasedProductLines(paymentChoice);
					LogFileUtils.writeToLogFile(purchasedProductLines);
				}
			}
			break;
		case (7):
			if (basketSize == 0) {
				System.out.println("You are unable to cancel a shopping basket with 0 items in it.");
			} else {
				System.out.print("Are you sure that you wish to cancel your current shopping basket with " + basketSize + " items? ");
				String cancelConfirmation = new Scanner(System.in).next();
				if (cancelConfirmation.toUpperCase().equals("Y")) {
					ArrayList<String> cancelledProductLines = customer.getCancelledProductLines();
					LogFileUtils.writeToLogFile(cancelledProductLines);
				}
			}
			shopManagement.saveProductsToStockFile();
			break;
		case (8):
			if (basketSize == 0) {
				System.out.println("You are unable to save a shopping basket with 0 items in it.");
			} else {
				System.out.print("Are you sure that you wish to save your current shopping basket with " + basketSize + " items? ");
				String saveConfirmation = new Scanner(System.in).next();
				if (saveConfirmation.toUpperCase().equals("Y")) {
					ArrayList<String> savedProductLines = customer.getSavedProductLines();
					LogFileUtils.writeToLogFile(savedProductLines);
				}
			}
			shopManagement.saveProductsToStockFile();
			break;
		default:
			System.out.println("This is an invalid input. Please select a menu option from 1 to 6.\n");
		}
		customerMenu(customer);
	}


	private void getUserInputForAddingToBasket(Customer customer) {
		boolean validBarcodeInput = false;
		while (!validBarcodeInput) {
			System.out.print("\nPlease enter the barcode of the item you would like to add to your shopping basket: ");
			int barcodeToAdd = new Scanner(System.in).nextInt();

			Product product = shopManagement.getProductByBarcode(barcodeToAdd);
			if (product != null) {
				if (customer.addToShoppingBasket(shopManagement.getProductByBarcode(barcodeToAdd))) {
					System.out.println(product.getProductType().toString() + " with barcode " + product.getBarcode() + " successfully added to basket.");
					System.out.println(customer.getShoppingBasket().toString());
					validBarcodeInput = true;
				} else {
					System.out.println("This item is currently out of stock. Please enter the barcode of a different item.");
				}
			} else {
				System.out.println("This barcode is not in the stock list. Please enter a different barcode.");
			}
		}
	}


	private void getUserInputForRemovingFromBasket(Customer customer) {
		boolean validBarcodeInput = false;
		while (!validBarcodeInput) {
			System.out.print("\nPlease enter the barcode of the item you would like to remove from your shopping basket: ");
			int barcodeToRemove = new Scanner(System.in).nextInt();

			Product product = shopManagement.getProductByBarcode(barcodeToRemove);
			if (product != null) {
				if (customer.removeFromShoppingBasket(product)) {
					System.out.println(product.getProductType().toString() + " with barcode " + product.getBarcode() + " successfully removed from basket.");
					System.out.println(customer.getShoppingBasket().toString());
					validBarcodeInput = true;
				} else {
					System.out.println("This item is not in your basket. Please enter the barcode of a different item.");
				}
			} else {
				System.out.println("This barcode is not in the stock list. Please enter a different barcode.");
			}
		}
	}


	private Product createProductToAdd() {
		try {
			System.out.print("\nPlease enter the product barcode: ");
			int barcode = new Scanner(System.in).nextInt();
			if (shopManagement.getProductByBarcode(barcode) != null) {
				System.out.println("A product with this barcode already exists. Please enter a unique barcode.");
				return createProductToAdd();
			}
			return uk.ac.lboro.jakerussell.cas.cli.ProductUtils.createProduct(barcode);
		} catch (Exception e) {
			System.out.println("Invalid input " + e.toString().substring(e.toString().lastIndexOf('.') + 1));
			return createProductToAdd();
		}
	}


	private void productOutput(Set<Product> products, UserType userType) {
		List<Product> sortedProducts = uk.ac.lboro.jakerussell.cas.common.ProductUtils.sortProductsByQuantityInStock(products);
		if (userType == UserType.ADMIN) {
			for (Product product : sortedProducts) {
				System.out.println(product.toString());
			}
		} else {
			for (Product product : sortedProducts) {
				System.out.println(product.toUserString());
			}
		}
	}


	private User getUserLogin() {
		int userID = 0;
		User user = null;
		try {
			do {
				System.out.print("\nPlease enter the user ID of the user who you would like to login to the system as: ");
				int inputID = new Scanner(System.in).nextInt();
				userID = inputID;
				user = userManagement.getUserByUserID(userID);
				if (user == null) {
					System.out.println("There is no user with the ID " + userID);
				}
			} while (user == null);
		}

		catch (Exception e) {
			System.out.println("This is an invalid input. The user ID entered should be a numeric value.");
			return getUserLogin();
		}
		return user;
	}


	private Set<Product> searchForItem() {
		System.out.print("\nWould you like to filter the search by a specific brand? ");
		String brandChoice = new Scanner(System.in).next();
		String brand = null;
		boolean filterByBrand = false;
		if (brandChoice.toUpperCase().equals("Y")) {
			System.out.print("Which brand would you like to search for? ");
			brand = new Scanner(System.in).next();
			filterByBrand = true;
		} else if (!brandChoice.toUpperCase().equals("N")) {
			System.out.println("This is an invalid input. Please enter either Y or N.");
			return searchForItem();
		}

		System.out.print("Would you like to filter the search by keyboards with a UK layout? ");
		String layoutChoice = new Scanner(System.in).next();
		boolean filterByUKKeyboards = false;
		if (layoutChoice.toUpperCase().equals("Y")) {
			filterByUKKeyboards = true;
		} else if (!layoutChoice.toUpperCase().equals("N")) {
			System.out.println("This is an invalid input. Please enter either Y or N.");
			return searchForItem();
		}

		return shopManagement.filterProducts(filterByUKKeyboards, filterByBrand, brand);
	}
}