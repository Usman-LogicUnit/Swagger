package model;

import javax.xml.bind.annotation.XmlElement;

public class ProductRef {
	String POID, id, name;
	@XmlElement(name = "ProductOffering_Id")
	String ProductOffering_Id;

	@XmlElement(name = "Product_Id")
	String Product_Id;

	public ProductRef() {
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

	@XmlElement(name = "ProductOffering_Id")
	public String getProductOffering_Id() {
		return ProductOffering_Id;
	}

	@XmlElement(name = "ProductOffering_Id")
	public void setProductOffering_Id(String productOffering_Id) {
		ProductOffering_Id = productOffering_Id;
	}

	@XmlElement(name = "Product_Id")
	public String getProduct_Id() {
		return Product_Id;
	}

	@XmlElement(name = "Product_Id")
	public void setProduct_Id(String product_Id) {
		Product_Id = product_Id;
	}

	@Override
	public String toString() {
		return "ProductRef [POID=" + POID + ", id=" + id + ", name=" + name + ", ProductOffering_Id="
				+ ProductOffering_Id + ", Product_Id=" + Product_Id + "]";
	}

}
