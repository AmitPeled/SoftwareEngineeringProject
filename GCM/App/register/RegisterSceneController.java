package register;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import mainApp.SceneNames;
import users.User;

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
		clearFields();
		RegisterModel.back();
	}

	@FXML
	public void register(ActionEvent event) throws IOException {
		System.out.println("Register!!!");

		if (!(RegisterModel.validName(nametxt.getText()))) {
			showAlert(AlertType.ERROR, getStageWindow(event), "Form Error!", "name need to be between 3 to 10 letters");
			nametxt.clear();
			// RegisterModel.clear(nametxt);

		} else if (!(RegisterModel.validlastName(lastnametxt.getText()))) {
			showAlert(AlertType.ERROR, getStageWindow(event), "Form Error!",
					"lastname need to be between 3 to 10 letters");
			// RegisterModel.clear(lastnametxt);
			lastnametxt.clear();

		} else if (!(RegisterModel.Validusername(usernametxt.getText()))) {
			showAlert(AlertType.ERROR, getStageWindow(event), "Form Error!",
					"username length need to be between 4 to 10 characters");
			usernametxt.clear();

		} else if (!(RegisterModel.validpassword(passwordtxt.getText()))) {
			showAlert(AlertType.ERROR, getStageWindow(event), "Form Error!",
					"password length need to be between 6 to 10 characters");
			passwordtxt.clear();

		} else if (!(RegisterModel.validConfirmPassword(passwordtxt.getText(), confirmpasswordtxt.getText()))) {
			showAlert(AlertType.ERROR, getStageWindow(event), "Form Error!",
					"password and confirm password dont match");
			confirmpasswordtxt.clear();

		} else if (!(RegisterModel.validPhoneNumber(phonetxt.getText()))) {
			showAlert(AlertType.ERROR, getStageWindow(event), "Form Error!", "phone length need to be 10");
			phonetxt.clear();

		} else {
			// register.register(username, password, user);
			User user = new User(nametxt.getText(), lastnametxt.getText(), emailtxt.getText(), phonetxt.getText());

			if (!RegisterModel.register(usernametxt.getText(), passwordtxt.getText(), user)) {
					System.out.println("someting is up");
			}
			clearFields();
			RegisterModel.switchScene(SceneNames.LOGIN);
		}
	}

	public Window getStageWindow(ActionEvent event) {
		Node source = (Node) event.getSource();
		Window theStage = source.getScene().getWindow();
		return theStage;
	}

	public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	public void clearFields() {
	
		nametxt.clear();
		lastnametxt.clear();
		emailtxt.clear();
		usernametxt.clear();
		passwordtxt.clear();
		confirmpasswordtxt.clear();
		phonetxt.clear();
	}
	
}
