package model;

import javax.xml.bind.annotation.XmlElement;

public class ProductOfferingPrice {
	String POID, id;
	float taxIncludedAmountValue, dutyFreeAmountValue, taxRate, percentage;
	@XmlElement(name = "ProductOffering_Id")
	String ProductOffering_Id;
	Quantity unitOfMeasure;

	public ProductOfferingPrice() {
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

	public String getProductOffering_Id() {
		return ProductOffering_Id;
	}

	public void setProductOffering_Id(String productOffering_Id) {
		ProductOffering_Id = productOffering_Id;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public Quantity getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(Quantity unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@Override
	public String toString() {
		return "ProductOfferingPrice [POID=" + POID + ", id=" + id + ", taxIncludedAmountValue="
				+ taxIncludedAmountValue + ", dutyFreeAmountValue=" + dutyFreeAmountValue + ", taxRate=" + taxRate
				+ ", percentage=" + percentage + ", ProductOffering_Id=" + ProductOffering_Id + ", unitOfMeasure="
				+ unitOfMeasure + "]";
	}

}
