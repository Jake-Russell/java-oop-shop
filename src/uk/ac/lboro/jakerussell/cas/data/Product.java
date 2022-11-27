package uk.ac.lboro.jakerussell.cas.data;

import java.util.Objects;

import uk.ac.lboro.jakerussell.cas.enums.Colour;
import uk.ac.lboro.jakerussell.cas.enums.ConnectivityType;
import uk.ac.lboro.jakerussell.cas.enums.ProductType;

/**
 * Product is responsible for representing a product on the system
 * 
 * @author Jake Russell
 * @version 1.0
 * @since 01/03/2020
 */
public abstract class Product {
	private int barcode, quantityInStock;
	private ProductType productType;
	private String brand;
	private Colour colour;
	private ConnectivityType connectivity;
	private double retailCost, originalCost;


	/**
	 * Constructor is responsible for creating a product from given values. Since
	 * Product is an abstract class, this is only used by child classes of Product
	 * 
	 * @param barcode         the product's barcode
	 * @param productType     the product's product type
	 * @param brand           the product's brand
	 * @param colour          the product's colour
	 * @param connectivity    the product's connectivity
	 * @param quantityInStock the product's quantity in stock
	 * @param originalCost    the product's original cost
	 * @param retailCost      the product's retail cost
	 */
	public Product(int barcode, ProductType productType, String brand, Colour colour, ConnectivityType connectivity, int quantityInStock, double originalCost, double retailCost) {
		this.barcode = barcode;
		this.productType = productType;
		this.brand = brand;
		this.colour = colour;
		this.connectivity = connectivity;
		this.quantityInStock = quantityInStock;
		this.originalCost = originalCost;
		this.retailCost = retailCost;
	}


	/**
	 * Abstract method to return all of the product details, used in the CLI
	 * application
	 * 
	 * @return all of the product details
	 */
	public abstract String toFileString();


	/**
	 * Abstract method to return all of the product details that a customer can see,
	 * used in the CLI application
	 * 
	 * @return all of the product details that a customer can see
	 */
	public abstract String toUserString();


	/**
	 * Returns the product's barcode
	 * 
	 * @return the barcode of the product
	 */
	public int getBarcode() {
		return barcode;
	}


	/**
	 * Returns the product's product type
	 * 
	 * @return the product type of the product
	 */
	public ProductType getProductType() {
		return productType;
	}


	/**
	 * Returns the product's brand
	 * 
	 * @return the brand of the product
	 */
	public String getBrand() {
		return brand;
	}


	/**
	 * Returns the product's colour
	 * 
	 * @return the colour of the product
	 */
	public Colour getColour() {
		return colour;
	}


	/**
	 * Returns the product's connectivity type
	 * 
	 * @return the connectivity of the product
	 */
	public ConnectivityType getConnectivity() {
		return connectivity;
	}


	/**
	 * Returns the product's quantity in stock
	 * 
	 * @return the quantity in stock of the product
	 */
	public int getQuantityInStock() {
		return quantityInStock;
	}


	/**
	 * Sets the product's quantity in stock
	 * 
	 * @param quantityInStock the new quantity in stock of the product to be set
	 */
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}


	/**
	 * Returns the product's original cost
	 * 
	 * @return the original cost of the product
	 */
	public double getOriginalCost() {
		return originalCost;
	}


	/**
	 * Returns the product's retail cost
	 * 
	 * @return the retail cost of the product
	 */
	public double getRetailCost() {
		return retailCost;
	}


	@Override
	public int hashCode() {
		return Objects.hash(barcode);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		return barcode == other.barcode;
	}
}
