package editor.tour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/tour.fxml"));
		 GcmDAO gcmDAO = new GcmDAO();
		 List<String> sites = new ArrayList<>();
		 sites.add("haifa");
		 sites.add("tel aviv");
		 List<String> timeEstimations = new ArrayList<>();
		 timeEstimations.add("1:30");
		 timeEstimations.add("2:00");
		 TourSites tourSites = new TourSites(sites, timeEstimations);
		 TextFieldUtility utilities = new TextFieldUtility();
		 fxmlLoader.setController(new TourController(gcmDAO, 1, tourSites, utilities));
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
