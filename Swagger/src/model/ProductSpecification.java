package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ProductSpecification {
	String name, description, POID, id, productNumber, lifeCycleStatus;
	List<UnitsOfMeasure> unitsOfMeasure;
	List<ProductOfferingRef> productOfferings;
	List<Option> productSpecCharacteristics;
	List<BarCode> availableBarcodes;
	@XmlElement(name = "isBundle")
	boolean isBundle;
	List<BundledProduct> bundledProductSpecifications;

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

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
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

	public List<Option> getProductSpecCharacteristics() {
		return productSpecCharacteristics;
	}

	public void setProductSpecCharacteristics(List<Option> productSpecCharacteristics) {
		this.productSpecCharacteristics = productSpecCharacteristics;
	}

	public List<BarCode> getAvailableBarcodes() {
		return availableBarcodes;
	}

	public void setAvailableBarcodes(List<BarCode> availableBarcodes) {
		this.availableBarcodes = availableBarcodes;
	}

	public String getLifeCycleStatus() {
		return lifeCycleStatus;
	}

	public void setLifeCycleStatus(String lifeCycleStatus) {
		this.lifeCycleStatus = lifeCycleStatus;
	}

	@XmlElement(name = "isBundle")
	public boolean isBundle() {
		return isBundle;
	}

	@XmlElement(name = "isBundle")
	public void setBundle(boolean isBundle) {
		this.isBundle = isBundle;
	}

	public List<BundledProduct> getBundledProductSpecifications() {
		return bundledProductSpecifications;
	}

	public void setBundledProductSpecifications(List<BundledProduct> bundledProductSpecifications) {
		this.bundledProductSpecifications = bundledProductSpecifications;
	}

	@Override
	public String toString() {
		return "ProductSpecification [name=" + name + ", description=" + description + ", POID=" + POID + ", id=" + id
				+ ", unitsOfMeasure=" + unitsOfMeasure + ", productOfferings=" + productOfferings
				+ ", productSpecCharacteristics=" + productSpecCharacteristics + ", availableBarcodes="
				+ availableBarcodes + ", isBundle=" + isBundle + ", bundledProductSpecifications="
				+ bundledProductSpecifications + "]";
	}

}
