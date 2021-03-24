package model;

import javax.xml.bind.annotation.XmlElement;

public class Quantity {

	String unitOfMeasureName;
	int number;
	@XmlElement(name = "unitOfMeasure_Id")
	String unitOfMeasure_Id;

	public Quantity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUnitOfMeasureName() {
		return unitOfMeasureName;
	}

	public void setUnitOfMeasureName(String unitOfMeasureName) {
		this.unitOfMeasureName = unitOfMeasureName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@XmlElement(name = "unitOfMeasure_Id")
	public String getUnitOfMeasure_Id() {
		return unitOfMeasure_Id;
	}

	@XmlElement(name = "unitOfMeasure_Id")
	public void setUnitOfMeasure_Id(String unitOfMeasure_Id) {
		this.unitOfMeasure_Id = unitOfMeasure_Id;
	}

	@Override
	public String toString() {
		return "Quantity [unitOfMeasureName=" + unitOfMeasureName + ", number=" + number + ", unitOfMeasure_Id="
				+ unitOfMeasure_Id + "]";
	}

}
