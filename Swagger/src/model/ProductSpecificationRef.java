package model;

import javax.xml.bind.annotation.XmlElement;

public class ProductSpecificationRef {
	String POID, id, name, description, conversionFactor, productSerialNumber;
	@XmlElement(name = "ProductOffering_Id")
	String ProductOffering_Id;

	@XmlElement(name = "Product_Id")
	String Product_Id;

	public ProductSpecificationRef() {
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

	public String getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(String conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public String getProductSerialNumber() {
		return productSerialNumber;
	}

	public void setProductSerialNumber(String productSerialNumber) {
		this.productSerialNumber = productSerialNumber;
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
		return "ProductSpecificationRef [POID=" + POID + ", id=" + id + ", name=" + name + ", description="
				+ description + ", conversionFactor=" + conversionFactor + ", productSerialNumber="
				+ productSerialNumber + ", ProductOffering_Id=" + ProductOffering_Id + ", Product_Id=" + Product_Id
				+ "]";
	}

}
