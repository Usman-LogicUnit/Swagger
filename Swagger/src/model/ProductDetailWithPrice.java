package model;

import java.util.List;

public class ProductDetailWithPrice {
	String productId, name, description, productNumber;
	List<UnitsOfMeasure> unitsOfmeasure;
	float price;

	public ProductDetailWithPrice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public List<UnitsOfMeasure> getUnitsOfmeasure() {
		return unitsOfmeasure;
	}

	public void setUnitsOfmeasure(List<UnitsOfMeasure> unitsOfmeasure) {
		this.unitsOfmeasure = unitsOfmeasure;
	}

}
