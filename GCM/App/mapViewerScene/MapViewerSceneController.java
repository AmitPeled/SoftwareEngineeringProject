/**
 * 
 */
package mapViewerScene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gcmDataAccess.GcmDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import mainApp.GcmClient;
import mainApp.SceneNames;
import mapViewer.MapViewer;
import mapViewer.MapViewerFactory;
import mapViewer.MapViewerListener;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import maps.Tour;
import queries.RequestState;

/**
* This class is an example class that demonstrates how to implement the MapViewerComponent class.
 * In this example screen, we have a grid with 2 columns, one for radio buttons and the other for
 * holding the map.
 *
 */
public class MapViewerSceneController implements Initializable{
	// Constants
	static final double LEFT_PANEL_WIDTH = 320.0;
	static final double BOTTOM_PANEL_HEIGHT = 80.0;
	static final int EDITOR_GRID_ROW_INDEX = 0;
	static final int MAP_VIEWER_GRID_COL_INDEX = 1;
	static final int MAP_VIEWER_GRID_ROW_INDEX = 0;
	private static final int SIDE_MENU_GRID_COL_INDEX = 0;
	private static final int SIDE_MENU_GRID_ROW_INDEX = 0;	
	static final String MAP_DISPLAY_SCENE_FXML = "/fxml/mapViewerClient/MapDisplayScene.fxml";	
	static final String SIDE_MENU_FXML = "/fxml/mapViewerClient/MapViewerSideMenu.fxml";
	
	private Scene scene;
	private MapViewerListener listener;
	private GcmClient gcmClient;
	private MapViewer mapViewer;
	private int cityId;
	private MapViewerSideMenuController sideMenuController;
	private int mapId;
	private Map map;
	@FXML
	Button addSite;
	@FXML
	Button editSite;
	@FXML
	Button deleteSite;
	@FXML
	Button addTour;
	@FXML
	Button editPrice;
	
	
	private MapViewerSceneController(GcmClient gcmClient, MapViewer mapViewer, int cityId, int mapId, Map map) {
		this.gcmClient = gcmClient;
		this.mapViewer = mapViewer;
		this.cityId = cityId;
		this.mapId = mapId;
		this.map = map;
		
		
		
		try {
			// Adding the listener
			listener = new SampleMapViewerListener(mapViewer);
			mapViewer.addListener(listener);
			
			// Loading the FXML view
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(MAP_DISPLAY_SCENE_FXML));
			loader.setController(this);
			GridPane gridPane = loader.load();
			gridPane.add(mapViewer.getScene().getRoot(), MAP_VIEWER_GRID_COL_INDEX , MAP_VIEWER_GRID_ROW_INDEX);
			setVisibility(gcmClient);
			
			// Demo data
			List<Site> sitesInTour = new ArrayList<Site>();
			Site demoSiteA = new Site(1, "Demo site A", new Coordinates(200,300));
			Site demoSiteB = new Site(2, "Demo site B", new Coordinates(300,400));
			Site demoSiteC = new Site(3, "Demo site C", new Coordinates(5,5));
			sitesInTour.add(demoSiteA);
			sitesInTour.add(demoSiteB);
			sitesInTour.add(demoSiteC);
			List<Tour> toursList = fetchTours(sitesInTour);
			List<Site> sitesList = sitesInTour;
			
			sideMenuController = new MapViewerSideMenuController(sitesList, toursList);
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(SIDE_MENU_FXML));
			loader.setController(sideMenuController);
			gridPane.add(loader.load(), SIDE_MENU_GRID_COL_INDEX , SIDE_MENU_GRID_ROW_INDEX);
			// Creating the Scene object that is returned
			double width = mapViewer.getImageWidth() + LEFT_PANEL_WIDTH;
			double height = mapViewer.getImageHeight() + BOTTOM_PANEL_HEIGHT;
			
			scene = new Scene(gridPane, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void setVisibility(GcmClient gcmClient) {
		RequestState userState = gcmClient.getUserInfo().getState();
		if(userState.equals(RequestState.editor) || userState.equals(RequestState.contentManager) || userState.equals(RequestState.generalManager) || userState.equals(RequestState.manager)) {
			addSite.setVisible(true);
			editSite.setVisible(true);
			deleteSite.setVisible(true);
			addTour.setVisible(true);
			editPrice.setVisible(true);
		}
	}
	private List<Tour> fetchTours(List<Site> sitesInTour) {
		
		List<Tour> toursList = new ArrayList<Tour>();
		Tour demoTour = new Tour("SomeDemoTour");
		demoTour.setSites(sitesInTour);
		toursList.add(demoTour);
		return toursList;
	}
	
	public Scene getScene() { return scene; }
	
	/**
	 * Factory method to return the MapViewerScene
	 * @param mapId the mapId as it is in the database
	 * @return JavaFX Scene object
	 */
	public static Scene getMapViewerScene(GcmClient gcmClient, int mapId, int cityId) {
		Map map = gcmClient.getDataAccessObject().getMapDetails(mapId);
		MapViewer mapViewerComponent = MapViewerFactory.getMapViewer(gcmClient.getDataAccessObject(),mapId);
		MapViewerSceneController mapViewerSceneController = new MapViewerSceneController(gcmClient, mapViewerComponent, cityId, mapId, map);
		return mapViewerSceneController.getScene();
	}
	
	@FXML
	public void onAddSite() { 
		double widthLocation = mapViewer.getMapClickCoordinates().x * mapViewer.getImageWorldWidth();
		double heightLocation = mapViewer.getMapClickCoordinates().y * mapViewer.getImageWorldHeight();
		System.out.println("Adding site to "+ widthLocation + ","+heightLocation);
		gcmClient.switchSceneToAddSite(cityId,
				widthLocation,
				heightLocation); 
	}
	
	@FXML
	public void onEditSite() {

	}

	@FXML
	public void onDeleteSite() {
		Site site = sideMenuController.getSelectedSite();
		System.out.println("Deleting site "+ site.getName());	
		//gcmClient.getDataAccessObject().deleteSiteFromMap(mapId, site.getId());
	}
	
	@FXML
	public void onAddTour() {
		
	}
	
	@FXML
	public void onEditPrice() {
		gcmClient.switchSceneToEditPrice(cityId);
	}
	
	@FXML
	public void onBack() {gcmClient.back();}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addSite.setVisible(false);
		editSite.setVisible(false);
		deleteSite.setVisible(false);
		addTour.setVisible(false);
		editPrice.setVisible(false);
	}
}
