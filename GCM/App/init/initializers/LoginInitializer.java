package init.initializers;

import login.LoginModel;
import login.LoginSceneController;
import mainApp.GcmClient;

public final class LoginInitializer implements Initializer {
	private LoginModel model;
	private LoginSceneController controller;
	private final String fxmlPath = "/fxml/login/LoginScene.fxml";
	
	public LoginInitializer(GcmClient gcmClient) {
		model = new LoginModel(gcmClient);
		controller = new LoginSceneController(model);
	}
	
	@Override
	public Object getController() { return controller; }
	
	@Override
	public String getFxmlPath() { return fxmlPath; }
}
