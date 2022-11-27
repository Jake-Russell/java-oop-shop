package uk.ac.lboro.jakerussell.cas.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import uk.ac.lboro.jakerussell.cas.data.Keyboard;
import uk.ac.lboro.jakerussell.cas.data.Mouse;
import uk.ac.lboro.jakerussell.cas.data.Product;
import uk.ac.lboro.jakerussell.cas.enums.ProductType;

/**
 * CommonFunctionality is responsible for handling any common functionality
 * between the GUI windows Primarily, this is the action of populating the
 * tables, although could have further implementations in future versions
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class CommonFunctionality {

	/**
	 * Populates the table, given the list of products and the table model
	 * 
	 * @param products the list of products to be added to the table
	 * @param dtm      the default table model to use
	 * @param isAdmin  true if it is an admin table, false otherwise (so the correct
	 *                 information is displayed)
	 */
	public static void populateTable(List<Product> products, DefaultTableModel dtm, boolean isAdmin) {
		dtm.setRowCount(0);
		for (Product product : products) {
			Object[] rowData;
			if (isAdmin) {
				if (product.getProductType().equals(ProductType.KEYBOARD)) {
					Keyboard keyboard = (Keyboard) product;
					rowData = new Object[] { keyboard.getBarcode(), keyboard.getProductType(), keyboard.getType(), keyboard.getBrand(), keyboard.getColour(), keyboard.getConnectivity(),
							keyboard.getQuantityInStock(), keyboard.getOriginalCost(), keyboard.getRetailCost(), keyboard.getLayout() };
				} else {
					Mouse mouse = (Mouse) product;
					rowData = new Object[] { mouse.getBarcode(), mouse.getProductType(), mouse.getType(), mouse.getBrand(), mouse.getColour(), mouse.getConnectivity(), mouse.getQuantityInStock(),
							mouse.getOriginalCost(), mouse.getRetailCost(), mouse.getNumberOfButtons() };
				}
			} else {
				if (product.getProductType().equals(ProductType.KEYBOARD)) {
					Keyboard keyboard = (Keyboard) product;
					rowData = new Object[] { keyboard.getBarcode(), keyboard.getProductType(), keyboard.getType(), keyboard.getBrand(), keyboard.getColour(), keyboard.getConnectivity(),
							keyboard.getQuantityInStock(), keyboard.getRetailCost(), keyboard.getLayout() };
				} else {
					Mouse mouse = (Mouse) product;
					rowData = new Object[] { mouse.getBarcode(), mouse.getProductType(), mouse.getType(), mouse.getBrand(), mouse.getColour(), mouse.getConnectivity(), mouse.getQuantityInStock(),
							mouse.getRetailCost(), mouse.getNumberOfButtons() };
				}
			}
			dtm.addRow(rowData);
		}
	}


	/**
	 * Populates the table, given the set of products and the table model
	 * 
	 * @param products the set of products to be added to the table
	 * @param dtm      the default table model to use
	 * @param isAdmin  true if it is an admin table, false otherwise (so the correct
	 *                 information is displayed)
	 */
	public static void populateTable(Set<Product> products, DefaultTableModel dtm, boolean isAdmin) {
		dtm.setRowCount(0);
		for (Product product : products) {
			Object[] rowData;
			if (isAdmin) {
				if (product.getProductType().equals(ProductType.KEYBOARD)) {
					Keyboard keyboard = (Keyboard) product;
					rowData = new Object[] { keyboard.getBarcode(), keyboard.getProductType(), keyboard.getType(), keyboard.getBrand(), keyboard.getColour(), keyboard.getConnectivity(),
							keyboard.getQuantityInStock(), keyboard.getOriginalCost(), keyboard.getRetailCost(), keyboard.getLayout() };
				} else {
					Mouse mouse = (Mouse) product;
					rowData = new Object[] { mouse.getBarcode(), mouse.getProductType(), mouse.getType(), mouse.getBrand(), mouse.getColour(), mouse.getConnectivity(), mouse.getQuantityInStock(),
							mouse.getOriginalCost(), mouse.getRetailCost(), mouse.getNumberOfButtons() };
				}
			} else {
				if (product.getProductType().equals(ProductType.KEYBOARD)) {
					Keyboard keyboard = (Keyboard) product;
					rowData = new Object[] { keyboard.getBarcode(), keyboard.getProductType(), keyboard.getType(), keyboard.getBrand(), keyboard.getColour(), keyboard.getConnectivity(),
							keyboard.getQuantityInStock(), keyboard.getRetailCost(), keyboard.getLayout() };
				} else {
					Mouse mouse = (Mouse) product;
					rowData = new Object[] { mouse.getBarcode(), mouse.getProductType(), mouse.getType(), mouse.getBrand(), mouse.getColour(), mouse.getConnectivity(), mouse.getQuantityInStock(),
							mouse.getRetailCost(), mouse.getNumberOfButtons() };
				}
			}
			dtm.addRow(rowData);
		}
	}
}
