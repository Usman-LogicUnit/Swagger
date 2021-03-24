package model;

import javax.xml.bind.annotation.XmlElement;

public class ProductSpecCharValueRef {
	String POID, id, value;

	@XmlElement(name = "ProductSpecCharacteristicRef_Id")
	String ProductSpecCharacteristicRef_Id;

	public ProductSpecCharValueRef() {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlElement(name = "ProductSpecCharacteristicRef_Id")
	public String getProductSpecCharacteristicRef_Id() {
		return ProductSpecCharacteristicRef_Id;
	}

	@XmlElement(name = "ProductSpecCharacteristicRef_Id")
	public void setProductSpecCharacteristicRef_Id(String productSpecCharacteristicRef_Id) {
		ProductSpecCharacteristicRef_Id = productSpecCharacteristicRef_Id;
	}

	@Override
	public String toString() {
		return "ProductSpecCharValueRef [POID=" + POID + ", id=" + id + ", value=" + value
				+ ", ProductSpecCharacteristicRef_Id=" + ProductSpecCharacteristicRef_Id + "]";
	}

}
