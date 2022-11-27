package uk.ac.lboro.jakerussell.cas.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.ac.lboro.jakerussell.cas.common.LogFileUtils;
import uk.ac.lboro.jakerussell.cas.data.Customer;
import uk.ac.lboro.jakerussell.cas.shop.ShopManagement;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 * LogOutConfirmationDialog is responsible for creating and displaying the
 * Logout Confirmation dialog to the user
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class LogOutConfirmationDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();


	/**
	 * Constructor creates the different Swing Components required for the dialog
	 * 
	 * @param customerMenuFrame an instance of the frame which has initiated this
	 *                          dialog
	 * @param label             the label to display text to when logging out
	 * @param customer          an instance of Customer, used to write to
	 *                          ActivityLog.txt
	 * @param shopManagement    an instance of ShopManagement, used to write to the
	 *                          stock file
	 */
	public LogOutConfirmationDialog(CustomerMenuFrame customerMenuFrame, JLabel label, Customer customer, ShopManagement shopManagement) {
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblLogOutWarning = SwingComponentsFactory.createLabel("<html><p style=\\\"text-align:center\\\">You have " + customer.getShoppingBasket().getNumberOfItems()
				+ " items in your shopping basket.<br/><br/>Logging out will cancel your basket. Do you wish to continue?</p></html>", 6, 6, 438, 55);
		lblLogOutWarning.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblLogOutWarning);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnYes = new JButton("Yes");
				btnYes.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
						customerMenuFrame.logOut(label);
						ArrayList<String> cancelledProductLines = customer.getCancelledProductLines();
						LogFileUtils.writeToLogFile(cancelledProductLines);
						shopManagement.saveProductsToStockFile();

					}
				});
				buttonPane.add(btnYes);
				getRootPane().setDefaultButton(btnYes);
			}
			{
				JButton btnNo = new JButton("No");
				btnNo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				buttonPane.add(btnNo);
			}
		}
	}
}
