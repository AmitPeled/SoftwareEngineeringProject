package editor.addCity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import editor.FileChooserInit;
import gcmDataAccess.GcmDAO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import maps.City;


public class AddCityController implements Initializable
{
	private GcmDAO gcmDAO;
	
	@FXML
	TextField cityName;
	@FXML
	TextField cityDescription;
	@FXML
	Button uploadMap;
	@FXML
	Button addCity;
	
	File file;
	Image image;
	private FileChooser fileChooser;
	
	public AddCityController(GcmDAO gcmDAO) {
		this.gcmDAO = gcmDAO;
		fileChooser = new FileChooserInit().getFileChooser();
	}
	
	
	public void addCityListener() {	
		addCity.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	String name = cityName.getText();
	            	String description = cityDescription.getText();
	            	if(name != null && !name.isEmpty() && description != null && !description.isEmpty() && file != null) {
		            	City newCity = new City(1, name, description);
		            	//City newCity = new City(name, description, file);
		            	gcmDAO.addCity(newCity);
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
	                    BufferedImage bufferedImage = ImageIO.read(file);
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
    	addCityListener();
    	uploadMapListener();
    }
}