package login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApp.GcmClient;

public class ForgotUsernameController {

	private GcmClient gcmClient;
	
	public ForgotUsernameController(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}
	
	@FXML
	private TextField emailtxt;

	@FXML
	public void back(ActionEvent event) throws IOException {
		System.out.println("well, im going back");
		returnToLoginScreen(event);
	}

	@FXML
	public void sendUsername(ActionEvent event) throws IOException {
		// need to check valid email -> send random pass to his email (or his actual password)
		System.out.println("after validate the email -> send mail -> go back to the log in screen");
		returnToLoginScreen(event);
	}
	
	private void returnToLoginScreen(ActionEvent event) throws IOException {
		// going back to log in screen
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//Parent LoginSceneParent = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/login/LoginScene.fxml"));
		Parent LoginSceneParent = loader.load();
		Scene LoginSceneScene = new Scene(LoginSceneParent);
		LoginSceneScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(LoginSceneScene);
		window.show();
	}
}
