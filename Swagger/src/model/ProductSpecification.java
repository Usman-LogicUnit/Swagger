package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ProductSpecification {
	String name, description, brand, POID, productNumber, href, id;
	List<UnitsOfMeasure> unitsOfMeasure;
	List<ProductOfferingRef> productOfferings;
	List<ProductSpecCharacteristic> productSpecCharacteristics;
	List<BarCode> availableBarcodes;
	@XmlElement(name = "isBundle")
	boolean isBundle;
	List<BundledProductSpecification> bundledProductSpecifications;

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

	@XmlElement(name = "isBundle")
	public boolean isBundle() {
		return isBundle;
	}

	@XmlElement(name = "isBundle")
	public void setBundle(boolean isBundle) {
		this.isBundle = isBundle;
	}

	public List<BundledProductSpecification> getBundledProductSpecifications() {
		return bundledProductSpecifications;
	}

	public void setBundledProductSpecifications(List<BundledProductSpecification> bundledProductSpecifications) {
		this.bundledProductSpecifications = bundledProductSpecifications;
	}

	@Override
	public String toString() {
		return "ProductSpecification [name=" + name + ", description=" + description + ", brand=" + brand + ", POID="
				+ POID + ", productNumber=" + productNumber + ", href=" + href + ", id=" + id + ", unitsOfMeasure="
				+ unitsOfMeasure + ", productOfferings=" + productOfferings + ", productSpecCharacteristics="
				+ productSpecCharacteristics + ", availableBarcodes=" + availableBarcodes + ", isBundle=" + isBundle
				+ ", bundledProductSpecifications=" + bundledProductSpecifications + "]";
	}

}
