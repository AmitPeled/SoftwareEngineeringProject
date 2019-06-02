package testApp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapViewer.MapViewerView;
import mapViewer.MapViewerViewModel;

public final class TestApplication extends Application{

	static final int SCREEN_WIDTH = 400;
	static final int SCREEN_HEIGHT = 400;
	
	private Stage primaryStage;
	private MapViewerView mapViewerView;
	
	public TestApplication() {
		mapViewerView = new MapViewerView(new MapViewerViewModel());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initiating the debug app
		Text helloWorldText = new Text("Hello World!");
		StackPane stackPane = new StackPane();
		primaryStage.setTitle("GCM Debug App");
		this.primaryStage = primaryStage;
		stackPane.getChildren().add(helloWorldText);
		
		// Adding a "Switch to Map Viewer" button
		Button switchToMapViewerButton = new Button();
		switchToMapViewerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) { switchToMapViewer(); }
		});
		switchToMapViewerButton.setText("Switch to Map-Viewer");
		stackPane.getChildren().add(switchToMapViewerButton);
		
		// Displaying the scene
		Scene scene = new Scene(stackPane,SCREEN_WIDTH,SCREEN_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void switchToMapViewer(){
		primaryStage.setScene(mapViewerView.getScene());
	}
	
}
