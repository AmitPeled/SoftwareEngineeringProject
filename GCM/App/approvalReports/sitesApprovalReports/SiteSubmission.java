package approvalReports.sitesApprovalReports;

import approvalReports.ActionTaken;
import approvalReports.ObjectsEnum;
import maps.Site;

public class SiteSubmission {
	private Site site;
	private ActionTaken actionTaken;
	/**
	 * if actionTaken is ADD, id is of the of the object the Tour added to. else id
	 * is of the city the object contained in.
	 */
	private int containingObjectID;

	/**
	 * if ADD, type is the ObjectType site added to (TOUR/MAP/CITY), else ObjectType
	 * = CITY by default.
	 */
	private ObjectsEnum containingObjectType;

	public SiteSubmission(Site site, ActionTaken actionTaken) {
		this.site = site;
		this.actionTaken = actionTaken;
	}

	public Site getSite() {
		return site;
	}

	public String getActionTaken() {
		return actionTaken.toString();
	}

	public ObjectsEnum getContainingObjectType() {
		return containingObjectType;
	}

	public void setContainingObjectType(ObjectsEnum containingObjectType) {
		this.containingObjectType = containingObjectType;
	}

	public int getContainingObjectID() {
		return containingObjectID;
	}

	public void setContainingObjectID(int containingObjectID) {
		this.containingObjectID = containingObjectID;
	}
}
