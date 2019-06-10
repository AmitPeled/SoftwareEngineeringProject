package init.initializers;

import login.ForgotPasswordController;
import mainApp.GcmClient;

public final class ForgotPasswordInitializer implements Initializer {

	private ForgotPasswordController controller;
	private final String fxmlFilePath;
	
	public ForgotPasswordInitializer(GcmClient gcmClient) {
		controller = new ForgotPasswordController(gcmClient);
		fxmlFilePath = "/fxml/login/ForgotPasswordScene.fxml";
	}
	
	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return fxmlFilePath; }
}
