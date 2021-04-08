package model;

import javax.xml.bind.annotation.XmlElement;

public class UnitsOfMeasure {
	String name, relation, POID, id;
	int conversionFactor;
	@XmlElement(name = "isDefault")
	boolean isDefault;
	@XmlElement(name = "ProductSpecification_Id")
	String ProductSpecification_Id;

	public UnitsOfMeasure() {
		super();
	}

	@XmlElement(name = "ProductSpecification_Id")
	public String getProductSpecification_Id() {
		return ProductSpecification_Id;
	}

	@XmlElement(name = "ProductSpecification_Id")
	public void setProductSpecification_Id(String productSpecification_Id) {
		ProductSpecification_Id = productSpecification_Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "isDefault")
	public boolean isDefault() {
		return isDefault;
	}

	@XmlElement(name = "isDefault")
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPOID() {
		return POID;
	}

	public void setPOID(String pOID) {
		POID = pOID;
	}

	public int getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(int conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public String toString() {
		return "UnitsOfMeasure [name=" + name + ", relation=" + relation + ", POID=" + POID + ", id=" + id
				+ ", conversionFactor=" + conversionFactor + ", isDefault=" + isDefault + ", ProductSpecification_Id="
				+ ProductSpecification_Id + "]";
	}

}
