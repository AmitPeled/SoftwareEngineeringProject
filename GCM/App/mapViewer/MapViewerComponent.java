package mapViewer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Contains a component that presents a map and listens to mouse clicks on the map.
 * Use the getScene() method to get the Scene object of this component in order to use it.
 * It's possible to add listeners using the constructor or with the addListener() method that listen
 * to mouse click events.
 */
public final class MapViewerComponent implements MapViewer {
	private Set<MapViewerListener> listeners;
	private ImageView mapImage;
	private StackPane group;
	private Scene scene;
	private double width;
	private double height;
	private Text statusText;
	private final Canvas canvas;
	private final GraphicsContext graphicsContext;
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
		if(listeners == null) {
			listeners = new HashSet<MapViewerListener>();
		} else {
			this.listeners = listeners;			
		}
		
		Image image = new Image(mapPath);
		mapImage = new ImageView(image);
		mapImage.setPickOnBounds(true);
		mapImage.setPreserveRatio(true);
		//mapImage.setOnMouseClicked(e-> {OnMouseClick(e.getX(), e.getY());});
		width = image.getWidth();
		height = image.getHeight();
		
		canvas = new Canvas(width,height);
		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setFill(javafx.scene.paint.Color.RED);
		graphicsContext.fillOval(200, 300, 200, 20);
		graphicsContext.setGlobalBlendMode(BlendMode.SCREEN);
		graphicsContext.drawImage(image, 0, 0);
		canvas.setOnMouseClicked(e-> {OnMouseClick(e.getX(), e.getY());});
		
		System.out.println(width + "x" + height);

		// Placing empty status text holder in the top-middle of the map
		statusText = new Text();
		statusText.setTextOrigin(VPos.TOP);
		statusText.setTextAlignment(TextAlignment.CENTER);
		statusText.setFont(Font.font(null,FontWeight.BOLD,22));
		statusText.setX(Math.round(width/2));
		
		group = new StackPane();
		group.getChildren().add(canvas);
		group.getChildren().add(statusText);
		StackPane.setAlignment(statusText,Pos.CENTER);
		scene = new Scene(group, width, height);
	}

	@Override
	public double getImageWidth() { return width; }
	@Override
	public double getImageHeight() { return height; }
	@Override
	public Scene getScene() { return scene; }
	
	@Override
	public void addListener(MapViewerListener listener) {
		if(listener == null) return;
		if(listeners == null) {
			listeners = new HashSet<MapViewerListener>();
		}
		
		listeners.add(listener);
	}

	@Override
	public void updateStatusText(String text) {
		statusText.setText(text);
		//statusText.setTextOrigin(VPos.TOP);
		//statusText.setTextAlignment(TextAlignment.CENTER);
		//statusText.setFont(Font.font(null,FontWeight.BOLD,22));
		//statusText.setX(Math.round(width/2));
		StackPane.setAlignment(statusText,Pos.TOP_CENTER);
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
		
		// Notify all listeners that a mouse click occurred
		for (MapViewerListener mapViewerListener : listeners) {
			mapViewerListener.onMapClick(relativeX,relativeY);
		}
	}

	@Override
	public void clearStatusText() { statusText.setText(""); }
}
