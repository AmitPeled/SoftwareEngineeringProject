package init.initializers;

import mainApp.GcmClient;
import purchase.PurchaseController;
import purchase.PurchaseModel;

public class PurchaseInitializer implements Initializer {

	private PurchaseController controller;
	private PurchaseModel model;
	private final String fxmlFilePath;
	
	public PurchaseInitializer(GcmClient gcmClient) {
		model = new PurchaseModel(gcmClient);
		controller = new PurchaseController(model);
		fxmlFilePath = "/fxml/purchase/Purchase.fxml";
	}
	
	@Override
	public Object getController() {	return controller;}

	@Override
	public String getFxmlPath() {return fxmlFilePath;}

}
