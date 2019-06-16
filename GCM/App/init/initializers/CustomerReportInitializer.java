package init.initializers;

import mainApp.GcmClient;
import userDetailsPresentation.UserDetailsPresentationController;

public class CustomerReportInitializer implements Initializer {

	private UserDetailsPresentationController controller;
	private final String fxmlFilePath;
	
	public CustomerReportInitializer(GcmClient gcmClient) {
		controller = new UserDetailsPresentationController(gcmClient);
		fxmlFilePath = "/fxml/UserDetailsPresentationScene.fxml";
	}
	
	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return fxmlFilePath; }
	
	
}
