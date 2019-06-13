package purchase;

import mainApp.GcmClient;
import mainApp.SceneNames;

public class PurchaseModel {

	private GcmClient gcmClient;

	public PurchaseModel(GcmClient gcmClient) {
		if (gcmClient == null)
			throw new IllegalArgumentException("GCM client is null");
		this.gcmClient = gcmClient;
	}
	
	
	public void switchScene(SceneNames scene) {
		gcmClient.switchScene(scene);
	}
	
	public void back() {
		gcmClient.back();
	}

	
}
