/**
 * 
 */
package mapViewerScene;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import mainApp.GcmClient;
import mainApp.SceneNames;
import mapViewer.MapViewer;
import mapViewer.MapViewerFactory;
import mapViewer.MapViewerListener;

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
	static final String FxmlPath = "/fxml/mapViewerClient/MapDisplayScene.fxml";	
	
	private Scene scene;
	private MapViewerListener listener;
	private GcmClient gcmClient;
	
	private MapViewerSceneController(GcmClient gcmClient, MapViewer mapViewer) {
		this.gcmClient = gcmClient;
		try {
			// Adding the listener
			listener = new SampleMapViewerListener(mapViewer);
			mapViewer.addListener(listener);
			
			// Loading the FXML view
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(FxmlPath));
			GridPane gridPane = loader.load();
			gridPane.add(mapViewer.getScene().getRoot(), MAP_VIEWER_GRID_COL_INDEX , MAP_VIEWER_GRID_ROW_INDEX);
			
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
	public static Scene getMapViewerScene(GcmClient gcmClient, int mapId) {
		MapViewer mapViewerComponent = MapViewerFactory.getMapViewer(mapId);
		MapViewerSceneController mapViewerSceneController = new MapViewerSceneController(gcmClient, mapViewerComponent);
		return mapViewerSceneController.getScene();
	}
	
	@FXML
	public void onAddCity() { gcmClient.switchScene(SceneNames.ADD_CITY); }
}
