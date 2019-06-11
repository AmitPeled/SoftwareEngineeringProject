package editor;

import javafx.stage.FileChooser;

public class FileChooserInit {
	private FileChooser fileChooser;
	
	public FileChooserInit() {
		fileChooser = new FileChooser();

		//Set extension filter
	    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	    fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	}
	public FileChooser getFileChooser() {
		return fileChooser;
	}
}
