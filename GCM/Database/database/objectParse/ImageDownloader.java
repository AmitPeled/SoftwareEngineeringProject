package database.objectParse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;

public class ImageDownloader {
	 public static File downloadImage(byte[] imageBytes, String filePath) throws IOException {
		  String imageType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));
		  String imageExtension = imageType.substring(imageType.indexOf('/') + 1); // Content Type format is [file_type] / [file_extension]
		  String imagePath = filePath + "." + imageExtension;
		  try (FileOutputStream stream = new FileOutputStream(imagePath)) {
			   stream.write(imageBytes);
		  } catch (FileNotFoundException e) {
			   e.printStackTrace();
		  } catch (IOException e) {
			   e.printStackTrace();
		  }
		  return new File(imagePath);
	 }
}
