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
		
		// going back to log in screen
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//Parent LoginSceneParent = FXMLLoader.load(getClass().getResource("login/LoginScene.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
		Parent LoginSceneParent = loader.load();
		Scene LoginSceneScene = new Scene(LoginSceneParent);
		LoginSceneScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(LoginSceneScene);
		window.show();
	}
	
	@FXML
	public void register(ActionEvent event) throws IOException {
		
		System.out.println("Register!!!");
		
		//Add validation for all fields
		
		//all this happen if the validation is ok
		// going back to log in screen
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//Parent LoginSceneParent = FXMLLoader.load(getClass().getResource("login/LoginScene.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
		Parent LoginSceneParent = loader.load();
		Scene LoginSceneScene = new Scene(LoginSceneParent);
		LoginSceneScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(LoginSceneScene);
		window.show();
	}
	
}
