package init.initializers;

import editor.editPrice.EditPriceController;
import mainApp.GcmClient;
import utility.TextFieldUtility;

public class EditPriceInitializer implements Initializer {

	EditPriceController controller;
	
	public EditPriceInitializer(GcmClient gcmClient) {
		controller = new EditPriceController(gcmClient,
				gcmClient.getDataAccessObject(),
				0, 
				new TextFieldUtility());
	}
	
	@Override
	public Object getController() {
		return controller;
	}

	@Override
	public String getFxmlPath() { return "/fxml/editor/editPrice.fxml"; }

}
