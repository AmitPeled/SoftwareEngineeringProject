package mapViewer;

import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Contains the view elements of the MapViewer.
 *
 */
public final class MapViewerComponent {
	private Set<MapViewerListener> listeners;
	private ImageView mapImage;
	private Pane pane;
	private Scene scene;
	private double width;
	private double height;
	
	public MapViewerComponent(String mapPath) { this(mapPath,null); }
	public MapViewerComponent(String mapPath, Set<MapViewerListener> listeners) {
		this.listeners = listeners;
		Image image = new Image(mapPath);
		mapImage = new ImageView(image);
		mapImage.setPickOnBounds(true);
		mapImage.setPreserveRatio(true);
		mapImage.setOnMouseClicked(e-> {OnMouseClick(e.getX(), e.getY());});
		width = image.getWidth();
		height = image.getHeight();
		
		System.out.println(width + "x" + height);
		
		pane = new Pane();
		pane.getChildren().add(mapImage);
		
		scene = new Scene(pane, width, height);
	}
	
	public Scene getScene() { return scene; }
	
	public void addListener(MapViewerListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Called when a mouse click occurs on the map. Translates the coordinates to relative (from 0 to 1) and calls 
	 * the onMapClick function on every listener object.
	 * @param x absolute x coordinates of the mouse click on the map
	 * @param y absolute y coordinates of the mouse click on the map
	 */
	private void OnMouseClick(double x, double y) {
		double relativeY = y/height;
		double relativeX = x/width;
		
		System.out.println("Mouse click registered in ["+x+","+y+"]");
		for (MapViewerListener mapViewerListener : listeners) {
			mapViewerListener.onMapClick(relativeX,relativeY);
		}
	}
}
