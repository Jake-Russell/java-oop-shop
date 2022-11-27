package uk.ac.lboro.jakerussell.cas.shop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import uk.ac.lboro.jakerussell.cas.data.Product;
import uk.ac.lboro.jakerussell.cas.data.Keyboard;
import uk.ac.lboro.jakerussell.cas.data.Mouse;
import uk.ac.lboro.jakerussell.cas.enums.Colour;
import uk.ac.lboro.jakerussell.cas.enums.ConnectivityType;
import uk.ac.lboro.jakerussell.cas.enums.KeyboardLayout;
import uk.ac.lboro.jakerussell.cas.enums.KeyboardType;
import uk.ac.lboro.jakerussell.cas.enums.MouseType;
import uk.ac.lboro.jakerussell.cas.enums.ProductType;

/**
 * ShopManagement is responsible for reading in Stock.txt, and creating an
 * instance of either a Keyboard or a Mouse for each product type in the file
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class ShopManagement {
	private Set<Product> products = new HashSet<>();


	/**
	 * Constructor reads in Stock.txt, and converts the text into objects
	 * 
	 * @throws FileNotFoundException if Stock.txt cannot be found
	 */
	public ShopManagement() throws FileNotFoundException {
		File inputFile = new File("Stock.txt");
		Scanner fileScanner = new Scanner(inputFile);

		int lineNumber = 0;

		while (fileScanner.hasNextLine()) {
			lineNumber++;
			try {
				String line = fileScanner.nextLine();
				String[] tempArray = line.split(", ");

				if (tempArray[1].toUpperCase().equals(ProductType.KEYBOARD.toString())) {
					Keyboard keyboard = new Keyboard(Integer.parseInt(tempArray[0]), KeyboardType.valueOf(tempArray[2].toUpperCase()), tempArray[3], Colour.valueOf(tempArray[4].toUpperCase()),
							ConnectivityType.valueOf(tempArray[5].toUpperCase()), Integer.parseInt(tempArray[6]), Double.parseDouble(tempArray[7]), Double.parseDouble(tempArray[8]),
							KeyboardLayout.valueOf(tempArray[9].toUpperCase()));

					products.add(keyboard);
				}

				else if (tempArray[1].toUpperCase().equals(ProductType.MOUSE.name())) {
					Mouse mouse = new Mouse(Integer.parseInt(tempArray[0]), MouseType.valueOf(tempArray[2].toUpperCase()), tempArray[3], Colour.valueOf(tempArray[4].toUpperCase()),
							ConnectivityType.valueOf(tempArray[5].toUpperCase()), Integer.parseInt(tempArray[6]), Double.parseDouble(tempArray[7]), Double.parseDouble(tempArray[8]),
							Integer.parseInt(tempArray[9]));

					products.add(mouse);
				}

				else {
					throw new Exception("ProductType." + line.substring(line.indexOf(' ') + 1, (line.indexOf(',', line.indexOf(',') + 1))));
				}
			}

			catch (Exception e) {
				String exceptionString = e.toString();
				if (exceptionString.contains("KeyboardType")) {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". There is no such Keyboard Type: " + exceptionString.substring(exceptionString.lastIndexOf('.') + 1));
				} else if (exceptionString.contains("KeyboardLayout")) {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". There is no such Keyboard Layout: " + exceptionString.substring(exceptionString.lastIndexOf('.') + 1));
				} else if (exceptionString.contains("MouseType")) {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". There is no such Mouse Type: " + exceptionString.substring(exceptionString.lastIndexOf('.') + 1));
				} else if (exceptionString.contains("Colour")) {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". There is no such Colour: " + exceptionString.substring(exceptionString.lastIndexOf('.') + 1));
				} else if (exceptionString.contains("ConnectivityType")) {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". There is no such Connectivity Type: " + exceptionString.substring(exceptionString.lastIndexOf('.') + 1));
				} else {
					System.out.println("Error in " + inputFile + ", line " + lineNumber + ". There is no such Product Type: " + exceptionString.substring(exceptionString.lastIndexOf('.') + 1));
				}
			}
		}
	}


	/**
	 * Returns the set of all products in Stock.txt
	 * 
	 * @return the set of all products in Stock.txt
	 */
	public Set<Product> getProducts() {
		return products;
	}


	/**
	 * Filters the products by a given criteria
	 * 
	 * @param filterByUKKeyboards if the set of products is to be filtered by UK
	 *                            keyboards only
	 * @param filterByBrand       if the set of products is to be filtered by a
	 *                            brand
	 * @param brand               the brand to filter the products by
	 * @return a set of Product, containing only the filtered products
	 */
	public Set<Product> filterProducts(boolean filterByUKKeyboards, boolean filterByBrand, String brand) {
		Set<Product> allProducts = this.getProducts();
		Set<Product> filteredProducts = new HashSet<>();

		if (!filterByUKKeyboards && !filterByBrand) {
			return allProducts;
		}

		for (Product product : allProducts) {
			if (filterByUKKeyboards && filterByBrand) {
				if (product.getProductType() == ProductType.KEYBOARD) {
					Keyboard keyboard = (Keyboard) product;
					if (keyboard.getLayout() == KeyboardLayout.UK && product.getBrand().toUpperCase().equals(brand.toUpperCase())) {
						filteredProducts.add(product);
					}
				}
			} else if (filterByUKKeyboards) {
				if (product.getProductType() == ProductType.KEYBOARD) {
					Keyboard keyboard = (Keyboard) product;
					if (keyboard.getLayout() == KeyboardLayout.UK) {
						filteredProducts.add(product);
					}
				}
			} else {
				if (product.getBrand().toUpperCase().equals(brand.toUpperCase())) {
					filteredProducts.add(product);
				}
			}
		}
		return filteredProducts;
	}


	/**
	 * Gets a product by its barcode
	 * 
	 * @param barcode the product's barcode
	 * @return the matching product or null if not found
	 */
	public Product getProductByBarcode(int barcode) {
		for (Product product : this.getProducts()) {
			if (product.getBarcode() == barcode) {
				return product;
			}
		}
		return null;
	}


	/**
	 * Writes the current set of products to the stock file
	 */
	public void saveProductsToStockFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Stock.txt"));
			for (Product product : products) {
				writer.write(product.toFileString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occured!");
			e.printStackTrace();
		}
	}


	/**
	 * Adds the specified product to the shop's product set
	 * 
	 * @param product the product to add
	 */
	public void addProduct(Product product) {
		products.add(product);
	}


	/**
	 * Removes the specified product from the shop's product set
	 * 
	 * @param product the product to remove
	 */
	public void removeProduct(Product product) {
		products.remove(product);
	}
}
