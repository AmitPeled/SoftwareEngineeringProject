package editor.editPrice;

import java.io.IOException;

import dataAccess.contentManager.ContentManagerDAO;
import editor.addCity.AddCityController;
import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		 // constructing our scene
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/editPrice.fxml"));
		 //ContentManagerDAO contentManagerDAO = new GcmDAO();
		 fxmlLoader.setController(new EditPriceController( 1));
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