package dataAccess.customer;

import java.sql.Date;

public class CityPurchase {
	private Date startDate;
	private Date endDate;
	int cityId;

	public CityPurchase(Date startDate, Date endDate, int cityId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.cityId = cityId;
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

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public void print() {
		System.out.println("CityId = " + this.cityId + " Start date = " + this.startDate + " End date = " + this.endDate);

	}

}
