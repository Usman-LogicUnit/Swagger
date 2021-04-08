package model;

import javax.xml.bind.annotation.XmlElement;

public class OptionValue {
	String POID, id, value;
	@XmlElement(name = "isDefault")
	boolean isDefault;

	@XmlElement(name = "ProductSpecCharacteristic_Id")
	String ProductSpecCharacteristic_Id;

	public OptionValue() {
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

	@XmlElement(name = "ProductSpecCharacteristic_Id")
	public String getProductSpecCharacteristic_Id() {
		return ProductSpecCharacteristic_Id;
	}

	@XmlElement(name = "ProductSpecCharacteristic_Id")
	public void setProductSpecCharacteristic_Id(String productSpecCharacteristic_Id) {
		ProductSpecCharacteristic_Id = productSpecCharacteristic_Id;
	}

	@XmlElement(name = "isDefault")
	public boolean isDefault() {
		return isDefault;
	}

	@XmlElement(name = "isDefault")
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "OptionValue [POID=" + POID + ", id=" + id + ", value=" + value + ", isDefault=" + isDefault
				+ ", ProductSpecCharacteristic_Id=" + ProductSpecCharacteristic_Id + "]";
	}

}
