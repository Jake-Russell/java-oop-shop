package uk.ac.lboro.jakerussell.cas.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.ac.lboro.jakerussell.cas.common.LogFileUtils;
import uk.ac.lboro.jakerussell.cas.data.Customer;
import uk.ac.lboro.jakerussell.cas.enums.PaymentMethod;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * PaymentFrame is responsible for creating and displaying the Payment frame to
 * the user
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class PaymentFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldPayPalEmailAddress;
	private JTextField textFieldCreditCardNumber;
	private JTextField textFieldSecurityNumber;
	private JLabel lblPayPalOutputMessage;
	private JLabel lblCreditCardOutputMessage;
	private Timer timer;


	/**
	 * Constructor creates the different Swing Components required for the frame
	 * 
	 * @param customerMenuFrame an instance of the frame which has initiated this
	 *                          frame
	 * @param customer          an instance of Customer, used to write to
	 *                          ActivityLog.txt
	 */
	public PaymentFrame(CustomerMenuFrame customerMenuFrame, Customer customer) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////// PAYPAL PAYMENT TAB /////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 563, 266);
		contentPane.add(tabbedPane);

		JPanel panelPayPalPayment = new JPanel();
		tabbedPane.addTab("PayPal Payment", null, panelPayPalPayment, null);
		panelPayPalPayment.setLayout(null);

		panelPayPalPayment.add(SwingComponentsFactory.createLabel("Please enter your PayPal email address:", 6, 6, 250, 16));

		textFieldPayPalEmailAddress = SwingComponentsFactory.createTextField(268, 1, 268, 26);
		panelPayPalPayment.add(textFieldPayPalEmailAddress);

		lblPayPalOutputMessage = SwingComponentsFactory.createLabel("", 6, 198, 530, 16);
		lblPayPalOutputMessage.setHorizontalAlignment(SwingConstants.CENTER);
		panelPayPalPayment.add(lblPayPalOutputMessage);

		JButton btnMakePayPalPayment = SwingComponentsFactory.createButton("Pay Now", 66, 135, 150, 29);
		btnMakePayPalPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (InputValidation.validatePayPalPayment(textFieldPayPalEmailAddress.getText())) {
					closeWindow(lblPayPalOutputMessage, true);
					ArrayList<String> purchasedProductLines = customer.getPurchasedProductLines(PaymentMethod.PAYPAL);
					LogFileUtils.writeToLogFile(purchasedProductLines);
					customerMenuFrame.updateShoppingBasketDisplay();
				} else {
					lblPayPalOutputMessage.setText("Invalid email entered.");
				}
			}
		});
		panelPayPalPayment.add(btnMakePayPalPayment);

		JButton btnCancelPayPalPayment = SwingComponentsFactory.createButton("Cancel Payment", 326, 135, 150, 29);
		btnCancelPayPalPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow(lblPayPalOutputMessage, false);
			}
		});
		panelPayPalPayment.add(btnCancelPayPalPayment);

		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////// CREDIT CARD PAYMENT TAB ///////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel panelCreditCardPayment = new JPanel();
		tabbedPane.addTab("Credit Card Payment", null, panelCreditCardPayment, null);
		panelCreditCardPayment.setLayout(null);

		panelCreditCardPayment.add(SwingComponentsFactory.createLabel("Please enter your 16 digit Credit Card number:", 6, 6, 300, 16));

		panelCreditCardPayment.add(SwingComponentsFactory.createLabel("Please enter your 3 digit security number:", 6, 34, 300, 16));

		lblCreditCardOutputMessage = SwingComponentsFactory.createLabel("", 6, 198, 530, 16);
		lblCreditCardOutputMessage.setHorizontalAlignment(SwingConstants.CENTER);
		panelCreditCardPayment.add(lblCreditCardOutputMessage);

		textFieldCreditCardNumber = SwingComponentsFactory.createTextField(318, 1, 218, 26);
		panelCreditCardPayment.add(textFieldCreditCardNumber);

		textFieldSecurityNumber = SwingComponentsFactory.createTextField(318, 29, 60, 26);
		panelCreditCardPayment.add(textFieldSecurityNumber);

		JButton btnMakeCreditCardPayment = SwingComponentsFactory.createButton("Pay Now", 66, 135, 150, 29);
		btnMakeCreditCardPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (InputValidation.validateCreditCardNumber(textFieldCreditCardNumber.getText())) {
					if (InputValidation.validateSecurityNumber(textFieldSecurityNumber.getText())) {
						closeWindow(lblCreditCardOutputMessage, true);
						ArrayList<String> purchasedProductLines = customer.getPurchasedProductLines(PaymentMethod.CREDITCARD);
						LogFileUtils.writeToLogFile(purchasedProductLines);
						customerMenuFrame.updateShoppingBasketDisplay();
					} else {
						lblCreditCardOutputMessage.setText("Invalid security number entered.");
					}
				} else {
					lblCreditCardOutputMessage.setText("Invalid Credit Card number entered.");
				}
			}
		});
		panelCreditCardPayment.add(btnMakeCreditCardPayment);

		JButton btnCancelPayment = SwingComponentsFactory.createButton("Cancel Payment", 326, 135, 150, 29);
		btnCancelPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow(lblCreditCardOutputMessage, false);
			}
		});
		panelCreditCardPayment.add(btnCancelPayment);
	}


	private void closeWindow(JLabel label, boolean makePayment) {
		int delay = 1000;

		ActionListener action = new ActionListener() {
			int counter = 5;


			@Override
			public void actionPerformed(ActionEvent event) {
				if (counter == 0) {
					timer.stop();
					setVisible(false);
					dispose();
				} else if (makePayment) {
					label.setText("Valid payment credentials entered. Window closing in " + counter + " seconds.");
					counter--;
				} else {
					label.setText("Cancelling payment. Window closing in " + counter + " seconds.");
					counter--;
				}
			}
		};

		timer = new Timer(delay, action);
		timer.setInitialDelay(0);
		timer.start();
	}
}
