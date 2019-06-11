package register;

import javafx.scene.control.Alert;
import javafx.stage.Window;
import mainApp.GcmClient;
import mainApp.SceneNames;

public class RegisterModel {

	private GcmClient gcmClient;

	public RegisterModel(GcmClient gcmClient) {
		if(gcmClient == null) throw new IllegalArgumentException("GCM client is null");
		this.gcmClient = gcmClient; 
	}

	public boolean Validusername(String username) {
		if (username.length() > 10 || username.length() < 4) {
			//showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
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
	
	public boolean validName(String name) {
		if(name.length()>10||name.length()<3) {
			return false;
		}
		return true;
	}
	
	public boolean validlastName(String lastname) {
		if(lastname.length()>10||lastname.length()<3) {
			return false;
		}
		return true;
	}
	
	public boolean validPhoneNumber(String phoneNumber) {
		if(phoneNumber.length()==10) {
			return true;
		}
		return false;
	}
	
	
	public void switchScene(SceneNames scene) { gcmClient.switchScene(scene); }

	public void back() { gcmClient.back(); }
	
	public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	

	
}
