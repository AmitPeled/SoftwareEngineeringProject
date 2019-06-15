package approvalReports.cityApprovalReports;

import approvalReports.ActionTaken;
import maps.City;

public class CitySubmission {
	private City city;
	private ActionTaken actionTaken;
	
	public CitySubmission(City city, ActionTaken actionTaken) {
		this.city = city;
		this.actionTaken = actionTaken;
	}
	public City getCity() {
		return city;
	}
	public String getActionTaken() {
		return actionTaken.toString();
	}
}
