package login;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mainApp.SceneNames;

public class LoginSceneController {

	private final LoginModel loginModel;

	@FXML
	private TextField usernametxt;

	@FXML
	private TextField passwordtxt;
	
	@FXML
	private Label wornglbl;

	public LoginSceneController(LoginModel model) {
		loginModel = model; 
	}
 
	@FXML
	public void LogIn(ActionEvent event) throws IOException {
		// send to data base to confirm that he is in the system
		System.out.println("Logging in....");
		if (!loginModel.login(usernametxt.getText(), passwordtxt.getText())) {
			System.out.println("Log in faild");
			wornglbl.setVisible(true);
		} else {
			System.out.println("Log in success, go to app main scene");
			wornglbl.setVisible(false);
			clearFields();
			loginModel.switchScene(SceneNames.MENU);
		}

	}

	@FXML
	public void forgotPassword(ActionEvent event) throws IOException {
		clearFields();
		loginModel.switchScene(SceneNames.FORGOT_PASSWORD);
	}

	@FXML
	public void forgotUsername(ActionEvent event) throws IOException {
		clearFields();
		loginModel.switchScene(SceneNames.FORGOT_USERNAME);
	}

	@FXML
	public void register(ActionEvent event) throws IOException {
		loginModel.switchScene(SceneNames.REGISTER);
		clearFields();
	}

	@FXML
	public void onBackButton() {
		loginModel.back();
	}
	
	public void clearFields() {
		usernametxt.clear();
		passwordtxt.clear();
		return;
		
	}
}
