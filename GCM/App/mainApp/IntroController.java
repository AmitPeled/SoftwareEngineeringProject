package mainApp;

import javafx.fxml.FXML;

public final class IntroController {
	private static GcmClient gcmClient;
	
	public static void setClient(GcmClient gcmClient) { IntroController.gcmClient = gcmClient; } // It's a weird hack I did. Need to look at it later
	
	@FXML
	public void onLoginButton() {
		gcmClient.switchScene(Scenes.LOGIN);
	}
	
	@FXML
	public void onRegisterButton() {
		return;
	}
	
	@FXML
	public void onExitButton() {
		return;
	}
}
