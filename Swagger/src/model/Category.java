package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Category {
	String id, parentId, name, description;
	@XmlElement(name = "Catalog_Id")
	String Catalog_Id;
	List<ProductOfferingRef> productOfferings;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	@XmlElement(name = "Catalog_Id")
	public String getCatalog_Id() {
		return Catalog_Id;
	}

	@XmlElement(name = "Catalog_Id")
	public void setCatalog_Id(String catalog_Id) {
		Catalog_Id = catalog_Id;
	}

	public List<ProductOfferingRef> getProductOfferings() {
		return productOfferings;
	}

	public void setProductOfferings(List<ProductOfferingRef> productOfferings) {
		this.productOfferings = productOfferings;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", parentId=" + parentId + ", name=" + name + ", description=" + description
				+ ", Catalog_Id=" + Catalog_Id + ", productOfferings=" + productOfferings + "]";
	}

}
