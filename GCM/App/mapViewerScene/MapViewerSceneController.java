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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import mainApp.GcmClient;
import mapViewer.MapViewer;
import mapViewer.MapViewerFactory;
import mapViewer.MapViewerListener;
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
	Button deleteTour;
	@FXML
	Button editTour;
	@FXML
	Button editMap;
	@FXML
	Button deleteMap;
	Alert alert;
	
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
			mapViewer.updateStatusText(map.getName());
			
			// Loading the FXML view
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(MAP_DISPLAY_SCENE_FXML));
			loader.setController(this);
			GridPane gridPane = loader.load();
			gridPane.add(mapViewer.getScene().getRoot(), MAP_VIEWER_GRID_COL_INDEX , MAP_VIEWER_GRID_ROW_INDEX);
			setVisibility();
			List<Tour> toursList = gcmClient.getDataAccessObject().getCityTours(cityId);
			List<Site> sitesList = gcmClient.getDataAccessObject().getCitySites(cityId);
			System.out.println("toursList entries: " + toursList.size());
			System.out.println("sitesList entries: " + sitesList.size());
			
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
	private void setVisibility() {
		RequestState userState = gcmClient.getUserInfo().getState();
		if(userState.equals(RequestState.editor) || userState.equals(RequestState.contentManager) || userState.equals(RequestState.generalManager) || userState.equals(RequestState.manager)) {
			addSite.setVisible(true);
			editSite.setVisible(true);
			deleteSite.setVisible(true);
			addTour.setVisible(true);
			editTour.setVisible(true);
			deleteTour.setVisible(true);
			editMap.setVisible(true);
			deleteMap.setVisible(true);
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
		gcmClient.switchSceneToAddSite(cityId, -1,
				widthLocation,
				heightLocation); 
	}
	
	@FXML
	public void onEditSite() { 
		Site site = sideMenuController.getSelectedSite();

		if(site == null) return;
		gcmClient.switchSceneToAddSite(cityId, site.getId(),
				site.getCoordinates().getX(),
				site.getCoordinates().getY()); 
	}

	@FXML
	public void onDeleteSite() {
		Site site = sideMenuController.getSelectedSite();

		if(site == null) return;

		showAlertBox("site", 0, site.getId());
	}
	
	@FXML
	public void onAddTour() {
		Tour tour = new Tour(null);
		if(tour == null) return;
		gcmClient.switchSceneToTour(cityId, mapId, tour);
	}
	@FXML
	public void onEditTour() {
		Tour tour = sideMenuController.getSelectedTour();
		if(tour == null) return;
		gcmClient.switchSceneToTour(cityId, mapId, tour);
	}
	@FXML
	public void onDeleteTour() {
		Tour tour = sideMenuController.getSelectedTour();

		if(tour == null) return;

		showAlertBox("tour", tour.getId(), 0);
	}
	
	@FXML
	public void onEditMap() {
		gcmClient.switchSceneToAddMap(cityId, mapId);
	}
	@FXML
	public void onDeleteMap() {
		showAlertBox("map", 0, 0);
	}
	public void showAlertBox(String type, int tourId, int siteId) {
		alert = new Alert(AlertType.CONFIRMATION, "delete " + type + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			switch(type) {
			case "map":
				gcmClient.getDataAccessObject().deleteMap(mapId);
				break;
			case "tour":
				gcmClient.getDataAccessObject().deleteTourFromMap(mapId, tourId);
				break;
			case "site":
				gcmClient.getDataAccessObject().deleteSiteFromCity(siteId);
				break;
			}
		}
		alert.close();
	}
	@FXML
	public void onBack() {gcmClient.back();}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addSite.setVisible(false);
		editSite.setVisible(false);
		deleteSite.setVisible(false);
		addTour.setVisible(false);
		editTour.setVisible(false);
		deleteTour.setVisible(false);
		editMap.setVisible(false);
		deleteMap.setVisible(false);
	}
}
