package init.initializers.editor;

import editor.addCity.AddCityController;
import init.initializers.Initializer;
import mainApp.GcmClient;
import utility.TextFieldUtility;

public final class AddCityInitializer implements Initializer {

	private final AddCityController controller;
	public AddCityInitializer(GcmClient gcmClient) {
		// There should never be an instance of "TextFieldUtility" and all the methods should be static (pure functions)
		controller = new AddCityController(gcmClient,gcmClient.getDataAccessObject(), new TextFieldUtility());
	}

	@Override
	public Object getController() {
		return controller;
	}

	@Override
	public String getFxmlPath() { return "/fxml/editor/addCity.fxml"; }
}
