package model;

import javax.xml.bind.annotation.XmlElement;

public class BarCode {
	String POID, id, code;
	@XmlElement(name = "ProductSpecification_Id")
	String ProductSpecification_Id;
	@XmlElement(name = "unitOfMeasureRef_Id")
	String unitOfMeasureRef_Id;

	public BarCode() {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProductSpecification_Id() {
		return ProductSpecification_Id;
	}

	public void setProductSpecification_Id(String productSpecification_Id) {
		ProductSpecification_Id = productSpecification_Id;
	}

	public String getUnitOfMeasureRef_Id() {
		return unitOfMeasureRef_Id;
	}

	public void setUnitOfMeasureRef_Id(String unitOfMeasureRef_Id) {
		this.unitOfMeasureRef_Id = unitOfMeasureRef_Id;
	}

	@Override
	public String toString() {
		return "BarCode [POID=" + POID + ", id=" + id + ", code=" + code + ", ProductSpecification_Id="
				+ ProductSpecification_Id + ", unitOfMeasureRef_Id=" + unitOfMeasureRef_Id + "]";
	}

}
