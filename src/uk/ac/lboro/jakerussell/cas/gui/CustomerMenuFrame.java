package uk.ac.lboro.jakerussell.cas.gui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.ac.lboro.jakerussell.cas.common.LogFileUtils;
import uk.ac.lboro.jakerussell.cas.data.Customer;
import uk.ac.lboro.jakerussell.cas.data.Keyboard;
import uk.ac.lboro.jakerussell.cas.data.Mouse;
import uk.ac.lboro.jakerussell.cas.data.Product;
import uk.ac.lboro.jakerussell.cas.enums.ProductType;
import uk.ac.lboro.jakerussell.cas.shop.ShopManagement;
import uk.ac.lboro.jakerussell.cas.shop.UserManagement;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * CustomerMenuFrame is responsible for creating and displaying the Customer
 * frame to the user
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class CustomerMenuFrame extends JFrame {

	private JPanel contentPane;
	private ShopManagement shopManagement;
	private UserManagement userManagement;
	private Customer customer;
	private JLabel lblShoppingBasketSummary;
	private DefaultTableModel dtmViewProducts;
	private DefaultTableModel dtmViewShoppingBasket;
	private CustomerMenuFrame self;
	private Timer timer;


	/**
	 * Constructor creates the different Swing Components required for the frame
	 * 
	 * @param userID The ID of the user who is logging into the system, used to
	 *               create an instance of Customer
	 */
	public CustomerMenuFrame(int userID) {
		try {
			shopManagement = new ShopManagement();
			userManagement = new UserManagement();
			customer = userManagement.createCustomerFromUserID(userID);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// The jTableViewProducts is created here so that when the tab is changed, and the tab index = 0, the table is auto refreshed
		dtmViewProducts = SwingComponentsFactory.createDefaultTableModel(
				new Object[] { "Barcode", "Product Type", "Specific Product Type", "Brand", "Colour", "Connectivity", "Quantity In Stock", "Retail Cost", "Keyboard Layout / Number of Buttons" });
		JTable jTableViewProducts = SwingComponentsFactory.createTable(dtmViewProducts);

		JScrollPane scrollPaneViewProducts = SwingComponentsFactory.createScrollPane(6, 6, 955, 260, jTableViewProducts);

		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////// VIEW PRODUCTS TAB //////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 988, 366);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0) {
					CommonFunctionality.populateTable(uk.ac.lboro.jakerussell.cas.common.ProductUtils.sortProductsByQuantityInStock(shopManagement.getProducts()), dtmViewProducts, false);
				}
			}
		});
		contentPane.add(tabbedPane);

		JPanel panelViewProducts = new JPanel();
		tabbedPane.addTab("View Products", null, panelViewProducts, null);
		panelViewProducts.setLayout(null);

		panelViewProducts.add(scrollPaneViewProducts);

		CommonFunctionality.populateTable(uk.ac.lboro.jakerussell.cas.common.ProductUtils.sortProductsByQuantityInStock(shopManagement.getProducts()), dtmViewProducts, false);

		JLabel lblViewProductsLogOut = SwingComponentsFactory.createLabel("", 6, 298, 955, 16);
		lblViewProductsLogOut.setHorizontalAlignment(SwingConstants.CENTER);
		panelViewProducts.add(lblViewProductsLogOut);

		self = this;

		JButton btnViewProductsLogOut = SwingComponentsFactory.createButton("Log Out", 403, 270, 150, 29);
		btnViewProductsLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customer.getShoppingBasket().getNumberOfItems() != 0) {
					LogOutConfirmationDialog logOutConfirmationDialog = new LogOutConfirmationDialog(self, lblViewProductsLogOut, customer, shopManagement);
					logOutConfirmationDialog.setVisible(true);
				} else {
					logOut(lblViewProductsLogOut);
				}
			}
		});
		panelViewProducts.add(btnViewProductsLogOut);

		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////// SEARCH FOR ITEM TAB /////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel panelSearchForItem = new JPanel();
		tabbedPane.addTab("Search For Item", null, panelSearchForItem, null);
		panelSearchForItem.setLayout(null);

		JLabel lblBrandSearch = SwingComponentsFactory.createLabel("Please enter the brand you would like to search for: ", 6, 34, 368, 16);
		panelSearchForItem.add(lblBrandSearch);

		JTextField textFieldBrandSearch = SwingComponentsFactory.createTextField(403, 29, 150, 26);
		panelSearchForItem.add(textFieldBrandSearch);

		panelSearchForItem.add(SwingComponentsFactory.createLabel("Would you like to filter the products by a specific brand?", 6, 6, 368, 16));

		JRadioButton rdbtnBrandYes = SwingComponentsFactory.createRadioButton("Yes", true, 403, 2, 60, 23);
		rdbtnBrandYes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				boolean filterByBrand;
				if (rdbtnBrandYes.isSelected()) {
					lblBrandSearch.setVisible(true);
					textFieldBrandSearch.setVisible(true);
					filterByBrand = true;
				} else {
					lblBrandSearch.setVisible(false);
					textFieldBrandSearch.setVisible(false);
					filterByBrand = false;
				}
			}
		});
		panelSearchForItem.add(rdbtnBrandYes);

		JRadioButton rdbtnBrandNo = SwingComponentsFactory.createRadioButton("No", false, 493, 2, 60, 23);
		panelSearchForItem.add(rdbtnBrandNo);

		SwingComponentsFactory.createRadioButtonGroup(new JRadioButton[] { rdbtnBrandYes, rdbtnBrandNo });

		panelSearchForItem.add(SwingComponentsFactory.createLabel("Would you like to filter the products by UK keyboards?", 6, 62, 368, 16));

		JRadioButton rdbtnKeyboardYes = SwingComponentsFactory.createRadioButton("Yes", true, 403, 58, 60, 23);
		panelSearchForItem.add(rdbtnKeyboardYes);

		JRadioButton rdbtnKeyboardNo = SwingComponentsFactory.createRadioButton("No", false, 493, 58, 60, 23);
		panelSearchForItem.add(rdbtnKeyboardNo);

		SwingComponentsFactory.createRadioButtonGroup(new JRadioButton[] { rdbtnKeyboardYes, rdbtnKeyboardNo });

		DefaultTableModel dtmSearchProducts = SwingComponentsFactory.createDefaultTableModel(
				new Object[] { "Barcode", "Product Type", "Specific Product Type", "Brand", "Colour", "Connectivity", "Quantity In Stock", "Retail Cost", "Keyboard Layout / Number of Buttons" });
		JTable jTableSearchProducts = SwingComponentsFactory.createTable(dtmSearchProducts);

		JScrollPane scrollPaneSearchProducts = SwingComponentsFactory.createScrollPane(6, 122, 955, 145, jTableSearchProducts);
		panelSearchForItem.add(scrollPaneSearchProducts);

		JButton btnSearch = SwingComponentsFactory.createButton("Search", 403, 90, 150, 29);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean filterByUKKeyboards = false;
				boolean filterByBrand = false;
				String brand = null;
				if (rdbtnBrandYes.isSelected()) {
					filterByBrand = true;
					brand = textFieldBrandSearch.getText();
				}
				if (rdbtnKeyboardYes.isSelected()) {
					filterByUKKeyboards = true;
				}

				CommonFunctionality.populateTable(shopManagement.filterProducts(filterByUKKeyboards, filterByBrand, brand), dtmSearchProducts, false);
			}
		});
		panelSearchForItem.add(btnSearch);

		JLabel lblSearchLogOut = SwingComponentsFactory.createLabel("", 6, 298, 955, 16);
		lblSearchLogOut.setHorizontalAlignment(SwingConstants.CENTER);
		panelSearchForItem.add(lblSearchLogOut);

		JButton btnSearchLogOut = SwingComponentsFactory.createButton("Log Out", 403, 270, 150, 29);
		btnSearchLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customer.getShoppingBasket().getNumberOfItems() != 0) {
					LogOutConfirmationDialog logOutConfirmationDialog = new LogOutConfirmationDialog(self, lblSearchLogOut, customer, shopManagement);
					logOutConfirmationDialog.setVisible(true);
				} else {
					logOut(lblSearchLogOut);
				}
			}
		});
		panelSearchForItem.add(btnSearchLogOut);

		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////// ADD TO SHOPPING BASKET TAB /////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel panelAddToShoppingBasket = new JPanel();
		tabbedPane.addTab("Add To Shopping Basket", null, panelAddToShoppingBasket, null);
		panelAddToShoppingBasket.setLayout(null);

		panelAddToShoppingBasket.add(SwingComponentsFactory.createLabel("Enter the barcode of the item to add to the basket:", 6, 6, 330, 16));

		JTextField textFieldItemToAdd = SwingComponentsFactory.createTextField(348, 1, 150, 26);
		panelAddToShoppingBasket.add(textFieldItemToAdd);

		JLabel lblOutputMessage = SwingComponentsFactory.createLabel("", 6, 98, 955, 16);
		lblOutputMessage.setHorizontalAlignment(SwingConstants.CENTER);
		panelAddToShoppingBasket.add(lblOutputMessage);

		JButton btnAddItem = SwingComponentsFactory.createButton("Add Item", 510, 1, 150, 29);
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product productToAdd = shopManagement.getProductByBarcode(Integer.parseInt(textFieldItemToAdd.getText()));

				if (!InputValidation.validateBarcodeInput(textFieldItemToAdd.getText())) {
					lblOutputMessage.setText("This is an invalid barcode. Please enter a 6 digit number.");
				} else if (productToAdd == null) {
					lblOutputMessage.setText("An item with this barcode is not stocked in this store. Please enter a different barcode.");
				} else if (customer.addToShoppingBasket(productToAdd)) {
					lblOutputMessage.setText(productToAdd.getBrand() + " " + productToAdd.getProductType().toString().toLowerCase() + " successfully added to basket.");
					updateShoppingBasketDisplay();
					shopManagement.saveProductsToStockFile();
				} else {
					lblOutputMessage.setText("This " + productToAdd.getBrand() + " " + productToAdd.getProductType().toString().toLowerCase() + " is currently out of stock.");
				}
			}
		});
		panelAddToShoppingBasket.add(btnAddItem);

		JLabel lblAddItemLogOut = SwingComponentsFactory.createLabel("", 6, 298, 955, 16);
		lblAddItemLogOut.setHorizontalAlignment(SwingConstants.CENTER);
		panelAddToShoppingBasket.add(lblAddItemLogOut);

		JButton btnAddItemLogOut = SwingComponentsFactory.createButton("Log Out", 403, 270, 150, 29);
		btnAddItemLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customer.getShoppingBasket().getNumberOfItems() != 0) {
					LogOutConfirmationDialog logOutConfirmationDialog = new LogOutConfirmationDialog(self, lblAddItemLogOut, customer, shopManagement);
					logOutConfirmationDialog.setVisible(true);
				} else {
					logOut(lblAddItemLogOut);
				}
			}
		});
		panelAddToShoppingBasket.add(btnAddItemLogOut);

		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////// VIEW SHOPPING BASKET TAB //////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel panelViewShoppingBasket = new JPanel();
		tabbedPane.addTab("View Shopping Basket", null, panelViewShoppingBasket, null);
		panelViewShoppingBasket.setLayout(null);

		dtmViewShoppingBasket = SwingComponentsFactory.createDefaultTableModel(
				new Object[] { "Barcode", "Product Type", "Specific Product Type", "Brand", "Colour", "Connectivity", "Retail Cost", "Keyboard Layout / Number of Buttons", "Quantity" });
		JTable jTableViewShoppingBasket = SwingComponentsFactory.createTable(dtmViewShoppingBasket);

		JScrollPane scrollPaneViewShoppingBasket = SwingComponentsFactory.createScrollPane(6, 62, 955, 161, jTableViewShoppingBasket);
		panelViewShoppingBasket.add(scrollPaneViewShoppingBasket);

		lblShoppingBasketSummary = SwingComponentsFactory.createLabel(
				"<html><p style=\"text-align:center\">" + customer.getUsername() + "'s Shopping Basket<br/><br/>" + customer.getShoppingBasket().toString() + "</p></html>", 6, 6, 955, 44);
		lblShoppingBasketSummary.setHorizontalAlignment(SwingConstants.CENTER);
		panelViewShoppingBasket.add(lblShoppingBasketSummary);

		JLabel lblShoppingBasketOutputMessage = SwingComponentsFactory.createLabel("", 6, 298, 955, 16);
		lblShoppingBasketOutputMessage.setHorizontalAlignment(SwingConstants.CENTER);
		panelViewShoppingBasket.add(lblShoppingBasketOutputMessage);

		JButton btnSaveBasket = SwingComponentsFactory.createButton("Save Basket", 403, 235, 150, 29);
		btnSaveBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customer.getShoppingBasket().getNumberOfItems() == 0) {
					lblShoppingBasketOutputMessage.setText("You are unable to save a shopping basket with 0 items in it.");
				} else {
					ArrayList<String> savedProductLines = customer.getSavedProductLines();
					LogFileUtils.writeToLogFile(savedProductLines);
					lblShoppingBasketOutputMessage.setText("Shopping Basket successfully saved.");
				}
				updateShoppingBasketDisplay();
				shopManagement.saveProductsToStockFile();
			}
		});
		panelViewShoppingBasket.add(btnSaveBasket);

		JButton btnCancelBasket = SwingComponentsFactory.createButton("Cancel Basket", 811, 235, 150, 29);
		btnCancelBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customer.getShoppingBasket().getNumberOfItems() == 0) {
					lblShoppingBasketOutputMessage.setText("You are unable to cancel a shopping basket with 0 items in it.");
				} else {
					ArrayList<String> cancelledProductLines = customer.getCancelledProductLines();
					LogFileUtils.writeToLogFile(cancelledProductLines);
					lblShoppingBasketOutputMessage.setText("Shopping Basket successfully cancelled.");
				}
				updateShoppingBasketDisplay();
				shopManagement.saveProductsToStockFile();
			}
		});
		panelViewShoppingBasket.add(btnCancelBasket);

		JButton btnPurchaseBasket = SwingComponentsFactory.createButton("Purchase Basket", 6, 235, 150, 29);
		btnPurchaseBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaymentFrame paymentFrame = new PaymentFrame(self, customer);
				if (customer.getShoppingBasket().getNumberOfItems() == 0) {
					lblShoppingBasketOutputMessage.setText("You are unable to purchase a shopping basket with 0 items in it.");
				} else {
					paymentFrame.setVisible(true);
				}
			}
		});
		panelViewShoppingBasket.add(btnPurchaseBasket);

		JButton btnShoppingBasketLogOut = SwingComponentsFactory.createButton("Log Out", 403, 270, 150, 29);
		btnShoppingBasketLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customer.getShoppingBasket().getNumberOfItems() != 0) {
					LogOutConfirmationDialog logOutConfirmationDialog = new LogOutConfirmationDialog(self, lblShoppingBasketOutputMessage, customer, shopManagement);
					logOutConfirmationDialog.setVisible(true);
				} else {
					logOut(lblShoppingBasketOutputMessage);
				}
			}
		});
		panelViewShoppingBasket.add(btnShoppingBasketLogOut);
	}


	/**
	 * Updates the shopping basket display whenever a change to the user's basket occurs
	 */
	public void updateShoppingBasketDisplay() {
		lblShoppingBasketSummary.setText("<html><p style=\"text-align:center\">" + customer.getUsername() + "'s Shopping Basket<br/><br/>" + customer.getShoppingBasket().toString() + "</p></html>");

		populateShoppingBasketTable(customer.getShoppingBasket().getItems(), dtmViewShoppingBasket);
	}


	/**
	 * Logs the user out of the system
	 * 
	 * @param label the label to display text to
	 */
	public void logOut(JLabel label) {
		int delay = 1000;
		ActionListener action = new ActionListener() {
			int counter = 3;

			@Override
			public void actionPerformed(ActionEvent event) {
				if (counter == 0) {
					timer.stop();
					setVisible(false);
					dispose();
				} else {
					label.setText("Logging out. Window closing in " + counter + " seconds.");
					counter--;
				}
			}
		};

		timer = new Timer(delay, action);
		timer.setInitialDelay(0);
		timer.start();
	}


	private void populateShoppingBasketTable(List<Product> shoppingBasket, DefaultTableModel dtm) {
		dtm.setRowCount(0);

		List<Product> products = new ArrayList<Product>();
		for (Product product : shoppingBasket) {
			if (!products.contains(product)) {
				products.add(product);
			}
		}

		for (Product product : products) {
			Object[] rowData;

			if (product.getProductType().equals(ProductType.KEYBOARD)) {
				Keyboard keyboard = (Keyboard) product;
				rowData = new Object[] { keyboard.getBarcode(), keyboard.getProductType(), keyboard.getType(), keyboard.getBrand(), keyboard.getColour(), keyboard.getConnectivity(),
						keyboard.getRetailCost(), keyboard.getLayout(), customer.getShoppingBasket().getQuantityOfItem(keyboard.getBarcode()) };
			} else {
				Mouse mouse = (Mouse) product;
				rowData = new Object[] { mouse.getBarcode(), mouse.getProductType(), mouse.getType(), mouse.getBrand(), mouse.getColour(), mouse.getConnectivity(), mouse.getRetailCost(),
						mouse.getNumberOfButtons(), customer.getShoppingBasket().getQuantityOfItem(mouse.getBarcode()) };
			}
			dtm.addRow(rowData);
		}
	}
}
