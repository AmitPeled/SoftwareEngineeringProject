package register;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterSceneController {

	@FXML
	private TextField nametxt, lastnametxt,emailtxt, usernametxt, passwordtxt, confirmpasswordtxt, phonetxt;
	
	@FXML
	public void back(ActionEvent event) throws IOException {
		System.out.println("well, im going back");
		returnToLoginScreen(event);
	}
	
	@FXML
	public void register(ActionEvent event) throws IOException {
		System.out.println("Register!!!");
		//Add validation for all fields		
		
		returnToLoginScreen(event);
	}

	private void returnToLoginScreen(ActionEvent event) throws IOException {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/login/LoginScene.fxml"));
		Parent LoginSceneParent = loader.load();
		Scene LoginSceneScene = new Scene(LoginSceneParent);
		LoginSceneScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(LoginSceneScene);
		window.show();
	}
	
}
