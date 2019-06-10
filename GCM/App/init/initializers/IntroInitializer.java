package init.initializers;

import mainApp.GcmClient;
import mainApp.IntroController;

public final class IntroInitializer implements Initializer {

	private IntroController controller;
	private final String fxmlFilePath;
	
	public IntroInitializer(GcmClient gcmClient) {
		controller = new IntroController(gcmClient);
		fxmlFilePath = "/fxml/Intro.fxml";
	}
	
	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return fxmlFilePath; }

}
