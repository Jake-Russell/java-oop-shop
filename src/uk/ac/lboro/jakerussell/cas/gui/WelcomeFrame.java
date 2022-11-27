package uk.ac.lboro.jakerussell.cas.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.ac.lboro.jakerussell.cas.common.UserUtils;
import uk.ac.lboro.jakerussell.cas.data.Customer;
import uk.ac.lboro.jakerussell.cas.data.User;
import uk.ac.lboro.jakerussell.cas.enums.UserType;
import uk.ac.lboro.jakerussell.cas.shop.UserManagement;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * WelcomeFrame is the main entry point for the GUI application, and is
 * responsible for creating and displaying the main welcome frame to the user
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class WelcomeFrame extends JFrame {

	private JPanel contentPane;
	private UserManagement userManagement;


	/**
	 * The main method which invokes the creation of the WelcomeFrame window
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeFrame frame = new WelcomeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Constructor creates the different Swing Components required for the frame
	 * 
	 * @throws FileNotFoundException if the UserAccounts.txt file cannot be found
	 *                               when creating a UserManagement instance
	 */
	public WelcomeFrame() throws FileNotFoundException {
		userManagement = new UserManagement();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		contentPane.add(SwingComponentsFactory.createLabel(
				"<html><p style=\"text-align:center\">Welcome to the Computer Accessories Shop<br/><br/>Please choose which user you would like to login to the system as from the below list:</p></html>",
				6, 6, 438, 66));

		JComboBox comboBoxUserID = new JComboBox();
		comboBoxUserID.setBounds(144, 84, 150, 27);
		for (User user : UserUtils.sortUsersByUserID(userManagement.getUsers())) {
			comboBoxUserID.addItem(user.getUserID());
		}
		contentPane.add(comboBoxUserID);

		JButton btnSubmitUserID = SwingComponentsFactory.createButton("Login", 169, 123, 100, 29);
		btnSubmitUserID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userID = (int) comboBoxUserID.getSelectedItem();
				if (userManagement.getUserByUserID(userID).getUserType() == UserType.ADMIN) {
					AdminMenuFrame adminMenuFrame = new AdminMenuFrame();
					adminMenuFrame.setVisible(true);
				} else {
					CustomerMenuFrame customerMenuFrame = new CustomerMenuFrame(userID);
					customerMenuFrame.setVisible(true);
				}
			}
		});
		contentPane.add(btnSubmitUserID);
	}
}