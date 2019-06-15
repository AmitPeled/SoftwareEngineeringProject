package approvalReports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.tourApprovalReports.TourSubmission;
import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import maps.Tour;
import utility.TextFieldUtility;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		 // constructing our scene
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/approvalReports/ApprovalReportsScene.fxml"));
		 //GcmDAO gcmDAO = new GcmDAO();
		 //gcmDAO.login("editor", "editor"); 
		 TextFieldUtility utilities = new TextFieldUtility();
		 List<CitySubmission> cityLists = new ArrayList<CitySubmission>();
	     cityLists.add(new CitySubmission(new City(1, "haifa", "this city sucks"), ActionTaken.ADD));
	     cityLists.add(new CitySubmission(new City(2, "tel aviv", "well this city doesn't suck"), ActionTaken.DELETE));
	     List<SiteSubmission> siteLists = new ArrayList<SiteSubmission>();
	     siteLists.add(new SiteSubmission(new Site(1, "hamburder", "tasty", "food", true, new Coordinates(1,1)), ActionTaken.ADD));
	     siteLists.add(new SiteSubmission(new Site(2, "cinema", "nice movie", "fun", true, new Coordinates(1,1)), ActionTaken.DELETE));
	     List<TourSubmission> tourLists = new ArrayList<TourSubmission>();
	     List<Site> tourSitesLists = new ArrayList<Site>();
	     Site site1 = new Site(1, "hamburder", "tasty", "food", true, new Coordinates(1,1));
	     tourSitesLists.add(site1);
	     List<Integer> tourSitesTimeLists = new ArrayList<Integer>();
	     tourSitesTimeLists.add(50);
	     Tour tour1 = new Tour(1, "israel tour", tourSitesLists, tourSitesTimeLists);
	     tourLists.add(new TourSubmission(tour1, ActionTaken.ADD));
	     tourLists.add(new TourSubmission(new Tour(2, "paris tour", tourSitesLists, tourSitesTimeLists), ActionTaken.DELETE));
	     List<Site> sites = Arrays.asList(site1);
	     List<Tour> tours = Arrays.asList(tour1);
		 List<MapSubmission> mapLists = new ArrayList<MapSubmission>();
	     mapLists.add(new MapSubmission(new Map(1, "israel", "nice place", 123, 864, new Coordinates(1,1), 22, sites, tours), ActionTaken.ADD));
	     mapLists.add(new MapSubmission(new Map(2, "tel aviv", "nice city", 456, 567, new Coordinates(1,1), 30, sites, tours), ActionTaken.DELETE));
	     fxmlLoader.setController(new ApprovalReportsController(cityLists, siteLists, tourLists, mapLists));
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