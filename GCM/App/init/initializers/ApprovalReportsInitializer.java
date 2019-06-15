package init.initializers;

import approvalReports.ApprovalReportsController;
import mainApp.GcmClient;

public final class ApprovalReportsInitializer implements Initializer {

	private ApprovalReportsController controller;

	public ApprovalReportsInitializer(GcmClient gcmClient) {
		this.controller = ApprovalReportsController.getConrollerObject(gcmClient);
	}
	
	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return "/fxml/approvalReports/ApprovalReportsScene.fxml"; }

}
