package init.initializers.editor;

import editor.addMap.AddMapController;
import init.initializers.Initializer;
import mainApp.GcmClient;
import utility.TextFieldUtility;

public final class AddMapInitializer implements Initializer {

	private final AddMapController controller;
	public AddMapInitializer(GcmClient gcmClient) {
		// There should never be an instance of "TextFieldUtility" and all the methods should be static (pure functions)
		controller = new AddMapController(gcmClient, 1, new TextFieldUtility());
	}

	@Override
	public Object getController() {
		return controller;
	}

	@Override
	public String getFxmlPath() { return "/fxml/editor/addMap.fxml"; }
}
