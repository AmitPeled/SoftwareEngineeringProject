package register;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import mainApp.SceneNames;

public class RegisterSceneController {

	private RegisterModel RegisterModel;

	@FXML
	private TextField nametxt, lastnametxt, emailtxt, usernametxt, passwordtxt, confirmpasswordtxt, phonetxt;

	public RegisterSceneController(RegisterModel model) {
		this.RegisterModel = model;
	}

	@FXML
	public void back(ActionEvent event) throws IOException {
		System.out.println("well, im going back");
		RegisterModel.back();
	}

	@FXML
	public void register(ActionEvent event) throws IOException {
		System.out.println("Register!!!");

		if (!(RegisterModel.validName(nametxt.getText()))) {
			Node source = (Node) event.getSource();
			Window theStage = source.getScene().getWindow();
			RegisterModel.showAlert(AlertType.ERROR, theStage.getScene().getWindow(), "Form Error!",
					"name need to be between 3 to 10 letters");
			nametxt.clear();
			// RegisterModel.clear(nametxt);

		} else if (!(RegisterModel.validlastName(lastnametxt.getText()))) {
			Node source = (Node) event.getSource();
			Window theStage = source.getScene().getWindow();
			RegisterModel.showAlert(AlertType.ERROR, theStage.getScene().getWindow(), "Form Error!",
					"lastname need to be between 3 to 10 letters");
			// RegisterModel.clear(lastnametxt);
			lastnametxt.clear();

		} else if (!(RegisterModel.Validusername(usernametxt.getText()))) {
			Node source = (Node) event.getSource();
			Window theStage = source.getScene().getWindow();
			RegisterModel.showAlert(AlertType.ERROR, theStage.getScene().getWindow(), "Form Error!",
					"username length need to be between 4 to 10 characters");
			usernametxt.clear();

		} else if (!(RegisterModel.validpassword(passwordtxt.getText()))) {
			Node source = (Node) event.getSource();
			Window theStage = source.getScene().getWindow();
			RegisterModel.showAlert(AlertType.ERROR, theStage.getScene().getWindow(), "Form Error!",
					"password length need to be between 6 to 10 characters");
			passwordtxt.clear();

		} else if (!(RegisterModel.validConfirmPassword(passwordtxt.getText(), confirmpasswordtxt.getText()))) {
			Node source = (Node) event.getSource();
			Window theStage = source.getScene().getWindow();
			RegisterModel.showAlert(AlertType.ERROR, theStage.getScene().getWindow(), "Form Error!",
					"password and confirm password dont match");
			confirmpasswordtxt.clear();

		} else if (!(RegisterModel.validPhoneNumber(phonetxt.getText()))) {
			Node source = (Node) event.getSource();
			Window theStage = source.getScene().getWindow();
			RegisterModel.showAlert(AlertType.ERROR, theStage.getScene().getWindow(), "Form Error!",
					"phone length need to be 10");
			passwordtxt.clear();

		} else {
			// register.register(username, password, user);
			RegisterModel.switchScene(SceneNames.LOGIN);
		}
	}
}
