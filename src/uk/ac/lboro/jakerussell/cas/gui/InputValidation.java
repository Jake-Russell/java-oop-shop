package uk.ac.lboro.jakerussell.cas.gui;

import uk.ac.lboro.jakerussell.cas.shop.ShopManagement;

/**
 * InputValidation is responsible for validating any required inputs given by
 * the user
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class InputValidation {
	/**
	 * Validates if an input is a valid Integer
	 * 
	 * @param isInteger the String input to validate
	 * @return true if isInteger is a valid Integer, false otherwise
	 */
	public static boolean validateIntegerInput(String isInteger) {
		try {
			int number = Integer.valueOf(isInteger);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * Validates if an input is a valid Double
	 * 
	 * @param isDouble the String input to validate
	 * @return true if isDouble is a valid Double, false otherwise
	 */
	public static boolean validateDoubleInput(String isDouble) {
		try {
			double number = Double.valueOf(isDouble);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * Validates if an input is a valid barcode
	 * 
	 * @param isBarcode the String input to validate
	 * @return true if isBarcode is a valid barcode, false otherwise
	 */
	public static boolean validateBarcodeInput(String isBarcode) {
		String barcodeRegex = "^[0-9]{6}$";
		if (isBarcode.matches(barcodeRegex)) {
			return true;
		}
		return false;
	}


	/**
	 * Validates if an input is a valid unique barcode
	 * 
	 * @param isUniqueBarcode the String input to validate
	 * @param shopManagement  an instance of ShopManagement
	 * @return true if isUniqueBarcode is a valid unique barcode, false otherwise
	 */
	public static boolean validateUniqueBarcodeInput(String isUniqueBarcode, ShopManagement shopManagement) {
		if (validateIntegerInput(isUniqueBarcode) && validateBarcodeInput(isUniqueBarcode)) {
			if (shopManagement.getProductByBarcode(Integer.valueOf(isUniqueBarcode)) == null) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Validates if an input is a valid email address
	 * 
	 * @param isEmail the String input to validate
	 * @return true if isEmail is a valid email address, false otherwise
	 */
	public static boolean validatePayPalPayment(String isEmail) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		// REGEX from OWASP Validation Regex Repository
		// https://owasp.org/www-community/OWASP_Validation_Regex_Repository
		if (isEmail.matches(emailRegex)) {
			return true;
		}
		return false;
	}


	/**
	 * Validates if an input is a valid 16 digit number
	 * 
	 * @param isCreditCardNumber the String input to validate
	 * @return true if isCreditCardNumber is a valid 16 digit number, false
	 *         otherwise
	 */
	public static boolean validateCreditCardNumber(String isCreditCardNumber) {
		String cardNumberRegex = "^[0-9]{16}$";
		if (isCreditCardNumber.matches(cardNumberRegex)) {
			return true;
		}
		return false;
	}


	/**
	 * Validates if an input is a valid 3 digit number
	 * 
	 * @param isSecurityNumber the String input to validate
	 * @return true if isSecurityNumber is a valid 3 digit number, false otherwise
	 */
	public static boolean validateSecurityNumber(String isSecurityNumber) {
		String securityCodeRegex = "^[0-9]{3}$";
		if (isSecurityNumber.matches(securityCodeRegex)) {
			return true;
		}
		return false;
	}
}
