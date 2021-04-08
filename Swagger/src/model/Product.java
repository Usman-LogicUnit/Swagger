package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Product {
	String id, POID, description, name, imageURL, status;
	List<UnitsOfMeasure> unitsOfMeasure;
	List<Option> productSpecCharacteristics;
	String startDate, endDate;
	float taxRate, price;
	@XmlElement(name = "Category_Id")
	String Category_Id;
	List<BarCode> barCodes;
	@XmlElement(name = "isBundle")
	boolean isBundle;
	List<BundledProduct> bundledProductSpecifications;

	public Product() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPOID() {
		return POID;
	}

	public void setPOID(String pOID) {
		POID = pOID;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "isBundle")
	public boolean isBundle() {
		return isBundle;
	}

	@XmlElement(name = "isBundle")
	public void setBundle(boolean isBundle) {
		this.isBundle = isBundle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UnitsOfMeasure> getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	public void setUnitsOfMeasure(List<UnitsOfMeasure> unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	public List<Option> getProductSpecCharacteristics() {
		return productSpecCharacteristics;
	}

	public void setProductSpecCharacteristics(List<Option> productSpecCharacteristics) {
		this.productSpecCharacteristics = productSpecCharacteristics;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@XmlElement(name = "Category_Id")
	public String getCategory_Id() {
		return Category_Id;
	}

	@XmlElement(name = "Category_Id")
	public void setCategory_Id(String category_Id) {
		Category_Id = category_Id;
	}

	public List<BarCode> getBarCodes() {
		return barCodes;
	}

	public void setBarCodes(List<BarCode> barCodes) {
		this.barCodes = barCodes;
	}

	public List<BundledProduct> getBundledProductSpecifications() {
		return bundledProductSpecifications;
	}

	public void setBundledProductSpecifications(List<BundledProduct> bundledProductSpecifications) {
		this.bundledProductSpecifications = bundledProductSpecifications;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Product [description=" + description + ", name=" + name + ", imageURL=" + imageURL + ", unitsOfMeasure="
				+ unitsOfMeasure + ", productSpecCharacteristics=" + productSpecCharacteristics + ", startDate="
				+ startDate + ", endDate=" + endDate + ", taxRate=" + taxRate + ", price=" + price + ", Category_Id="
				+ Category_Id + ", barCodes=" + barCodes + ", isBundle=" + isBundle + ", bundledProductSpecifications="
				+ bundledProductSpecifications + "]";
	}

}
