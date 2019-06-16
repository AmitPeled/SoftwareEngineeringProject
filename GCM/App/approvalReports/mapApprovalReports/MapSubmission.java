package approvalReports.mapApprovalReports;

import approvalReports.ActionTaken;
import maps.Map;

public class MapSubmission {
	private Map map;
	private ActionTaken actionTaken;
	/**
	 * the id of the city contained in. if ADD/DELETE, field contains the id of the
	 * city added to or deleted from
	 */
	private int containingCityID;

	public MapSubmission(Map map, ActionTaken actionTaken) {
		this.setContainingCityID(-1);
		this.map = map;
		this.actionTaken = actionTaken;
	}

	public MapSubmission(int containingCityID, Map map, ActionTaken actionTaken) {
		this.setContainingCityID(containingCityID);
		this.map = map;
		this.actionTaken = actionTaken;
	}

	public Map getMap() {
		return map;
	}

	public String getActionTaken() {
		return actionTaken.toString();
	}

	public int getContainingCityID() {
		return containingCityID;
	}

	public void setContainingCityID(int containingCityID) {
		this.containingCityID = containingCityID;
	}
}
