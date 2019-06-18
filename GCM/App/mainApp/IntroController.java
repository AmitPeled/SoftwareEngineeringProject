package mainApp;

import javafx.fxml.FXML;

public final class IntroController{
	private GcmClient gcmClient;
	
	public IntroController(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}

	@FXML
	public void onLoginButton() {
		gcmClient.switchScene(SceneNames.LOGIN);
	}
	
	@FXML
	public void onRegisterButton() {
		gcmClient.switchScene(SceneNames.REGISTER);;
	}
	
	@FXML
	public void onGuestEntranceButton() {
		gcmClient.switchScene(SceneNames.SEARCH);;
	}
	
	@FXML
	public void onExitButton() {
		gcmClient.shutdown();
	}
}
