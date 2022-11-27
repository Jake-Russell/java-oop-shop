package uk.ac.lboro.jakerussell.cas.cli;

import java.util.Scanner;

/**
 * PaymentUtils is responsible for dealing with any payment related utilities
 * that the CLI application requires
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public class PaymentUtils {

	/**
	 * Responsible for gathering user input and validating PayPal payment, used in
	 * the CLI application
	 */
	public static void doPayPalPayment() {
		boolean validEmail = false;
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		// REGEX from OWASP Validation Regex Repository
		// https://owasp.org/www-community/OWASP_Validation_Regex_Repository

		System.out.println("\nPayment via PayPal initiated.");

		while (!validEmail) {
			System.out.print("Please enter your PayPal email address: ");
			String inputEmail = new Scanner(System.in).next();

			if (inputEmail.matches(emailRegex)) {
				System.out.println("Valid email");
				validEmail = true;
			} else {
				System.out.println("Invalid email.");
			}
		}
	}


	/**
	 * Responsible for gathering user input and validating Credit Card payment, used
	 * in the CLI application
	 */
	public static void doCreditCardPayment() {
		boolean validCreditCardNumber = false;
		boolean validSecurityCode = false;
		String cardNumberRegex = "^[0-9]{16}$";
		String securityCodeRegex = "^[0-9]{3}$";

		System.out.println("\nPayment via Credit Card initiated.");

		while (!validCreditCardNumber) {
			System.out.print("\nPlease enter your 16 digit credit card number: ");
			String creditCardNumber = new Scanner(System.in).next();

			if (creditCardNumber.matches(cardNumberRegex)) {
				validCreditCardNumber = true;
				while (!validSecurityCode) {
					System.out.print("\nPlease enter your 3 digit security code: ");
					String securityCode = new Scanner(System.in).next();
					if (securityCode.matches(securityCodeRegex)) {
						System.out.println("Valid Credit Card Credentials.");
						validSecurityCode = true;
					} else {
						System.out.println("Invalid security code. Please enter a 3 digit number.");
					}
				}
			} else {
				System.out.println("Invalid credit card number. Please enter a 16 digit number.");
			}
		}
	}
}
