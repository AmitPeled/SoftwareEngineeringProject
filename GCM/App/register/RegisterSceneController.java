package register;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mainApp.Controller;
import mainApp.GcmClient;
import mainApp.SceneNames;

public class RegisterSceneController implements Controller {

	private GcmClient gcmClient;
	
	@FXML
	private TextField nametxt, lastnametxt,emailtxt, usernametxt, passwordtxt, confirmpasswordtxt, phonetxt;
	
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

/*	private void returnToLoginScreen(ActionEvent event) throws IOException {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/login/LoginScene.fxml"));
		Parent LoginSceneParent = loader.load();
		Scene LoginSceneScene = new Scene(LoginSceneParent);
		LoginSceneScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(LoginSceneScene);
		window.show();
	}*/

	@Override
	public void setClient(GcmClient gcmClient) {
		this.gcmClient = gcmClient; 
	}
	
}
