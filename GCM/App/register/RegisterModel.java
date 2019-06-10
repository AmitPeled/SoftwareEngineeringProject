package register;

import javafx.scene.control.TextField;

public class RegisterModel {

	public boolean Validusername(TextField usernametxt) {
		if (usernametxt.getText().length() > 10 || usernametxt.getText().length() < 4) {
			return false;
		}

		return true;
	}

	public boolean validpassword(String password) {
		if (password.length() > 10 || password.length() < 6) {
			return false;
		}
		return true;
	}

	public boolean validConfirmPassword(String password,String confirmPassword) {
		if(!password.equals(confirmPassword)) {
			return false;
		}
		return true;
	}
	
}
