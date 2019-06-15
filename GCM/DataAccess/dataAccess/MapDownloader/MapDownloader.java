package dataAccess.MapDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import gcmDataAccess.GcmDAO;

public class MapDownloader {
	String FOLDER_DIR = "C:\\Users\\gabriel\\Documents\\mapDownloads\\";
	
	public MapDownloader(GcmDAO gcmDAO, int cityId, int mapId) throws IOException {
		// GET MAP
		//File mapFile = gcmDAO.downloadMap(mapId);
		File mapFile = new File("C:\\Users\\gabriel\\Documents\\IMG_5747.jpg");
		byte[] fileContent = Files.readAllBytes(mapFile.toPath());

        // make folder
        File folder = new File(FOLDER_DIR);
		Boolean bool = folder.mkdir();
		
		// UPDATE DIRECTORY ADDRESS
		String extension = "";
		String name = mapFile.getName();
        extension = name.substring(name.lastIndexOf("."));
		String updatedFolderDir = FOLDER_DIR + "city" + Integer.toString(cityId) + "_map" + Integer.toString(mapId) + extension;

		// DOWNLOAD TO FOLDER
         FileInputStream fis = null;
         int i = 0;
         char c;
         FileOutputStream fos = new FileOutputStream(new File(updatedFolderDir)); //FILE Save Location goes here
         fos.write(fileContent);  // write out the file we want to save.
         fos.flush();
         fos.close(); // close the output stream write
	}
}
