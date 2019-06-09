package mainApp;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		GcmClient gcm = new GcmClientImpl(primaryStage);
		gcm.switchScene(SceneNames.INTRO);
	}

	public static void main(String[] args) {
		launch(args);
	}
}