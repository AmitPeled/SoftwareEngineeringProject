package register;

import java.io.IOException;

import dataAccess.users.UserDAO;
import gcmDataAccess.GcmDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import mainApp.GcmClient;
import mainApp.SceneNames;

public class RegisterSceneController {

	private GcmClient gcmClient;
	
	@FXML
	private TextField nametxt, lastnametxt,emailtxt, usernametxt, passwordtxt, confirmpasswordtxt, phonetxt;
	
	public RegisterSceneController(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}

	@FXML
	public void back(ActionEvent event) throws IOException {
		System.out.println("well, im going back");
		gcmClient.back();
	}
	
	@FXML
	public void register(ActionEvent event) throws IOException {
		System.out.println("Register!!!");
		//Add validation for all fields		
		//UserDAO register = new GcmDAO();
		//register.register(username, password, user);
		gcmClient.switchScene(SceneNames.LOGIN);
	}
}
