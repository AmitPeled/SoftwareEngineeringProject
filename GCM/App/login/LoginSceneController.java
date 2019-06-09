package login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainApp.Controller;
import mainApp.GcmClient;
import mainApp.SceneNames;
import javafx.scene.Node;

public class LoginSceneController implements Controller {

	private GcmClient gcmClient;
	
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
			System.out.println("Log in success, go to app main scene");
			gcmClient.back();
		}

	}

	@FXML
	public void forgotPassword(ActionEvent event) throws IOException {
		// open scene with email to send a new password -> new password password confirm
		System.out.println("guess what, you are an idiot");
		gcmClient.switchScene(SceneNames.FORGOT_PASSWORD);
	}

	@FXML
	public void forgotUsername(ActionEvent event) throws IOException {
		// open scene with email to send a new password -> new password password confirm
		System.out.println("guess what, you are an idiot");
		gcmClient.switchScene(SceneNames.FORGOT_USERNAME);
	}
	
	@FXML
	public void register(ActionEvent event) throws IOException {
		gcmClient.switchScene(SceneNames.REGISTER);
		System.out.println("going to register");
	}

	@Override
	public void setClient(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}

}
