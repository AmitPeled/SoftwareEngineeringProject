package login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginSceneController {

	@FXML
	private TextField usernametxt;

	@FXML
	private TextField passwordtxt;

	@FXML
	public void LogIn(ActionEvent event) throws IOException {
		// send to data base to confirm that he is in the system
		System.out.println("Logging in....");
		LoginModel loginModel = new LoginModel();
		if (!loginModel.login(usernametxt.getText(), passwordtxt.getText())) {
			System.out.println("Log in faild");
		} else {
			// need to move to the main scene (search and shit)
			System.out.println("Log in success, go to app main scene");

		}

	}

	@FXML
	public void forgotPassword(ActionEvent event) throws IOException {
		// open scene with email to send a new password -> new password password confirm
		System.out.println("guess what, you are an idiot");
		// need to make new fmxl file to go to the main scene
		// need to do the same for the rest of the actionevents -> dont forget to close
		// current window!

		switchScene(event, "/fxml/ForgotPasswordScene.fxml");
	}

	@FXML
	public void forgotUsername(ActionEvent event) throws IOException {
		// open scene with email to send a new password -> new password password confirm
		System.out.println("guess what, you are an idiot");
		// need to make new fmxl file to go to the main scene
		// need to do the same for the rest of the actionevents -> dont forget to close
		// current window!

		//Parent ForgotUsernameParent = FXMLLoader.load(getClass().getResource("ForgotUsernameScene.fxml"));
		switchScene(event, "/fxml/ForgotUsernameScene.fxml");
	}
	
	@FXML
	public void register(ActionEvent event) throws IOException {
		// go to register scene
		switchScene(event,"/fxml/RegisterScene.fxml");
		System.out.println("going to register");

	}

	private void switchScene(ActionEvent event, String fxmlFilePath) throws IOException {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxmlFilePath));
		Parent ForgotUsernameParent = loader.load();
		Scene ForgotUsernameScene = new Scene(ForgotUsernameParent);
		ForgotUsernameScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(ForgotUsernameScene);
		window.show();
	}

}
