package approvalReports.tourApprovalReports;

import approvalReports.ActionTaken;
import approvalReports.ObjectsEnum;
import maps.Tour;

public class TourSubmission {
	private Tour tour;
	private ActionTaken actionTaken;
	/**
	 * if actionTaken is ADD, id is of the of the object the Tour added to. else id
	 * is of the city the object contained in.
	 */
	private int containingObjectID;

	/**
	 * if ADD, type is the ObjectType site added to (MAP/CITY), else ObjectType
	 * = CITY by default.
	 */
	private ObjectsEnum containingObjectType;

	public TourSubmission(Tour tour, ActionTaken actionTaken) {
		this.setContainingObjectID(-1);
		this.setContainingObjectType(ObjectsEnum.CITY);
		this.tour = tour;
		this.actionTaken = actionTaken;
	}

	public TourSubmission(int containingObjectID, ObjectsEnum containingObjectType, Tour tour,
			ActionTaken actionTaken) {
		this.tour = tour;
		this.actionTaken = actionTaken;
		this.setContainingObjectID(containingObjectID);
		this.setContainingObjectType(containingObjectType);
	}

	public Tour getTour() {
		return tour;
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
