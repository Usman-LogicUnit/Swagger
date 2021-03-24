package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ProductSpecCharacteristic {
	String POID, id, name, description;
	@XmlElement(name = "ProductSpecification_Id")
	String ProductSpecification_Id;

	List<ProductSpecCharValue> productSpecCharValues = new ArrayList<>();

	public ProductSpecCharacteristic() {
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

	@XmlElement(name = "ProductSpecification_Id")
	public String getProductSpecification_Id() {
		return ProductSpecification_Id;
	}

	@XmlElement(name = "ProductSpecification_Id")
	public void setProductSpecification_Id(String productSpecification_Id) {
		ProductSpecification_Id = productSpecification_Id;
	}

	public List<ProductSpecCharValue> getProductSpecCharValues() {
		return productSpecCharValues;
	}

	public void setProductSpecCharValues(List<ProductSpecCharValue> productSpecCharValues) {
		this.productSpecCharValues = productSpecCharValues;
	}

	@Override
	public String toString() {
		return "ProductSpecCharacteristic [POID=" + POID + ", id=" + id + ", name=" + name + ", description="
				+ description + ", ProductSpecification_Id=" + ProductSpecification_Id + ", productSpecCharValues="
				+ productSpecCharValues + "]";
	}

}
