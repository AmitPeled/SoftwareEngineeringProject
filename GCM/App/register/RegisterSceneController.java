package register;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import mainApp.SceneNames;
import users.User;
import utility.TextFieldUtility;

public class RegisterSceneController implements Initializable {

	private final int Max_length_phone = 10;
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
		System.out.println("Trying to register");

		if (!(RegisterModel.validName(nametxt.getText()))) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
					"name need to be between 3 to 10 letters");
			nametxt.clear();
			// RegisterModel.clear(nametxt);

		} else if (!(RegisterModel.validlastName(lastnametxt.getText()))) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
					"lastname need to be between 3 to 10 letters");
			lastnametxt.clear();

		} else if (!(RegisterModel.Validusername(usernametxt.getText()))) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
					"username length need to be between 4 to 10 characters");

			usernametxt.clear();

		} else if (!(RegisterModel.validpassword(passwordtxt.getText()))) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
					"password length need to be between 6 to 10 characters");

			passwordtxt.clear();

		} else if (!(RegisterModel.validConfirmPassword(passwordtxt.getText(), confirmpasswordtxt.getText()))) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
					"password and confirm password dont match");
			confirmpasswordtxt.clear();

		} else if (!(RegisterModel.validEmail(emailtxt.getText()))) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
					"invalid email address");
			emailtxt.clear();

		} else {
			// register.register(username, password, user);
			User user = new User(usernametxt.getText(), nametxt.getText(), lastnametxt.getText(), emailtxt.getText(),
					phonetxt.getText());

			if (!RegisterModel.register(usernametxt.getText(), passwordtxt.getText(), user)) {
				System.out.println("someting is up");
			}
			clearFields();
			System.out.println("Register!!!");
			RegisterModel.switchScene(SceneNames.LOGIN);
		}
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TextFieldUtility.numericTextOnly(phonetxt);
		TextFieldUtility.addTextLimiter(phonetxt, Max_length_phone);

	}

}
