package editor.pointOfInterest;

import java.io.IOException;

import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maps.Coordinates;
import utility.TextFieldUtility;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		 // constructing our scene
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/pointOfInterest.fxml"));
		 GcmDAO gcmDAO = new GcmDAO();  
		 gcmDAO.login("editor", "editor");
		 TextFieldUtility utilities = new TextFieldUtility();
		 Coordinates coordinates = new Coordinates(1,1);
		 fxmlLoader.setController(new PointOfInterestController(gcmDAO, 18, coordinates,utilities));
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