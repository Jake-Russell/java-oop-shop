package uk.ac.lboro.jakerussell.cas.gui;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import uk.ac.lboro.jakerussell.cas.data.Keyboard;
import uk.ac.lboro.jakerussell.cas.data.Mouse;
import uk.ac.lboro.jakerussell.cas.data.Product;
import uk.ac.lboro.jakerussell.cas.enums.Colour;
import uk.ac.lboro.jakerussell.cas.enums.ConnectivityType;
import uk.ac.lboro.jakerussell.cas.enums.KeyboardLayout;
import uk.ac.lboro.jakerussell.cas.enums.KeyboardType;
import uk.ac.lboro.jakerussell.cas.enums.MouseType;
import uk.ac.lboro.jakerussell.cas.enums.ProductType;
import uk.ac.lboro.jakerussell.cas.shop.ShopManagement;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;

/**
 * AdminMenuFrame is responsible for creating and displaying the Admin frame to
 * the user
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class AdminMenuFrame extends JFrame {

	private JTextField textFieldBarcode;
	private JTextField textFieldBrand;
	private JTextField textFieldQuantityInStock;
	private JTextField textFieldOriginalPrice;
	private JTextField textFieldRetailPrice;
	private JTextField textFieldNumberOfButtons;
	private JPanel contentPane;
	private ShopManagement shopManagement;
	private Timer timer;


	/**
	 * Constructor creates the different Swing Components required for the frame
	 */
	public AdminMenuFrame() {
		try {
			shopManagement = new ShopManagement();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableModel dtmViewProducts = SwingComponentsFactory.createDefaultTableModel(new Object[] { "Barcode", "Product Type", "Specific Product Type", "Brand", "Colour", "Connectivity",
				"Quantity In Stock", "Original Cost", "Retail Cost", "Keyboard Layout / Number of Buttons" });
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
					CommonFunctionality.populateTable(uk.ac.lboro.jakerussell.cas.common.ProductUtils.sortProductsByQuantityInStock(shopManagement.getProducts()), dtmViewProducts, true);
				}
			}
		});
		contentPane.add(tabbedPane);

		JPanel panelViewProducts = new JPanel();
		tabbedPane.addTab("View Products", null, panelViewProducts, null);
		panelViewProducts.setLayout(null);

		panelViewProducts.add(scrollPaneViewProducts);
		CommonFunctionality.populateTable(uk.ac.lboro.jakerussell.cas.common.ProductUtils.sortProductsByQuantityInStock(shopManagement.getProducts()), dtmViewProducts, true);

		JLabel lblViewProductsLogOut = SwingComponentsFactory.createLabel("", 6, 298, 955, 16);
		lblViewProductsLogOut.setHorizontalAlignment(SwingConstants.CENTER);
		panelViewProducts.add(lblViewProductsLogOut);

		JButton btnViewProductsLogOut = SwingComponentsFactory.createButton("Log Out", 403, 270, 150, 29);
		btnViewProductsLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logOut(lblViewProductsLogOut);
			}
		});
		panelViewProducts.add(btnViewProductsLogOut);

		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////// ADD STOCK TAB ////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel panelAddStock = new JPanel();
		tabbedPane.addTab("Add Stock", null, panelAddStock, null);
		panelAddStock.setLayout(null);

		panelAddStock.add(SwingComponentsFactory.createLabel("Barcode", 140, 10, 120, 16));
		panelAddStock.add(SwingComponentsFactory.createLabel("Brand", 140, 50, 120, 16));
		panelAddStock.add(SwingComponentsFactory.createLabel("Quantity In Stock", 140, 90, 120, 16));
		panelAddStock.add(SwingComponentsFactory.createLabel("Original Price", 140, 130, 120, 16));
		panelAddStock.add(SwingComponentsFactory.createLabel("Retail Price", 140, 170, 120, 16));

		panelAddStock.add(SwingComponentsFactory.createLabel("Product Type", 567, 10, 120, 16));
		panelAddStock.add(SwingComponentsFactory.createLabel("Colour", 567, 50, 120, 16));
		panelAddStock.add(SwingComponentsFactory.createLabel("Connectivity", 567, 90, 120, 16));

		JLabel lblKeyboardType = SwingComponentsFactory.createLabel("Keyboard Type", 567, 130, 120, 16);
		panelAddStock.add(lblKeyboardType);

		JLabel lblKeyboardLayout = SwingComponentsFactory.createLabel("Keyboard Layout", 567, 170, 120, 16);
		panelAddStock.add(lblKeyboardLayout);

		JLabel lblMouseType = SwingComponentsFactory.createLabel("Mouse Type", 567, 130, 120, 16);
		lblMouseType.setVisible(false);
		panelAddStock.add(lblMouseType);

		JLabel lblNumberOfButtons = SwingComponentsFactory.createLabel("Number Of Buttons", 567, 170, 125, 16);
		lblNumberOfButtons.setVisible(false);
		panelAddStock.add(lblNumberOfButtons);

		JLabel lblOutputMessage = SwingComponentsFactory.createLabel("", 6, 298, 955, 16);
		lblOutputMessage.setForeground(Color.RED);
		lblOutputMessage.setHorizontalAlignment(SwingConstants.CENTER);
		panelAddStock.add(lblOutputMessage);

		textFieldBarcode = SwingComponentsFactory.createTextField(260, 5, 130, 26);
		panelAddStock.add(textFieldBarcode);

		textFieldBrand = SwingComponentsFactory.createTextField(260, 45, 130, 26);
		panelAddStock.add(textFieldBrand);

		textFieldQuantityInStock = SwingComponentsFactory.createTextField(260, 85, 130, 26);
		panelAddStock.add(textFieldQuantityInStock);

		textFieldOriginalPrice = SwingComponentsFactory.createTextField(260, 125, 130, 26);
		panelAddStock.add(textFieldOriginalPrice);

		textFieldRetailPrice = SwingComponentsFactory.createTextField(260, 165, 130, 26);
		panelAddStock.add(textFieldRetailPrice);

		textFieldNumberOfButtons = SwingComponentsFactory.createTextField(687, 165, 130, 26);
		textFieldNumberOfButtons.setVisible(false);
		panelAddStock.add(textFieldNumberOfButtons);

		JComboBox comboBoxKeyboardType = new JComboBox(KeyboardType.values());
		comboBoxKeyboardType.setBounds(687, 125, 130, 26);
		panelAddStock.add(comboBoxKeyboardType);

		JComboBox comboBoxKeyboardLayout = new JComboBox(KeyboardLayout.values());
		comboBoxKeyboardLayout.setBounds(687, 165, 130, 26);
		panelAddStock.add(comboBoxKeyboardLayout);

		JComboBox comboBoxMouseType = new JComboBox(MouseType.values());
		comboBoxMouseType.setBounds(687, 125, 130, 26);
		comboBoxMouseType.setVisible(false);
		panelAddStock.add(comboBoxMouseType);

		JComboBox comboBoxProductType = new JComboBox(ProductType.values());
		comboBoxProductType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxProductType.getSelectedItem().toString().equals(ProductType.KEYBOARD.toString())) {
					lblKeyboardLayout.setVisible(true);
					comboBoxKeyboardLayout.setVisible(true);
					lblKeyboardType.setVisible(true);
					comboBoxKeyboardType.setVisible(true);

					lblMouseType.setVisible(false);
					comboBoxMouseType.setVisible(false);
					lblNumberOfButtons.setVisible(false);
					textFieldNumberOfButtons.setVisible(false);
				} else if (comboBoxProductType.getSelectedItem().toString().equals(ProductType.MOUSE.toString())) {
					lblKeyboardLayout.setVisible(false);
					comboBoxKeyboardLayout.setVisible(false);
					lblKeyboardType.setVisible(false);
					comboBoxKeyboardType.setVisible(false);

					lblMouseType.setVisible(true);
					comboBoxMouseType.setVisible(true);
					lblNumberOfButtons.setVisible(true);
					textFieldNumberOfButtons.setVisible(true);
				}
			}
		});
		comboBoxProductType.setBounds(687, 5, 130, 26);
		panelAddStock.add(comboBoxProductType);

		JComboBox comboBoxColour = new JComboBox(Colour.values());
		comboBoxColour.setBounds(687, 45, 130, 26);
		panelAddStock.add(comboBoxColour);

		JComboBox comboBoxConnectivity = new JComboBox(ConnectivityType.values());
		comboBoxConnectivity.setBounds(687, 85, 130, 26);
		panelAddStock.add(comboBoxConnectivity);

		JButton btnAddStock = SwingComponentsFactory.createButton("Add Stock", 403, 220, 150, 29);
		btnAddStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblOutputMessage.setVisible(true);

				if (textFieldBarcode.getText().equals("")) {
					lblOutputMessage.setText("Please enter the Barcode.");
				} else if (!InputValidation.validateUniqueBarcodeInput(textFieldBarcode.getText(), shopManagement)) {
					lblOutputMessage.setText("The Barcode should be a unique 6 digit number.");
				}

				else if (textFieldBrand.getText().equals("")) {
					lblOutputMessage.setText("Please enter the Brand.");
				}

				else if (textFieldQuantityInStock.getText().equals("")) {
					lblOutputMessage.setText("Please enter the Quantity In Stock.");
				} else if (!InputValidation.validateIntegerInput(textFieldQuantityInStock.getText())) {
					lblOutputMessage.setText("The Quantity In Stock should be a number.");
				}

				else if (textFieldOriginalPrice.getText().equals("")) {
					lblOutputMessage.setText("Please enter the Original Price.");
				} else if (!InputValidation.validateDoubleInput(textFieldOriginalPrice.getText())) {
					lblOutputMessage.setText("The Origainal Price should be a number.");
				}

				else if (textFieldRetailPrice.getText().equals("")) {
					lblOutputMessage.setText("Please enter the Retail Price.");
				} else if (!InputValidation.validateDoubleInput(textFieldRetailPrice.getText())) {
					lblOutputMessage.setText("The Retail Price should be a number.");
				}

				else if (comboBoxProductType.getSelectedItem().toString().equals(ProductType.MOUSE.toString()) && textFieldNumberOfButtons.getText().equals("")) {
					lblOutputMessage.setText("Please enter the Number of Buttons.");
				} else if (comboBoxProductType.getSelectedItem().toString().equals(ProductType.MOUSE.toString()) && !InputValidation.validateIntegerInput(textFieldNumberOfButtons.getText())) {
					lblOutputMessage.setText("Please enter the Number of Buttons.");
				}

				else {
					if (comboBoxProductType.getSelectedItem().toString().equals(ProductType.MOUSE.toString())) {
						Mouse mouseToAdd = new Mouse(Integer.parseInt(textFieldBarcode.getText()), MouseType.valueOf(comboBoxMouseType.getSelectedItem().toString()), textFieldBrand.getText(),
								Colour.valueOf(comboBoxColour.getSelectedItem().toString()), ConnectivityType.valueOf(comboBoxConnectivity.getSelectedItem().toString()),
								Integer.parseInt(textFieldQuantityInStock.getText()), Double.parseDouble(textFieldOriginalPrice.getText()), Double.parseDouble(textFieldRetailPrice.getText()),
								Integer.parseInt(textFieldNumberOfButtons.getText()));
						shopManagement.addProduct(mouseToAdd);
					} else {
						Keyboard keyboardToAdd = new Keyboard(Integer.parseInt(textFieldBarcode.getText()), KeyboardType.valueOf(comboBoxKeyboardType.getSelectedItem().toString()),
								textFieldBrand.getText(), Colour.valueOf(comboBoxColour.getSelectedItem().toString()), ConnectivityType.valueOf(comboBoxConnectivity.getSelectedItem().toString()),
								Integer.parseInt(textFieldQuantityInStock.getText()), Double.parseDouble(textFieldOriginalPrice.getText()), Double.parseDouble(textFieldRetailPrice.getText()),
								KeyboardLayout.valueOf(comboBoxKeyboardLayout.getSelectedItem().toString()));
						shopManagement.addProduct(keyboardToAdd);
					}
					shopManagement.saveProductsToStockFile();
					resetFields();
					lblOutputMessage.setText("Product successfully added");
				}
			}
		});
		panelAddStock.add(btnAddStock);

		JButton btnAddStockLogOut = SwingComponentsFactory.createButton("Log Out", 403, 270, 150, 29);
		btnAddStockLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logOut(lblOutputMessage);
			}
		});
		panelAddStock.add(btnAddStockLogOut);
	}


	private void resetFields() {
		textFieldBarcode.setText("");
		textFieldBrand.setText("");
		textFieldQuantityInStock.setText("");
		textFieldOriginalPrice.setText("");
		textFieldRetailPrice.setText("");
		textFieldNumberOfButtons.setText("");
	}


	private void logOut(JLabel label) {
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
}
