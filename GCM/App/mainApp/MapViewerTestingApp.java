package mainApp;

import dataAccess.customer.CustomerDAO;
import dataAccess.mapDownload.MapDownload;
import dataAccess.users.UserDAO;
import gcmDataAccess.GcmDAO;
import init.*;
import javafx.application.Application;
import javafx.stage.Stage;
import serverMetaData.ServerMetaDataHandler;
import userInfo.UserInfo;
import userInfo.UserInfoImpl;

public class MapViewerTestingApp extends Application {
	private static final boolean ReceiveServerDetailsFromConsole = false;
	private static final String USERNAME = "c-manager";
	private static final String PASSWORD = "c-manager";
	private static final int MAP_ID = 351; // Map G
	
	static GcmDAO gcmDAO;
	@Override
	public void start(Stage primaryStage) {
		UserDAO userDAO = gcmDAO;
		CustomerDAO customerDAO = gcmDAO;
		UserInfo userInfo = new UserInfoImpl(userDAO,customerDAO);
		MapDownload mapDownload = new MapDownload(gcmDAO);
		GcmClient gcm = new GcmClientImpl(primaryStage, userInfo, gcmDAO, mapDownload);
		ScenesInitializer initializer = new ScenesInitializerImpl(gcm);
		SceneManager manager = new SceneManagerImpl(initializer.getScenes(),initializer.getControllers());
		gcm.setSceneManager(manager);
		gcm.getUserInfo().login(USERNAME, PASSWORD);
		gcm.switchScene(SceneNames.INTRO);
		gcm.loadMapDisplay(MAP_ID);
	}

	public static void main(String[] args) {
		if(ReceiveServerDetailsFromConsole)
			ServerMetaDataHandler.receiveFromConsole(args);
		gcmDAO = new GcmDAO(ServerMetaDataHandler.getServerHostName(), ServerMetaDataHandler.getServerPortNumer());
		launch(args);
	}
}