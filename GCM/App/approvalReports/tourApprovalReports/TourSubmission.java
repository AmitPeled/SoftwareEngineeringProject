package approvalReports.tourApprovalReports;

import approvalReports.ActionTaken;
import maps.Tour;

public class TourSubmission {
	private Tour tour;
	private ActionTaken actionTaken;

	public TourSubmission(Tour tour, ActionTaken actionTaken) {
		this.tour = tour;
		this.actionTaken = actionTaken;
	}
	public Tour getTour() {
		return tour;
	}
	public String getActionTaken() {
		return actionTaken.toString();
	}
}
