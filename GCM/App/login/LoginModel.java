package login;

import mainApp.GcmClient;
import mainApp.SceneNames;
import queries.RequestState;

public class LoginModel {

	private GcmClient gcmClient;

	public LoginModel(GcmClient gcmClient) {
		if (gcmClient == null)
			throw new IllegalArgumentException("GCM client is null");
		this.gcmClient = gcmClient;
	}
 
	public boolean login(String username, String password) {
		return gcmClient.getUserInfo().login(username, password);
	}

	public void switchScene(SceneNames scene) {
		gcmClient.switchScene(scene);
	}

	public void back() {
		gcmClient.back();
	}
	
	public RequestState getUserState() {
		return gcmClient.getUserInfo().getState();
		
	}
	
}
 