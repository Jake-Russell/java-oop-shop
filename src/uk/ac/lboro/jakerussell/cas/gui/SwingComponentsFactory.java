package uk.ac.lboro.jakerussell.cas.gui;

import java.awt.Component;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * SwingComponentsFactory is responsible for creating different Swing
 * Components, given a range of different properties
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class SwingComponentsFactory {

	/**
	 * Creates a JLabel
	 * 
	 * @param value  the text value which the label has
	 * @param x      the x coordinate of the label
	 * @param y      the y coordinate of the label
	 * @param width  the width of the label
	 * @param height the height of the label
	 * @return the created label
	 */
	public static JLabel createLabel(String value, int x, int y, int width, int height) {
		JLabel label = new JLabel(value);
		label.setBounds(x, y, width, height);

		return label;
	}


	/**
	 * Creates a JButton
	 * 
	 * @param value  the text value which the button has
	 * @param x      the x coordinate of the button
	 * @param y      the y coordinate of the button
	 * @param width  the width of the button
	 * @param height the height of the button
	 * @return the created button
	 */
	public static JButton createButton(String value, int x, int y, int width, int height) {
		JButton button = new JButton(value);
		button.setBounds(x, y, width, height);

		return button;
	}


	/**
	 * Creates a JRadioButton
	 * 
	 * @param value    the text value which the radio button has
	 * @param selected if the radio button is selected or not
	 * @param x        the x coordinate of the radio button
	 * @param y        the y coordinate of the radio button
	 * @param width    the width of the radio button
	 * @param height   the height of the radio button
	 * @return the created radio button
	 */
	public static JRadioButton createRadioButton(String value, boolean selected, int x, int y, int width, int height) {
		JRadioButton radioButton = new JRadioButton(value);
		radioButton.setSelected(selected);
		radioButton.setBounds(x, y, width, height);

		return radioButton;
	}


	/**
	 * Creates a ButtonGroup
	 * 
	 * @param buttons an array of JRadioButton, containing the radio buttons to be
	 *                added to the button group
	 */
	public static void createRadioButtonGroup(JRadioButton[] buttons) {
		ButtonGroup buttonGroup = new ButtonGroup();
		for (JRadioButton radioButton : buttons) {
			buttonGroup.add(radioButton);
		}
	}


	/**
	 * Creates a JTextField
	 * 
	 * @param x      the x coordinate of the text field
	 * @param y      the y coordinate of the text field
	 * @param width  the width of the text field
	 * @param height the height of the text field
	 * @return the created text field
	 */
	public static JTextField createTextField(int x, int y, int width, int height) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);

		return textField;
	}


	/**
	 * Creates a JTextArea
	 * 
	 * @param editable if the text area is editable
	 * @param visible  if the text area is visible
	 * @param x        the x coordinate of the text area
	 * @param y        the y coordinate of the text area
	 * @param width    the width of the text area
	 * @param height   the height of the text area
	 * @return the created text area
	 */
	public static JTextArea createTextArea(boolean editable, boolean visible, int x, int y, int width, int height) {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(editable);
		textArea.setVisible(visible);
		textArea.setBounds(x, y, width, height);

		return textArea;
	}


	/**
	 * Creates a JScrollPane
	 * 
	 * @param x         the x coordinate of the scroll pane
	 * @param y         the y coordinate of the scroll pane
	 * @param width     the width of the scroll pane
	 * @param height    the height of the scroll pane
	 * @param component the component which the scroll pane is added to
	 * @return the created scroll pane
	 */
	public static JScrollPane createScrollPane(int x, int y, int width, int height, Component component) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(x, y, width, height);
		scrollPane.setViewportView(component);

		return scrollPane;
	}


	/**
	 * Creates a JTable
	 * 
	 * @param dtm the DefaultTableModel for the table
	 * @return the created table
	 */
	public static JTable createTable(DefaultTableModel dtm) {
		JTable table = new JTable();
		table.setModel(dtm);
		table.getTableHeader().setReorderingAllowed(false);

		return table;
	}


	/**
	 * Creates a DefaultTableModel
	 * 
	 * @param object an array of Object, containing the table headers
	 * @return the created default table model
	 */
	public static DefaultTableModel createDefaultTableModel(Object[] object) {
		DefaultTableModel dtm = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		dtm.setColumnIdentifiers(object);
		dtm.setRowCount(0);

		return dtm;
	}
}
