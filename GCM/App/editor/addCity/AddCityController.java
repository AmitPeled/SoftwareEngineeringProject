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
	
	public AddCityController(GcmDAO gcmDAO) {
		this.gcmDAO = gcmDAO;
	}
	
	
	public void addCityListener() {	
		addCity.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	String name = cityName.getText();
	            	String description = cityDescription.getText();
	            	List<String> list = Arrays.asList(name, description);
	            	if(checkFilledFields(list)){
	            		City newCity = new City(name, description);
		            	int cityId = gcmDAO.addCity(newCity);
					}else {
						setErrors("Please fill all fields!");
					}
		            	
	            }
			})
		);
	}

	public void setErrors(String error) {
		errors.setVisible(true);
		errors.setText(error);
	}
	public boolean checkFilledFields(List<String> list) {
		for (String item : list) {
			if(item == null || item.isEmpty()) {
				return false;
			}
		}
		return true;
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