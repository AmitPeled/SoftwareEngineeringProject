package approvalReports.sitesApprovalReports;

import approvalReports.ActionTaken;
import maps.Site;

public class SiteSubmission {
	private Site site;
	private ActionTaken actionTaken;

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
}
