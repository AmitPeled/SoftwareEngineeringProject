package init.initializers;

import mainApp.GcmClient;
import menu.MenuController;

public final class MenuInitializer implements Initializer {

	private MenuController controller;
	private final String fxmlFilePath;
	
	public MenuInitializer(GcmClient gcmClient) {
		controller = new MenuController(gcmClient);
		fxmlFilePath = "/fxml/menu/MenuScene.fxml";
	}
	
	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return fxmlFilePath; }

}
