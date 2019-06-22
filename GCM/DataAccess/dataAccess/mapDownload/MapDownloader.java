package dataAccess.mapDownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import gcmDataAccess.GcmDAO;

public class MapDownloader {
	String mapFile;
	File file;
	String FOLDER_DIR;
	private GcmDAO gcmDAO;
	
	public MapDownloader(GcmDAO gcmDAO) {
		this.gcmDAO = gcmDAO;
		// make folder
		FOLDER_DIR = System.getProperty("user.home") + "\\Documents\\mapDownloads\\";
        File folder = new File(FOLDER_DIR);
		folder.mkdir();
	}
	public Boolean downloadMap(GcmDAO gcmDAO, int cityId, int mapId){
		// GET FILE
		System.out.println("Downloading map #"+mapId);
		File file = gcmDAO.getMapFile(mapId);
		System.out.println("Downloaded map #"+mapId);
		if(file == null) {
			System.out.println("file returned is null");
			return false;
		}
		
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
	
    public String getFileExtension(int cityId, int mapId) {
    	 List<String> extensions = Arrays.asList(".jpg", ".png", ".tif", ".jpeg", "gif");
    	 for (String extension : extensions) {
    		 String directory = FOLDER_DIR + "city" + Integer.toString(cityId) + "_map" + Integer.toString(mapId) + extension;
    		 File tmpDir = new File(directory);
    		 boolean exists = tmpDir.exists();
    		 if(exists) {
    			 return extension;
    		 }
		}
    	return null;
    	 
    }
    
    public File getMapFile(int cityId, int mapId) {
   	 	String filePath = FOLDER_DIR + "city" + Integer.toString(cityId) + "_map" + Integer.toString(mapId) + getFileExtension(cityId, mapId);
   	 	System.out.println("Using map file: "+filePath);
   	 	File file = new File(filePath);
   	 	if(file.exists()) return file;
   	 	downloadMap(gcmDAO, cityId, mapId);
	 	
   	 	return new File(filePath);
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
}






