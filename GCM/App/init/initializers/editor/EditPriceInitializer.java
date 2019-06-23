package init.initializers.editor;

import editor.editPrice.EditPriceController;
import editor.pointOfInterest.PointOfInterestController;
import init.initializers.Initializer;
import mainApp.GcmClient;
import maps.Coordinates;
import utility.TextFieldUtility;

public class EditPriceInitializer implements Initializer {

	private final EditPriceController controller;
	public EditPriceInitializer(GcmClient gcmClient) {
		// There should never be an instance of "TextFieldUtility" and all the methods should be static (pure functions)
		controller = new EditPriceController(gcmClient,
				gcmClient.getDataAccessObject(), 
				1,  
				new TextFieldUtility());
	}

	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return "/fxml/editor/editPrice.fxml"; }
}
