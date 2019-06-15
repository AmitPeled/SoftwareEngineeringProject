package approvalReports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.tourApprovalReports.TourSubmission;
import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import maps.City;
import maps.Coordinates;
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
		 GcmDAO gcmDAO = new GcmDAO();
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
	     tourSitesLists.add(new Site(1, "hamburder", "tasty", "food", true, new Coordinates(1,1)));
	     List<Integer> tourSitesTimeLists = new ArrayList<Integer>();
	     tourSitesTimeLists.add(50);
	     tourLists.add(new TourSubmission(new Tour(1, "israel tour", tourSitesLists, tourSitesTimeLists), ActionTaken.ADD));
	     tourLists.add(new TourSubmission(new Tour(2, "paris tour", tourSitesLists, tourSitesTimeLists), ActionTaken.DELETE));

	     fxmlLoader.setController(new ApprovalReportsController(gcmDAO, cityLists, siteLists, tourLists));
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