package model;

import javax.xml.bind.annotation.XmlElement;

public class BundledProductSpecification {
	String POID, id, name, barCode, unitOfMeasureId;
	int quantity;
	@XmlElement(name = "ProductSpecification_Id")
	String ProductSpecification_Id;

	public BundledProductSpecification() {
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getUnitOfMeasureId() {
		return unitOfMeasureId;
	}

	public void setUnitOfMeasureId(String unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@XmlElement(name = "ProductSpecification_Id")
	public String getProductSpecification_Id() {
		return ProductSpecification_Id;
	}

	@XmlElement(name = "ProductSpecification_Id")
	public void setProductSpecification_Id(String productSpecification_Id) {
		ProductSpecification_Id = productSpecification_Id;
	}

	@Override
	public String toString() {
		return "BundledProductSpecification [POID=" + POID + ", id=" + id + ", name=" + name + ", barCode=" + barCode
				+ ", unitOfMeasureId=" + unitOfMeasureId + ", quantity=" + quantity + ", ProductSpecification_Id="
				+ ProductSpecification_Id + "]";
	}

}
