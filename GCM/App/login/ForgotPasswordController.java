package login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mainApp.GcmClient;

public class ForgotPasswordController {

	private GcmClient gcmClient;
	
	public ForgotPasswordController(GcmClient gcmClient) {
		if(gcmClient == null) throw new IllegalArgumentException("gcmClient is null");
		
		this.gcmClient = gcmClient;
	}
	
	@FXML
	private TextField emailtxt;

	@FXML
	public void back(ActionEvent event) throws IOException {
		
		System.out.println("well, im going back");
		
		// going back to log in screen
		gcmClient.back();
	}

	@FXML
	public void resetPassword(ActionEvent event) throws IOException {
		// need to check valid email -> send random pass to his email (or his actual password)
		System.out.println("after validate the email -> send mail -> go back to the log in screen");
		gcmClient.back();
	}
}
