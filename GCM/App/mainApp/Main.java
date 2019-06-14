package mainApp;

import dataAccess.customer.CustomerDAO;
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
		GcmDAO gcmDAO = new GcmDAO();
		UserDAO userDAO = gcmDAO;
		CustomerDAO customerDAO = gcmDAO;
		UserInfo userInfo = new UserInfoImpl(userDAO,customerDAO);
		GcmClient gcm = new GcmClientImpl(primaryStage,userInfo);
		ScenesInitializer initializer = new ScenesInitializerImpl(gcm);
		SceneManager manager = new SceneManagerImpl(initializer.getScenes());
		gcm.setSceneManager(manager);
		gcm.switchScene(SceneNames.PURCHASE);
	}

	public static void main(String[] args) {
		launch(args);
	}
}