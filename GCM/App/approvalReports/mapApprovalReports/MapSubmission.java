package approvalReports.mapApprovalReports;

import java.io.File;
import java.io.Serializable;

import approvalReports.ActionTaken;
import maps.Map;

public class MapSubmission implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map map;
	private byte[] mapFile;
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

	public MapSubmission(int containingCityID, Map map, byte[] mapFile, ActionTaken actionTaken) {
		this.setContainingCityID(containingCityID);
		this.map = map;
		this.setMapFile(mapFile);
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

	public byte[] getMapFile() {
		return mapFile;
	}

	public void setMapFile(byte[] mapFile) {
		this.mapFile = mapFile;
	}

	public ActionTaken getAction() {
		return actionTaken;
	}
}
