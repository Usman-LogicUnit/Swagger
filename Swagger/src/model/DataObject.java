package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DataObject {
	String description, isBundle, name, number;
	List<UnitsOfMeasure> unitsOfMeasure;
	List<ProductSpecCharacteristic> productSpecCharacteristics;
	String offeringName, offeringDescription;
	String offeringStartDate, offeringEndDate;
	String conversionFactor;
	String priceName, priceDescription, priceType;
	float taxIncludedAmountValue, dutyFreeAmountValue, taxRate, percentage;
	@XmlElement(name = "Category_Id")
	String Category_Id;
	List<BarCode> barCodes;

	public DataObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsBundle() {
		return isBundle;
	}

	public void setIsBundle(String isBundle) {
		this.isBundle = isBundle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<UnitsOfMeasure> getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	public void setUnitsOfMeasure(List<UnitsOfMeasure> unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	public List<ProductSpecCharacteristic> getProductSpecCharacteristics() {
		return productSpecCharacteristics;
	}

	public void setProductSpecCharacteristics(List<ProductSpecCharacteristic> productSpecCharacteristics) {
		this.productSpecCharacteristics = productSpecCharacteristics;
	}

	public String getOfferingName() {
		return offeringName;
	}

	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}

	public String getOfferingDescription() {
		return offeringDescription;
	}

	public void setOfferingDescription(String offeringDescription) {
		this.offeringDescription = offeringDescription;
	}

	public String getOfferingStartDate() {
		return offeringStartDate;
	}

	public void setOfferingStartDate(String offeringStartDate) {
		this.offeringStartDate = offeringStartDate;
	}

	public String getOfferingEndDate() {
		return offeringEndDate;
	}

	public void setOfferingEndDate(String offeringEndDate) {
		this.offeringEndDate = offeringEndDate;
	}

	public String getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(String conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public String getPriceDescription() {
		return priceDescription;
	}

	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public float getTaxIncludedAmountValue() {
		return taxIncludedAmountValue;
	}

	public void setTaxIncludedAmountValue(float taxIncludedAmountValue) {
		this.taxIncludedAmountValue = taxIncludedAmountValue;
	}

	public float getDutyFreeAmountValue() {
		return dutyFreeAmountValue;
	}

	public void setDutyFreeAmountValue(float dutyFreeAmountValue) {
		this.dutyFreeAmountValue = dutyFreeAmountValue;
	}

	public float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
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

}
