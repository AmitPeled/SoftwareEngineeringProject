package init.initializers;

import mainApp.GcmClient;
import reports.ReportsController;

public final class ReportsInitializer implements Initializer{

	private final ReportsController reportsController;
	
	public ReportsInitializer(GcmClient gcmClient) {
		reportsController = new ReportsController(gcmClient);
	}
	
	@Override
	public Object getController() {	return reportsController; }

	@Override
	public String getFxmlPath() { return "/fxml/reports/ReportsScene.fxml"; }
}
