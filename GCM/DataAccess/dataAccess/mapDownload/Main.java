package dataAccess.mapDownload;

import java.io.File;

import gcmDataAccess.GcmDAO;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main  extends Application {
	GcmDAO gcmDAO = new GcmDAO();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MapDownload downloader = new MapDownload();
		String extension = downloader.checkIfFileExist(1,2);
		if(!extension.equals("no")) {
			File newFile = downloader.getFileFromDirectory(5, 4, extension);
			System.out.println(newFile);
		}else {
			System.out.println("failed reading file!");
		}
		downloader.DownloadMap(gcmDAO, 3, 4);

	}
	public static void main(String[] args) {
		launch(args);
	}
}
