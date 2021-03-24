package model;

import javax.xml.bind.annotation.XmlElement;

public class ProductSpecCharValue {
	String POID, id, valueType, value, valueFrom, valueTo, rangeInterval, unitOfMeasure;
	@XmlElement(name = "isDefault")
	boolean isDefault;

	@XmlElement(name = "ProductSpecCharacteristic_Id")
	String ProductSpecCharacteristic_Id;

	public ProductSpecCharValue() {
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

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueFrom() {
		return valueFrom;
	}

	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}

	public String getValueTo() {
		return valueTo;
	}

	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}

	public String getRangeInterval() {
		return rangeInterval;
	}

	public void setRangeInterval(String rangeInterval) {
		this.rangeInterval = rangeInterval;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
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
		return "ProductSpecCharValue [POID=" + POID + ", id=" + id + ", valueType=" + valueType + ", value=" + value
				+ ", valueFrom=" + valueFrom + ", valueTo=" + valueTo + ", rangeInterval=" + rangeInterval
				+ ", unitOfMeasure=" + unitOfMeasure + ", isDefault=" + isDefault + ", ProductSpecCharacteristic_Id="
				+ ProductSpecCharacteristic_Id + "]";
	}

	

}
