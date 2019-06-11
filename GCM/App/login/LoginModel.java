package login;

import mainApp.GcmClient;
import mainApp.SceneNames;

public class LoginModel {

	private GcmClient gcmClient;
	// private UserDAO userDAO = new GcmDAO();

	public LoginModel(GcmClient gcmClient) {
		if (gcmClient == null)
			throw new IllegalArgumentException("GCM client is null");
		this.gcmClient = gcmClient;
	}

	public boolean login(String username, String password) {
		return gcmClient.getUserInfo().login(username, password);
		// if (username.equals("user") && password.equals("pass")) {
		// return RequestState.customer;
		// } else
		// return RequestState.wrongDetails;
	}

	public void switchScene(SceneNames scene) {
		gcmClient.switchScene(scene);
	}

	public void back() {
		gcmClient.back();
	}
}
