package login;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mainApp.SceneNames;
import queries.RequestState;

public class LoginSceneController {

	private final LoginModel loginModel;

	@FXML
	private TextField usernametxt;

	@FXML
	private TextField passwordtxt;

	public LoginSceneController(LoginModel model) {
		loginModel = model;
	}

	@FXML
	public void LogIn(ActionEvent event) throws IOException {
		// send to data base to confirm that he is in the system
		System.out.println("Logging in....");
		if (loginModel.login(usernametxt.getText(), passwordtxt.getText()) == RequestState.wrongDetails) {
			System.out.println("Log in faild");
		} else {
			System.out.println("Log in success, go to app main scene");
			loginModel.switchScene(SceneNames.MENU);
		}

	}

	@FXML
	public void forgotPassword(ActionEvent event) throws IOException {
		// open scene with email to send a new password -> new password password confirm
		System.out.println("guess what, you are an idiot");
		loginModel.switchScene(SceneNames.FORGOT_PASSWORD);
	}

	@FXML
	public void forgotUsername(ActionEvent event) throws IOException {
		// open scene with email to send a new password -> new password password confirm
		System.out.println("guess what, you are an idiot");
		loginModel.switchScene(SceneNames.FORGOT_USERNAME);
	}

	@FXML
	public void register(ActionEvent event) throws IOException {
		loginModel.switchScene(SceneNames.REGISTER);
		System.out.println("going to register");
	}

	@FXML
	public void onBackButton() {
		loginModel.back();
	}
}
