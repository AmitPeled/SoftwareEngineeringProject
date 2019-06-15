package editor.editPrice;

import java.io.IOException;

import dataAccess.contentManager.ContentManagerDAO;
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
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/editPrice.fxml"));
		 GcmDAO gcmDAO = new GcmDAO();
		 gcmDAO.login("editor", "editor");
		 ContentManagerDAO contentManagerDAO = gcmDAO;
		 TextFieldUtility utilities = new TextFieldUtility();
		 

		 fxmlLoader.setController(new EditPriceController(contentManagerDAO, 1, utilities));
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