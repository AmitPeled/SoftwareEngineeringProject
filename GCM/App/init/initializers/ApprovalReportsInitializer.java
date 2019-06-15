package init.initializers;

import java.util.List;

import approvalReports.ApprovalReportsController;
import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.tourApprovalReports.TourSubmission;
import mainApp.GcmClient;

public final class ApprovalReportsInitializer implements Initializer {

	private GcmClient gcmClient;
	private List<CitySubmission> citySubmissions;
	private List<TourSubmission> tourSubmissions;
	private List<SiteSubmission> siteSubmissions;
	
	public ApprovalReportsInitializer(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}
	
	@Override
	public Object getController() {
		return new ApprovalReportsController(gcmClient, gcmClient.getDataAccessObject(), citySubmissions, siteSubmissions, tourSubmissions);
	}

	@Override
	public String getFxmlPath() { return "/fxml/approvalReports/ApprovalReportsScene.fxml"; }

}
