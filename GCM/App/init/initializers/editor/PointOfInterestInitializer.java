package init.initializers.editor;

import editor.pointOfInterest.PointOfInterestController;
import init.initializers.Initializer;
import mainApp.GcmClient;
import maps.Coordinates;
import utility.TextFieldUtility;

public class PointOfInterestInitializer implements Initializer {

	private final PointOfInterestController controller;
	public PointOfInterestInitializer(GcmClient gcmClient) {
		// There should never be an instance of "TextFieldUtility" and all the methods should be static (pure functions)
		controller = new PointOfInterestController(gcmClient,
				gcmClient.getDataAccessObject(), 
				0, 
				new Coordinates(), 
				new TextFieldUtility());
	}

	@Override
	public Object getController() { return controller; }

	@Override
	public String getFxmlPath() { return "/fxml/editor/pointOfInterest.fxml"; }
}
