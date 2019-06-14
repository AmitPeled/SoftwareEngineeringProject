package editor.buySubscription;

import java.io.IOException;

import dataAccess.contentManager.ContentManagerDAO;
import editor.addCity.AddCityController;
import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.TextFieldUtility;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		 // constructing our scene
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/buySubscription.fxml"));
		 GcmDAO gcmDAO = new GcmDAO();
		 gcmDAO.login("editor", "editor"); 

		 TextFieldUtility utilities = new TextFieldUtility();
		 fxmlLoader.setController(new BuySubscriptionController(gcmDAO, 1));
		 Parent root = fxmlLoader.load();
		 Scene scene = new Scene( root );

		 // setting the stage
		 primaryStage.setScene( scene );
		 primaryStage.setTitle( "Search" );

		 primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}