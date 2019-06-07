package mapViewer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Contains a component that presents a map and listens to mouse clicks on the map.
 * Use the getScene() method to get the Scene object of this component in order to use it.
 * It's possible to add listeners using the constructor or with the addListener() method that listen
 * to mouse click events.
 */
public final class MapViewerComponent {
	private Set<MapViewerListener> listeners;
	private ImageView mapImage;
	private Pane pane;
	private Scene scene;
	private double width;
	private double height;
	
	/**
	 * Constructs a MapViewerComponent object with no listeners.
	 * @param mapPath
	 */
	public MapViewerComponent(String mapPath) { this(mapPath,(Set<MapViewerListener>)null); }
	
	/**
	 * Constructs a MapViewerComponent object with a single listener
	 * @param mapPath the path of the map file prefixed by "file:" and using relative path
	 * @param listener A listener object that will be invoked when a mouse click occurs on the map
	 */
	public MapViewerComponent(String mapPath, MapViewerListener listener) { this(mapPath,new HashSet<MapViewerListener>(Arrays.asList(listener))); }

	/**
	 * Constructs a MapViewerComponent object with a several listeners
	 * @param mapPath the path of the map file prefixed by "file:" and using relative path
	 * @param A collection of listener objects that will be invoked when a mouse click occurs on the map
	 */
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
		if(listeners == null || listeners.isEmpty()) return;
		
		double relativeY = y/height;
		double relativeX = x/width;
		
		System.out.println("Mouse click registered in ["+x+","+y+"]");
		for (MapViewerListener mapViewerListener : listeners) {
			mapViewerListener.onMapClick(relativeX,relativeY);
		}
	}
}
