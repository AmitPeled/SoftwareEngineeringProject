package editor.tour;


import java.net.URL;
import java.util.ResourceBundle;

import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TourController  implements Initializable {
	private GcmDAO gcmDAO;
	@FXML
	TextField description;
	@FXML
	ComboBox<String> sitesPicker;
	@FXML
	TextField timeEstimation;
	@FXML
	Button addNewPlace;
	@FXML
	Button addEditTour;
	@FXML
	//ListView<PlaceOnTour> listOfPlaces;
	
	int mapId;
	
	public TourController(GcmDAO gcmDAO, int mapId) {
		this.gcmDAO = gcmDAO;
		this.mapId = mapId;
	}
	public void addPlaceListener() {	
		addNewPlace.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String sitesPickerValue = sitesPicker.getSelectionModel().getSelectedItem();
	            	String time = timeEstimation.getText();
	            	if(sitesPickerValue != null && !sitesPickerValue.isEmpty() && time != null && !time.isEmpty()) {
	            		// call function to add site to tour
	            	}
	            }
			})
		);
	}
	public void tourListener() {	
		addEditTour.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String tourDescription = description.getText();
	            	
	            	if(tourDescription != null) {
	            		// call method to add or edit tour
	            	}
	            }
			})
		);
	}
	public void initSitesPicker() {
		// call function to retrieve list of places for this map
		sitesPicker.setItems(FXCollections.observableArrayList("1","2","3","4","5","6"));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initSitesPicker();
		tourListener();
		
	}

}
