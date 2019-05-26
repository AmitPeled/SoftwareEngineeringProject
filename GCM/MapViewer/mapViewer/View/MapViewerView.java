package mapViewer.View;

import java.lang.reflect.Constructor;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import mapViewer.ViewModel.*;

/**
 * Contains the view elements of the MapViewer.
 *
 */
public final class MapViewerView {
	
	static final int WIDTH = 400;
	static final int HEIGHT = 400;
	
	private MapViewerViewModel viewModel;
	private String mapPathString;
	private Image mapImage;
	
	public MapViewerView(MapViewerViewModel viewModel) {
		this.viewModel = viewModel;
		this.mapPathString = viewModel.getMapPath();
		this.mapImage = new Image(mapPathString);
	}
	
	public Scene getScene() {
		GridPane gridPane = GetPane();
		Scene scene = new Scene(gridPane, WIDTH, HEIGHT);
		
		return scene;
	}
	
	GridPane GetPane() {
		GridPane gridPane = new GridPane();
		
		BackgroundImage backgroundImage = new BackgroundImage(
				mapImage, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, 
				BackgroundSize.DEFAULT);
		
		Background background = new Background(backgroundImage);
		gridPane.setBackground(background);
		
		return gridPane;
	}
}