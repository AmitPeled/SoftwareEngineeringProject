package approvalReports.mapApprovalReports;

import approvalReports.ActionTaken;
import maps.Map;

public class MapSubmission {
	private Map map;
	private ActionTaken actionTaken;

	public MapSubmission(Map map, ActionTaken actionTaken) {
		this.map = map;
		this.actionTaken = actionTaken;
	}
	public Map getMap() {
		return map;
	}
	public String getActionTaken() {
		return actionTaken.toString();
	}
}
