package model;

public class TimePeriod {
	String startDate, endDate;

	public TimePeriod() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "TimePeriod [startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
