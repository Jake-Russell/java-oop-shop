package uk.ac.lboro.jakerussell.cas.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import uk.ac.lboro.jakerussell.cas.data.Product;

/**
 * ProductUtils is responsible for the common product functionality between the
 * CLI and GUI applications
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class ProductUtils {

	/**
	 * Sorts products by their quantity in stock given a set of the products to be
	 * sorted
	 * 
	 * @param products the set of products to be sorted
	 * @return the list of products, sorted
	 */
	public static List<Product> sortProductsByQuantityInStock(Set<Product> products) {
		List<Product> sortedProducts = new ArrayList<>(products);
		sortedProducts.sort(new Comparator<Product>() {

			@Override
			public int compare(Product product1, Product product2) {
				if (product1.getQuantityInStock() == product2.getQuantityInStock()) {
					return 0;
				} else if (product1.getQuantityInStock() < product2.getQuantityInStock()) {
					return 1;
				}
				return -1;
			}

		});
		return sortedProducts;
	}
}
