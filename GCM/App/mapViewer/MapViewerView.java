package mapViewer;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

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
	
	public MapViewerView(MapViewerViewModel viewModel) {
		this.mapPathString = viewModel.getMapPath();
		this.mapImage = new ImageView(mapPathString);
		mapImage.setOnMouseClicked(e-> {OnMouseClick(e.getX(), e.getY());});
	}
	
	public Scene getScene() {
		StackPane stackPane = GetPane();
		Scene scene = new Scene(stackPane, WIDTH, HEIGHT);
		
		return scene;
	}
	
	private StackPane GetPane() {
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(mapImage);
		
		return stackPane;
	}
	
	private void OnMouseClick(double x, double y) {
		System.out.println("Mouse click registered in ["+x+","+y+"]");
	}
}
