package login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//import javafx.scene.control.TextField;
import userAccess.UserDAO;
import users.User;
import login.LoginModel;

public class LoginSceneController{

	@FXML
	private TextField usernametxt;

	@FXML
	private TextField passwordtxt;
	
	@FXML
	public void LogIn(ActionEvent event) throws IOException {
		//send to data base to confirm that he is in the system
		System.out.println("Logging in....");
		LoginModel loginModel = new LoginModel();
		if(!loginModel.login(usernametxt.getText(), passwordtxt.getText())) {
			System.out.println("Log in faild");
		}else {
			System.out.println("Log in success, go to app main scene");
			Stage primaryStage = new Stage();
			//need to make new fmxl file to go to the main scene
			//need to do the same for the rest of the actionevents -> dont forget to close current window!
			Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
		
	}
	
	@FXML
	public void forgatPassword(ActionEvent event) {
		//open scene with email to send a new password -> new password password confirm
		System.out.println("guess what, you ate an idiot");
	}
	
	@FXML
	public void rgister(ActionEvent event) {
		//go to register scene
		System.out.println("going to register");
		
	}


	
}
