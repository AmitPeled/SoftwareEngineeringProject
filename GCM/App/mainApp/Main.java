package mainApp;

import dataAccess.users.UserDAO;
import gcmDataAccess.GcmDAO;
import init.*;
import javafx.application.Application;
import javafx.stage.Stage;
import userInfo.UserInfo;
import userInfo.UserInfoImpl;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		UserDAO userDAO = new GcmDAO();
		UserInfo userInfo = new UserInfoImpl(userDAO);
		GcmClient gcm = new GcmClientImpl(primaryStage,userInfo);
		ScenesInitializer initializer = new ScenesInitializerImpl(gcm);
		SceneManager manager = new SceneManagerImpl(initializer.getScenes());
		gcm.setSceneManager(manager);
		gcm.switchScene(SceneNames.INTRO);
	}

	public static void main(String[] args) {
		launch(args);
	}
}