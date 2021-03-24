package model;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
	String name, description, brand, isBundle, POID, productNumber, href, id;
	List<UnitsOfMeasure> unitsOfMeasure = new ArrayList<>();
	List<ProductOfferingRef> productOfferings = new ArrayList<>();
	List<ProductSpecCharacteristic> productSpecCharacteristics = new ArrayList<>();
	List<BarCode> availableBarcodes = new ArrayList<>();

	public ProductSpecification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPOID() {
		return POID;
	}

	public void setPOID(String pOID) {
		POID = pOID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getIsBundle() {
		return isBundle;
	}

	public void setIsBundle(String isBundle) {
		this.isBundle = isBundle;
	}

	public List<UnitsOfMeasure> getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	public void setUnitsOfMeasure(List<UnitsOfMeasure> unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	public List<ProductOfferingRef> getProductOfferings() {
		return productOfferings;
	}

	public void setProductOfferings(List<ProductOfferingRef> productOfferings) {
		this.productOfferings = productOfferings;
	}

	public List<ProductSpecCharacteristic> getProductSpecCharacteristics() {
		return productSpecCharacteristics;
	}

	public void setProductSpecCharacteristics(List<ProductSpecCharacteristic> productSpecCharacteristics) {
		this.productSpecCharacteristics = productSpecCharacteristics;
	}

	public List<BarCode> getAvailableBarcodes() {
		return availableBarcodes;
	}

	public void setAvailableBarcodes(List<BarCode> availableBarcodes) {
		this.availableBarcodes = availableBarcodes;
	}

	@Override
	public String toString() {
		return "ProductSpecification [name=" + name + ", description=" + description + ", brand=" + brand
				+ ", isBundle=" + isBundle + ", POID=" + POID + ", productNumber=" + productNumber + ", href=" + href
				+ ", id=" + id + ", unitsOfMeasure=" + unitsOfMeasure + ", productOfferings=" + productOfferings + "]";
	}

}
