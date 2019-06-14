package editor.addCity;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
import utility.TextFieldUtility;


public class AddCityController implements Initializable
{
	private GcmDAO gcmDAO;
	
	@FXML
	TextField cityName;
	@FXML
	TextField cityDescription;
	@FXML
	TextField errors;
	@FXML
	Button addCity;
	
	File file;
	Image image;
	TextFieldUtility utilities;
	
	public AddCityController(GcmDAO gcmDAO, TextFieldUtility utilities) {
		this.gcmDAO = gcmDAO;
		this.utilities = utilities;
	}
	
	
	public void addCityListener() {	
		addCity.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	errors.setVisible(false);
	            	String name = cityName.getText();
	            	String description = cityDescription.getText();
	            	List<String> list = Arrays.asList(name, description);
	            	if(utilities.checkFilledFields(list)){
	            		City newCity = new City(name, description);
		            	int cityId = gcmDAO.addCity(newCity);
					}else {
						utilities.setErrors("Please fill all fields!", errors);
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
    	errors.setVisible(false);
    	addCityListener();
    }
}