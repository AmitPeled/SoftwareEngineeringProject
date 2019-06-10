package login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mainApp.GcmClient;
import mainApp.SceneNames;

public class ForgotUsernameController {

	private GcmClient gcmClient;
	
	public ForgotUsernameController(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}
	
	@FXML
	private TextField emailtxt;

	@FXML
	public void back(ActionEvent event) throws IOException {
		gcmClient.back();
	}

	@FXML
	public void sendUsername(ActionEvent event) throws IOException {
		// need to check valid email -> send random pass to his email (or his actual password)
		System.out.println("after validate the email -> send mail -> go back to the log in screen");
		gcmClient.switchScene(SceneNames.LOGIN);
	}
}
