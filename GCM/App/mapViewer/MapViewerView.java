package mapViewer;

import java.io.Console;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Contains the view elements of the MapViewer.
 *
 */
public final class MapViewerView {
	
	static final int WIDTH = 1280;
	static final int HEIGHT = 720;
	
//	private MapViewerViewModel viewModel;
	private String mapPathString;
	private ImageView mapImage;
	private Pane pane;
	private Scene scene;
	
	public MapViewerView(MapViewerViewModel viewModel) {
		mapPathString = viewModel.getMapPath();
		Image image = new Image(mapPathString);
		mapImage = new ImageView(image);
		mapImage.setPickOnBounds(true);
		mapImage.setPreserveRatio(true);
		mapImage.setOnMouseClicked(e-> {OnMouseClick(e.getX(), e.getY());});
		double width = image.getWidth();
		double height = image.getHeight();
		
		System.out.println(width + "x" + height);
		
		pane = new Pane();
		pane.getChildren().add(mapImage);
		
		scene = new Scene(pane, width, height);
	}
	
	public Scene getScene() { return scene; }
	
	
	private void OnMouseClick(double x, double y) {
		System.out.println("Mouse click registered in ["+x+","+y+"]");
		ShowLabel(x, y);
	}
	
	private void ShowLabel(double x, double y){
		Text text = new Text("text on map");
		text.setX(x);
		text.setY(y);
		
		pane.getChildren().add(text);
	}
}
