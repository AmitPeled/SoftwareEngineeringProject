package mainApp;

import javafx.fxml.FXML;

public final class IntroController implements Controller {
	private GcmClient gcmClient;
	
	@Override
	public void setClient(GcmClient gcmClient) { this.gcmClient = gcmClient; } 
	
	@FXML
	public void onLoginButton() {
		gcmClient.switchScene(SceneNames.LOGIN);
	}
	
	@FXML
	public void onRegisterButton() {
		gcmClient.switchScene(SceneNames.REGISTER);;
	}
	
	@FXML
	public void onExitButton() {
		return;
	}
}
