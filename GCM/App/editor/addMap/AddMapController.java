package editor.addMap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import editor.FileChooserInit;
import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import maps.City;
import maps.Coordinates;
import maps.Map;
import search.MapItem;

public class AddMapController implements Initializable
{
	private GcmDAO gcmDAO;
	
	@FXML
	TextField mapName;
	@FXML
	TextField mapDescription;
	@FXML
	Button uploadMap;
	@FXML
	Button addMap;
	
	File file;
	int cityId;
	Image image;
	private FileChooser fileChooser;
	BufferedImage bufferedImage;
	
	public AddMapController(GcmDAO gcmDAO, int cityId) {
		this.gcmDAO = gcmDAO;
		fileChooser = new FileChooserInit().getFileChooser();
		this.cityId = cityId;

	}
	
	public void addMapListener() {	
		addMap.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String name = mapName.getText();
	            	String description = mapDescription.getText();
	            	int width = bufferedImage.getWidth();
	            	int height = bufferedImage.getHeight();
	            	
	            	if(name != null && !name.isEmpty() && description != null && !description.isEmpty() && file != null) {
	            		Map newMap = new Map(name, description, width, height, new Coordinates());
	            		gcmDAO.addMapToCity(cityId, newMap, file);
	            	}
	            }
			})
		);
	}
	
	public void uploadMapListener() {	
		uploadMap.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	 //Show open file dialog
	                file = fileChooser.showOpenDialog(null);
	                           
	                try {
	                    bufferedImage = ImageIO.read(file);
	                    image = SwingFXUtils.toFXImage(bufferedImage, null);
	                } catch (IOException ex) {
	                }
	            }
			})
		);
	}
	
    /**
	* @param url
	* @param rb
	**/
    @Override 
	public void initialize(URL url, ResourceBundle rb) {
    	addMapListener();
    	uploadMapListener();
    }
}