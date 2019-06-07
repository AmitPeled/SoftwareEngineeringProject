/**
 * 
 */
package mapViewerClientExample;

import java.io.Console;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import mapViewer.MapViewerComponent;

/**
 * 
 * @author aagami
 *
 */
public class MapViewerClientExample {
	static final double leftPanelWidth = 80.0;
	static final int mapViewerGridIndex = 1;
	static final String FxmlPath = "/fxml/mapViewerClient/MapViewerClientExampleGridPane.fxml";
	private Scene scene;
	
	
	public MapViewerClientExample(MapViewerComponent mapViewer) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(FxmlPath));
			GridPane gridPane = loader.load();
			gridPane.add(mapViewer.getScene().getRoot(), mapViewerGridIndex , 0);
			
			double width = mapViewer.getImageWidth() + leftPanelWidth;
			double height = mapViewer.getImageHeight();
			scene = new Scene(gridPane, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Scene getScene() { return scene; }
}
