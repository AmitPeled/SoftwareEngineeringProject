package purchase;

import dataAccess.users.PurchaseDetails;
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
	
	public void purchase(int cityId,int timeInterval, PurchaseDetails purchaseDetails) {
		gcmClient.getDataAccessObject().purchaseCity(cityId, timeInterval, purchaseDetails);
		System.out.println("purchase command has been send");
	}

	
}
