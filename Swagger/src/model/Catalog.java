package model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Catalog {
	String id, POID, name, type;
	@ApiModelProperty(position = 1, hidden=true, notes = "catalog valid for")
	TimePeriod validFor;
	@ApiModelProperty(position = 1, hidden=true, notes = "category ref to catalog")
	List<CategoryRef> categories;

	public Catalog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPOID() {
		return POID;
	}

	public void setPOID(String pOID) {
		POID = pOID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TimePeriod getValidFor() {
		return validFor;
	}

	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}

	public List<CategoryRef> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryRef> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Catalog [id=" + id + ", POID=" + POID + ", name=" + name + ", type=" + type + ", validFor=" + validFor
				+ ", categories=" + categories + "]";
	}

}
