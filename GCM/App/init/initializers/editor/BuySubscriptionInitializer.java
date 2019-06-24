package init.initializers.editor;

import editor.buySubscription.BuySubscriptionController;
import init.initializers.Initializer;
import mainApp.GcmClient;

public final class BuySubscriptionInitializer implements Initializer {

	private final BuySubscriptionController controller;
	public BuySubscriptionInitializer(GcmClient gcmClient) {
		// There should never be an instance of "TextFieldUtility" and all the methods should be static (pure functions)
		controller = new BuySubscriptionController(gcmClient, 1);
	}

	@Override
	public Object getController() {
		return controller;
	}

	@Override
	public String getFxmlPath() { return "/fxml/editor/buySubscription.fxml"; }
}
