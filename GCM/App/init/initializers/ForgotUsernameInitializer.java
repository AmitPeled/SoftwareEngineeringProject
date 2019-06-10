package init.initializers;

import login.ForgotUsernameController;
import mainApp.GcmClient;

public final class ForgotUsernameInitializer implements Initializer {

	private ForgotUsernameController controller;
	private final String fxmlFilePath;
	
	public ForgotUsernameInitializer(GcmClient gcmClient) {
		controller = new ForgotUsernameController(gcmClient);
		fxmlFilePath = "/fxml/login/ForgotUsernameScene.fxml";
	}
	
	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return fxmlFilePath; }
}
