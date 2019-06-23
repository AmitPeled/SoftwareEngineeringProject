package init.initializers.editor;

import editor.tour.TourController;
import init.initializers.Initializer;
import mainApp.GcmClient;
import maps.Tour;
import utility.TextFieldUtility;

public final class TourInitializer implements Initializer {

	private final TourController controller;
	public TourInitializer(GcmClient gcmClient) {
		// There should never be an instance of "TextFieldUtility" and all the methods should be static (pure functions)
		controller = new TourController(gcmClient, 1, 1, new Tour(null), new TextFieldUtility());
	}

	@Override
	public Object getController() {
		return controller;
	}

	@Override
	public String getFxmlPath() { return "/fxml/editor/tour.fxml"; }
}
