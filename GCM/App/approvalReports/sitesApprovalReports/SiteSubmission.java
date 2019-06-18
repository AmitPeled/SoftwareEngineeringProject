package approvalReports.sitesApprovalReports;

import java.io.Serializable;

import approvalReports.ActionTaken;
import approvalReports.ObjectsEnum;
import maps.Site;

public class SiteSubmission implements Serializable {
	private static final long serialVersionUID = 1L;
	private Site site;
	private ActionTaken actionTaken;
	/**
	 * if actionTaken is ADD/DELETE, id is of the of the object the Tour added to of
	 * deleted from. else id is of the city the object contained in.
	 */
	private int containingObjectID;

	/**
	 * if ADD/DELETE, type is the ObjectType site added to or deleted from
	 * (TOUR/MAP/CITY), else ObjectType = CITY by default.
	 */
	private ObjectsEnum containingObjectType;

	public SiteSubmission(Site site, ActionTaken actionTaken) {
		this.containingObjectID = -1;
		this.containingObjectType = ObjectsEnum.CITY;
		this.site = site;
		this.actionTaken = actionTaken;
	}

	public SiteSubmission(int containingObjectID, ObjectsEnum containingObjectType, Site site,
			ActionTaken actionTaken) {
		this.site = site;
		this.actionTaken = actionTaken;
		this.containingObjectID = containingObjectID;
		this.containingObjectType = containingObjectType;
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

	public ActionTaken getAction() {
		return actionTaken;
	}
}
