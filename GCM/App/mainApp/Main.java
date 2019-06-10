package mainApp;

import init.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		GcmClient gcm = new GcmClientImpl(primaryStage);
		ScenesInitializer initializer = new ScenesInitializerImpl(gcm);
		SceneManager manager = new SceneManagerImpl(initializer.getScenes());
		gcm.setSceneManager(manager);
		gcm.switchScene(SceneNames.INTRO);
	}

	public static void main(String[] args) {
		launch(args);
	}
}