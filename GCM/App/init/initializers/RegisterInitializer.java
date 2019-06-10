package init.initializers;

import mainApp.GcmClient;
import register.RegisterSceneController;;

public final class RegisterInitializer implements Initializer {

	private RegisterSceneController controller;
	private final String fxmlFilePath;
	
	public RegisterInitializer(GcmClient gcmClient) {
		controller = new RegisterSceneController(gcmClient);
		fxmlFilePath = "/fxml/register/RegisterScene.fxml";
	}
	
	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return fxmlFilePath; }

}
