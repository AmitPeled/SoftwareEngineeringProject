/**
 * 
 */
package mapViewerClientExample;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import mapViewer.MapViewer;
import mapViewer.MapViewerListener;

/**
* This class is an example class that demonstrates how to implement the MapViewerComponent class.
 * In this example screen, we have a grid with 2 columns, one for radio buttons and the other for
 * holding the map.
 *
 */
public class MapViewerClientExample {
	static final double leftPanelWidth = 80.0;
	static final int mapViewerGridIndex = 1;
	static final String FxmlPath = "/fxml/mapViewerClient/MapViewerClientExampleGridPane.fxml";
	private Scene scene;
	private MapViewerListener listener;
	
	public MapViewerClientExample(MapViewer mapViewer) {
		try {
			// Adding the listener
			listener = new SampleMapViewerListener(mapViewer);
			mapViewer.addListener(listener);
			
			// Loading the FXML view
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(FxmlPath));
			GridPane gridPane = loader.load();
			gridPane.add(mapViewer.getScene().getRoot(), mapViewerGridIndex , 0);
			
			// Creating the Scene object that is returned
			double width = mapViewer.getImageWidth() + leftPanelWidth;
			double height = mapViewer.getImageHeight();
			
			scene = new Scene(gridPane, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Scene getScene() { return scene; }
}
