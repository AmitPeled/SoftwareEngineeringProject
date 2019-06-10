package register;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import mainApp.GcmClient;

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
		
		gcmClient.switchScene(SceneNames.LOGIN);
	}
}
