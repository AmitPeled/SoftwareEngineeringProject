package mapViewer;

import java.util.HashSet;
import java.util.Set;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import maps.Coordinates;
import maps.Site;

/**
 * Contains a component that presents a map and listens to mouse clicks on the map.
 * Use the getScene() method to get the Scene object of this component in order to use it.
 * It's possible to add listeners using the constructor or with the addListener() method that listen
 * to mouse click events.
 */
final class MapViewerComponent implements MapViewer {
	// Constants
	private static final int FIT_HEIGHT = 768;
	private static final int CIRCLE_SIZE = 15;
	private static final int FONT_SIZE = 20;
	private static final double STATUS_TEXT_WIDTH_POSITION = 0.5;
	private static final double STATUS_TEXT_HEIGHT_POSITION = 0.05;
	private static final float MOUSE_CLICK_OFFSET = 7.5f;
	private static final double SITE_TEXT_VERTICAL_OFFSET = 10;
	
	// JavaFX
	private Image image;
	private ImageView mapImage;
	private StackPane group;
	private Scene scene;
	private final Canvas canvas;
	private final GraphicsContext graphicsContext;

	// Map rendering information
	private double width;
	private double height;
	private String statusText;
	private Set<Site> sites;
	
	// Mouse click events
	private Set<MapViewerListener> listeners;
	private boolean locationIsSelected;
	private Coordinates selectedLocationPosition;

	// Map information
	private double realWorldWidth;
	private double realWorldHeight;
	
	/**
	 * Constructs a MapViewerComponent object with no listeners.
	 * @param mapPath
	 */
	// MapViewerComponent(String mapPath) { this(mapPath,(Set<MapViewerListener>)null,null); }
	
	/**
	 * Constructs a MapViewerComponent object with a single listener
	 * @param mapPath the path of the map file prefixed by "file:" and using relative path
	 * @param listener A listener object that will be invoked when a mouse click occurs on the map
	 */
	// MapViewerComponent(String mapPath, MapViewerListener listener) { this(mapPath,new HashSet<MapViewerListener>(Arrays.asList(listener)),null); }

	/**
	 * Constructs a MapViewerComponent object with a several listeners
	 * @param mapPath the path of the map file prefixed by "file:" and using relative path
	 * @param A collection of listener objects that will be invoked when a mouse click occurs on the map
	 * @param A collection of all the Sites in this map
	 */
	MapViewerComponent(String mapPath, 
			Set<MapViewerListener> listeners, 
			Set<Site> sites,
			double worldWidth,
			double worldHeight) {
		realWorldWidth = worldWidth;
		realWorldHeight = worldHeight;
		if(listeners == null) {
			listeners = new HashSet<MapViewerListener>();
		} else {
			this.listeners = listeners;			
		}
		if(sites == null) {
			sites = new HashSet<Site>();
		} else {
			this.sites = sites;
		}
		locationIsSelected = false;
		selectedLocationPosition = new Coordinates();
		
		image = new Image(mapPath);
		mapImage = new ImageView(image);
		mapImage.setPickOnBounds(true);
		mapImage.setFitHeight(FIT_HEIGHT);
		mapImage.setPreserveRatio(true);
		width = image.getWidth();
		height = image.getHeight();
		
		canvas = new Canvas(width,height);
		canvas.setOnMouseClicked(e-> {OnMouseClick(e.getX(), e.getY());});
		graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		graphicsContext.setTextBaseline(VPos.TOP);
		graphicsContext.setFont(Font.font(null,FontWeight.BOLD,FONT_SIZE));
		render();
		group = new StackPane();
		group.getChildren().add(canvas);
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
	public void updateStatusText(String text) { statusText = text; }
	
	public Coordinates getSelectedLocationPosition() {
		return new Coordinates(selectedLocationPosition);
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
		
		locationIsSelected = true;
		selectedLocationPosition = new Coordinates(x,y);
		render();
	}

	@Override
	public void clearStatusText() { statusText = ""; }
	
	/**
	 * Clears the canvas and redraws everything on the map
	 */
	private void render() {
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.drawImage(image, 0, 0);
		drawAllSites();
		drawMouseClick();

		graphicsContext.fillText(
				statusText, 
				Math.round(width* STATUS_TEXT_WIDTH_POSITION), 
				Math.round(height * STATUS_TEXT_HEIGHT_POSITION)
			);
	}

	private void drawMouseClick() {
		if(!locationIsSelected) return;
		
		graphicsContext.setFill(javafx.scene.paint.Color.RED);
		graphicsContext.fillOval(selectedLocationPosition.x - MOUSE_CLICK_OFFSET, 
				selectedLocationPosition.y- MOUSE_CLICK_OFFSET, 
				CIRCLE_SIZE, 
				CIRCLE_SIZE);
	}

	private void drawAllSites() {
		for (Site site : sites) {
			if(site.getCoordinates().x > realWorldWidth || site.getCoordinates().y > realWorldHeight) {
				System.out.println("site \"" + site.getName() + "\" is out of bounds");
				continue;
			}
			graphicsContext.setFill(javafx.scene.paint.Color.YELLOW);
			double widthLocation = site.getCoordinates().x * width / realWorldWidth;
			double heightLocation = site.getCoordinates().y * height /realWorldHeight;
			graphicsContext.fillOval(widthLocation, heightLocation, CIRCLE_SIZE, CIRCLE_SIZE);
			System.out.println("drawing site in [" + widthLocation + "," + heightLocation + "]");
			
			graphicsContext.fillText(
					site.getName(), 
					Math.round(widthLocation), 
					Math.round(heightLocation + SITE_TEXT_VERTICAL_OFFSET)
				);
		}
	}

	@Override
	public Coordinates getMapClickCoordinates() {
		return selectedLocationPosition;
	}

	@Override
	public double getImageWorldWidth() { return realWorldWidth; }

	@Override
	public double getImageWorldHeight() { return realWorldHeight; }
}
