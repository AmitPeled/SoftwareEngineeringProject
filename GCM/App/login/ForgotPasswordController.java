package login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mainApp.GcmClient;
import mainApp.SceneNames;
import utility.TextFieldUtility;

public class ForgotPasswordController {

	private GcmClient gcmClient;

	public ForgotPasswordController(GcmClient gcmClient) {
		if (gcmClient == null)
			throw new IllegalArgumentException("gcmClient is null");

		this.gcmClient = gcmClient;
	}

	@FXML
	private TextField emailtxt;

	@FXML
	public void back(ActionEvent event) throws IOException { gcmClient.back(); }

	@FXML
	public void resetPassword(ActionEvent event) throws IOException {
		if (!TextFieldUtility.validEmail(emailtxt.getText())) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
					"invalid email address");

			emailtxt.clear();
		}
		else { gcmClient.switchScene(SceneNames.LOGIN); }
	}
}
