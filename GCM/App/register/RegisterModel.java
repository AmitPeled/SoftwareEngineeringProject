package register;

import javafx.scene.control.Alert;
import javafx.stage.Window;
import mainApp.GcmClient;
import mainApp.SceneNames;
import users.User;

public class RegisterModel {

	private GcmClient gcmClient;
	private final int MAX_USERNAME_LENGTH = 10;
	private final int MIN_USERNAME_LENGTH = 4;
	private final int MAX_PASSWORD_LENGTH = 12;
	private final int MIN_PASSWOR_LENGTH = 6;
	private final int MAX_NAMEORLASTNAME_LENGTH = 10;
	private final int MIN_NAMEORLASTNAME_LENGTH = 3;
	private final int PHONE_NUBER_LENGTH = 10;

	public RegisterModel(GcmClient gcmClient) {
		if (gcmClient == null)
			throw new IllegalArgumentException("GCM client is null");
		this.gcmClient = gcmClient;
	}

	public boolean Validusername(String username) {
		if (username.length() > MAX_USERNAME_LENGTH || username.length() < MIN_USERNAME_LENGTH) {
			return false;
		}

		return true;
	}

	public boolean validpassword(String password) {
		if (password.length() > MAX_PASSWORD_LENGTH || password.length() < MIN_PASSWOR_LENGTH) {
			return false;
		}
		return true;
	}

	public boolean validConfirmPassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return false;
		}
		return true;
	}

	public boolean validName(String name) {
		if (name.length() > MAX_NAMEORLASTNAME_LENGTH || name.length() < MIN_NAMEORLASTNAME_LENGTH) {
			return false;
		}
		return true;
	}

	public boolean validlastName(String lastname) {
		if (lastname.length() > MAX_NAMEORLASTNAME_LENGTH || lastname.length() < MIN_NAMEORLASTNAME_LENGTH) {
			return false;
		}
		return true;
	}

	public boolean validPhoneNumber(String phoneNumber) {
		if (phoneNumber.length() == PHONE_NUBER_LENGTH) {
			return true;
		}
		return false;
	}

	public boolean register(String username, String password, User user) {

		if (!gcmClient.getUserInfo().register(username, password, user)) {
			return false;
		}

		return true;
	}

	public void switchScene(SceneNames scene) {
		gcmClient.switchScene(scene);
	}

	public void back() {
		gcmClient.back();
	}

}
