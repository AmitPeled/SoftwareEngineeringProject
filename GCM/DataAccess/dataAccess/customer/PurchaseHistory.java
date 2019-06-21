package dataAccess.customer;

import java.io.Serializable;
import java.sql.Date;

import maps.City;

public class PurchaseHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date startDate;
	private Date endDate;
	City city;

	public PurchaseHistory(Date startDate, Date endDate, City city) {
		if (city == null)
			throw new IllegalArgumentException("Purchased city cannot be null.");
		this.startDate = startDate;
		this.endDate = endDate;
		this.city = city;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public City getCityId() {
		return city;
	}

	public void print() {
		System.out.println(toString());
	}

	public String toString() {
		String cityName = String.format("%-20.20s", city.getName());
		return ("City name: " + cityName + " Start date: " + this.startDate + "\tEnd date: " + this.endDate);
	}

}
