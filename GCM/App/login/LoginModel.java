package login;

import mainApp.GcmClient;
import mainApp.SceneNames;
import queries.RequestState;

public class LoginModel {

	private GcmClient gcmClient;
	
	public LoginModel(GcmClient gcmClient) { this.gcmClient = gcmClient; }

	public RequestState login(String username, String password) {
		if (username.equals("user") && password.equals("pass")) {
			return RequestState.customer;
		} else
			return RequestState.wrongDetails;
	}

	public void switchScene(SceneNames scene) { gcmClient.switchScene(scene); }

	public void back() { gcmClient.back(); }
}
