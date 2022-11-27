package uk.ac.lboro.jakerussell.cas.cli;

import java.util.Scanner;

import uk.ac.lboro.jakerussell.cas.data.Keyboard;
import uk.ac.lboro.jakerussell.cas.data.Mouse;
import uk.ac.lboro.jakerussell.cas.data.Product;
import uk.ac.lboro.jakerussell.cas.enums.Colour;
import uk.ac.lboro.jakerussell.cas.enums.ConnectivityType;
import uk.ac.lboro.jakerussell.cas.enums.KeyboardLayout;
import uk.ac.lboro.jakerussell.cas.enums.KeyboardType;
import uk.ac.lboro.jakerussell.cas.enums.MouseType;
import uk.ac.lboro.jakerussell.cas.enums.ProductType;

/**
 * ProductUtils is responsible for dealing with any product related utilities
 * that the CLI application requires
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class ProductUtils {

	/**
	 * Creates a product by getting user input through the command line, used in the
	 * CLI application
	 * 
	 * @param barcode the barcode for the product
	 * @return the created product
	 */
	public static Product createProduct(int barcode) {

		System.out.print("Please enter the product type: ");
		ProductType productType = ProductType.valueOf(new Scanner(System.in).next().toUpperCase());

		System.out.print("Please enter the brand: ");
		String brand = new Scanner(System.in).next();

		System.out.print("Please enter the colour: ");
		Colour colour = Colour.valueOf(new Scanner(System.in).next().toUpperCase());

		System.out.print("Please enter the connectivity: ");
		ConnectivityType connectivity = ConnectivityType.valueOf(new Scanner(System.in).next().toUpperCase());

		System.out.print("Please enter the quantity in stock: ");
		int quantity = new Scanner(System.in).nextInt();

		System.out.print("Please enter the original price: ");
		double originalPrice = new Scanner(System.in).nextDouble();

		System.out.print("Please enter the retail price: ");
		double retailPrice = new Scanner(System.in).nextDouble();

		if (productType.equals(ProductType.KEYBOARD)) {
			System.out.print("Please enter the keyboard layout: ");
			KeyboardLayout keyboardLayout = KeyboardLayout.valueOf(new Scanner(System.in).next().toUpperCase());

			System.out.print("Please enter the keyboard type: ");
			KeyboardType keyboardType = KeyboardType.valueOf(new Scanner(System.in).next().toUpperCase());

			Keyboard keyboardToAdd = new Keyboard(barcode, keyboardType, brand, colour, connectivity, quantity, originalPrice, retailPrice, keyboardLayout);
			return keyboardToAdd;
		} else {
			System.out.print("Please enter the number of buttons: ");
			int numberOfButtons = new Scanner(System.in).nextInt();

			System.out.print("Please enter the mouse type: ");
			MouseType mouseType = MouseType.valueOf(new Scanner(System.in).next().toUpperCase());

			Mouse mouseToAdd = new Mouse(barcode, mouseType, brand, colour, connectivity, quantity, originalPrice, retailPrice, numberOfButtons);
			return mouseToAdd;
		}
	}

}
