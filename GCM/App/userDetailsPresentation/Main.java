package userDetailsPresentation;

import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String [] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/UserDetailsPresentationScene.fxml"));
		 //GcmDAO gcmDAO = new GcmDAO();
		// gcmDAO.login("editor", "editor");
		 
		 Parent root = fxmlLoader.load();
		 Scene scene = new Scene( root );

		 // setting the stage
		 primaryStage.setScene( scene );

		 primaryStage.show();
	}

}
