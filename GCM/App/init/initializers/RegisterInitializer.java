package init.initializers;

import mainApp.GcmClient;
import register.RegisterModel;
import register.RegisterSceneController;;

public final class RegisterInitializer implements Initializer {

	private RegisterSceneController controller;
	private RegisterModel model;
	private final String fxmlFilePath;
	
	public RegisterInitializer(GcmClient gcmClient) {
		model = new RegisterModel(gcmClient);
		controller = new RegisterSceneController(model);
		fxmlFilePath = "/fxml/register/RegisterScene.fxml";
	}
	
	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return fxmlFilePath; }

}
