package userDetailsPresentation;

import java.net.URL;
import java.util.ResourceBundle;

import gcmDataAccess.GcmDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import mainApp.GcmClient;
import maps.Coordinates;
import users.User;

public class UserDetailsPresentationController implements Initializable {

	private User user;
	private GcmClient gcmClient;
	private GcmDAO gcmDAO;
	
	@FXML
	private Text nametxt, lastnametxt, emailtxt, phonenumbertxt, usernametxt;

	public UserDetailsPresentationController(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}
	
	public void setDeteails() {
		
		nametxt.setText(user.getFirstName());
		lastnametxt.setText(user.getLastName());
		emailtxt.setText(user.getEmail());
		phonenumbertxt.setText(user.getPhoneNumber());
		usernametxt.setText(user.getUsername());
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// setDeteails();
	}

	public void initalizeControl(User user) {
		this.user = user;
		setDeteails();
	}

}
