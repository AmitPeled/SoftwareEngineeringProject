/**
 * 
 */
package mapViewerScene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gcmDataAccess.GcmDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import mainApp.GcmClient;
import mainApp.SceneNames;
import mapViewer.MapViewer;
import mapViewer.MapViewerFactory;
import mapViewer.MapViewerListener;
import maps.Coordinates;
import maps.Site;
import maps.Tour;

/**
* This class is an example class that demonstrates how to implement the MapViewerComponent class.
 * In this example screen, we have a grid with 2 columns, one for radio buttons and the other for
 * holding the map.
 *
 */
public class MapViewerSceneController {
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
	
	private MapViewerSceneController(GcmClient gcmClient, MapViewer mapViewer, int cityId, int mapId) {
		this.gcmClient = gcmClient;
		this.mapViewer = mapViewer;
		this.cityId = cityId;
		this.mapId = mapId;
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
			
			List<Tour> toursList = new ArrayList<Tour>();
			toursList.add(new Tour("SomeDemoTour"));
			List<Site> sitesList = new ArrayList<Site>();
			sitesList.add(new Site(cityId, "Demo site", new Coordinates(0,0)));
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
	
	public Scene getScene() { return scene; }
	
	/**
	 * Factory method to return the MapViewerScene
	 * @param mapId the mapId as it is in the database
	 * @return JavaFX Scene object
	 */
	public static Scene getMapViewerScene(GcmClient gcmClient, int mapId, int cityId) {
		MapViewer mapViewerComponent = MapViewerFactory.getMapViewer(gcmClient.getDataAccessObject(),mapId);
		MapViewerSceneController mapViewerSceneController = new MapViewerSceneController(gcmClient, mapViewerComponent, cityId, mapId);
		return mapViewerSceneController.getScene();
	}
	
	@FXML
	public void onAddSite() { 
		double widthLocation = mapViewer.getMapClickCoordinates().x * mapViewer.getImageWorldWidth();
		double heightLocation = mapViewer.getMapClickCoordinates().y* mapViewer.getImageWorldHeight();
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
}
