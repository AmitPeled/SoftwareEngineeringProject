package editor.addCity;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


import gcmDataAccess.GcmDAO;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import maps.City;


public class AddCityController implements Initializable
{
	private GcmDAO gcmDAO;
	
	@FXML
	TextField cityName;
	@FXML
	TextField cityDescription;

	@FXML
	Button addCity;
	
	File file;
	Image image;
	
	public AddCityController(GcmDAO gcmDAO) {
		this.gcmDAO = gcmDAO;
	}
	
	
	public void addCityListener() {	
		addCity.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	String name = cityName.getText();
	            	String description = cityDescription.getText();
	            	if(name != null && !name.isEmpty() && description != null && !description.isEmpty()) {
	            		// missing id
		            	City newCity = new City(1, name, description);
		            	int cityId = gcmDAO.addCity(newCity);
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
    }
}