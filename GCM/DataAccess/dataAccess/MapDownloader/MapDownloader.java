package dataAccess.MapDownloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import gcmDataAccess.GcmDAO;

public class MapDownloader {
	String mapFile;
	File file;
	String FOLDER_DIR;
	public MapDownloader() {
		// make folder
		FOLDER_DIR = System.getProperty("user.home") + "\\Documents\\mapDownloads\\";
        File folder = new File(FOLDER_DIR);
		Boolean bool = folder.mkdir();
	}
	public Boolean DownloadMap(GcmDAO gcmDAO, int cityId, int mapId) throws IOException {
		// GET FILE
		//File file = gcmDAO.downloadMap(mapId);
		File file = new File("C:\\Users\\gabriel\\Documents\\IMG_5747.jpg");
		byte[] fileContent = Files.readAllBytes(file.toPath());

		
		// UPDATE DIRECTORY ADDRESS
		String extension = "";
		String name = file.getName();
        extension = name.substring(name.lastIndexOf("."));
		String UPLOAD_FOLDER = FOLDER_DIR + "city" + Integer.toString(cityId) + "_map" + Integer.toString(mapId) + extension;

		// DOWNLOAD TO FOLDER
		FileInputStream fileInputStream = null;

        try {

            byte[] bFile = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);

            //save bytes[] into a file
            writeBytesToFile(bFile, UPLOAD_FOLDER );
            writeBytesToFileClassic(bFile, UPLOAD_FOLDER);
            writeBytesToFileNio(bFile, UPLOAD_FOLDER);

            System.out.println("Done");
            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
		return false;
	}
	
    //Classic, < JDK7
    private static void writeBytesToFileClassic(byte[] bFile, String fileDest) {

        FileOutputStream fileOuputStream = null;

        try {
            fileOuputStream = new FileOutputStream(fileDest);
            fileOuputStream.write(bFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //Since JDK 7 - try resources
    private static void writeBytesToFile(byte[] bFile, String fileDest) {

        try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
            fileOuputStream.write(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Since JDK 7, NIO
    private static void writeBytesToFileNio(byte[] bFile, String fileDest) {

        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String checkIfFileExist(int cityId, int mapId) {
    	 List<String> extensions = Arrays.asList(".jpg", ".png", ".tif", ".jpeg");
    	 for (String extension : extensions) {
    		 String directory = FOLDER_DIR + "city" + Integer.toString(cityId) + "_map" + Integer.toString(mapId) + extension;
    		 File tmpDir = new File(directory);
    		 boolean exists = tmpDir.exists();
    		 if(exists) {
    			 return extension;
    		 }
		}
    	return "no";
    	 
    }
    public File getFileFromDirectory(int cityId, int mapId, String extension) throws IOException {
   	 	String directory = FOLDER_DIR + "city" + Integer.toString(cityId) + "_map" + Integer.toString(mapId) + extension;
   	 	File file = new File(directory);
   	 	return file;
    }
}
