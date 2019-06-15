package dataAccess.MapDownloader;

import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main  extends Application {
	GcmDAO gcmDAO = new GcmDAO();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MapDownloader downloader = new MapDownloader(gcmDAO, 1, 2);
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
