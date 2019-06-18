package approvalReports.cityApprovalReports;

import java.io.Serializable;

import approvalReports.ActionTaken;
import maps.City;

public class CitySubmission implements Serializable {
	private static final long serialVersionUID = 1L;
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
	public ActionTaken getAction() {
		return actionTaken;
	}
}
