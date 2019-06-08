package mainApp;

import java.util.Dictionary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GcmClientImpl implements GcmClient {
	/**
	 * Contains the mapping between scenes and their .fxml file path
	 */
	private Dictionary<Scenes, String> fxmlFilePath;
	
	/**
	 * A reference to the primary stage (used to switch scenes)
	 */
	private Stage primaryStage;
	
	public GcmClientImpl(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void switchScene(Scenes destinationScenes) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxmlFilePath.get(destinationScenes)));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
