package editor.tour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maps.Coordinates;
import maps.Site;
import maps.Tour;
import utility.TextFieldUtility;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		 // constructing our scene
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/tour.fxml"));
		 GcmDAO gcmDAO = new GcmDAO();
		 gcmDAO.login("editor", "editor");
		 
		 
		 int id = 1;
		 String description = "";
		 Site site1 = new Site(1, "cafe", "a good cafe", "food", true, new Coordinates(1,2));
		 Site site2 = new Site(2, "hamburger", "a good hamburger", "food", true, new Coordinates(1,2));
		 Site site3 = new Site(3, "cinema", "a good cinema", "fun", true, new Coordinates(1,2));
		 List<Site> tourSites = new ArrayList<Site>();
		 tourSites.add(site1);
		 tourSites.add(site2);
		 tourSites.add(site3);
		 List<Integer> sitesTimeToVisit = new ArrayList<Integer>();
		 sitesTimeToVisit.add(1);
		 sitesTimeToVisit.add(2);
		 sitesTimeToVisit.add(3);
		 Tour tour = new Tour("");
		 //Tour tour = new Tour(id , description, tourSites, sitesTimeToVisit);
		 TextFieldUtility utilities = new TextFieldUtility();
		 fxmlLoader.setController(new TourController(gcmDAO, 1,1, tour, utilities));
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
