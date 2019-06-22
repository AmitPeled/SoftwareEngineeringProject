package mainApp;

import dataAccess.customer.CustomerDAO;
import dataAccess.mapDownload.MapDownloader;
import dataAccess.users.UserDAO;
import gcmDataAccess.GcmDAO;
import init.*;
import javafx.application.Application;
import javafx.stage.Stage;
import serverMetaData.ServerMetaDataHandler;
import userInfo.UserInfo;
import userInfo.UserInfoImpl;

public class Main extends Application {
	static final boolean ReceiveServerDetailsFromConsole = false;
	static GcmDAO gcmDAO;
	@Override
	public void start(Stage primaryStage) {
		UserDAO userDAO = gcmDAO;
		CustomerDAO customerDAO = gcmDAO;
		UserInfo userInfo = new UserInfoImpl(userDAO,customerDAO);
		MapDownloader mapDownloader = new MapDownloader(gcmDAO);
		GcmClient gcm = new GcmClientImpl(primaryStage, userInfo, gcmDAO, mapDownloader);
		ScenesInitializer initializer = new ScenesInitializerImpl(gcm);
		SceneManager manager = new SceneManagerImpl(initializer.getScenes(),initializer.getControllers());
		gcm.setSceneManager(manager);
		gcm.switchScene(SceneNames.INTRO);
	}

	public static void main(String[] args) {
		if(ReceiveServerDetailsFromConsole)
			ServerMetaDataHandler.receiveFromConsole(args);
		gcmDAO = new GcmDAO(ServerMetaDataHandler.getServerHostName(), ServerMetaDataHandler.getServerPortNumer());
		launch(args);
	}
}