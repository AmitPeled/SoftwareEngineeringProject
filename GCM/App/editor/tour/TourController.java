package editor.tour;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import maps.Site;
import search.MapItem;

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
	ListView<String> listOfSites;
	@FXML
	TextField errors;
	
	ObservableList<String> data = FXCollections.observableArrayList();
	List<String> newSites;
	List<String> newTimeEstimations;
	TourSites tourSites;
	int mapId;
	
	public TourController(GcmDAO gcmDAO, int mapId, TourSites tourSites) {
		this.gcmDAO = gcmDAO;
		this.mapId = mapId;
		this.tourSites = tourSites;
		newSites = tourSites.getSites();
		newTimeEstimations = tourSites.getTimeEstimations();
	}
	public void addPlaceListener() {	
		addNewPlace.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String sitesPickerValue = sitesPicker.getSelectionModel().getSelectedItem();
	            	String time = timeEstimation.getText();
	            	
	            	if(sitesPickerValue != null && !sitesPickerValue.isEmpty()) {
	            		 if(time != null && !time.isEmpty()){
	 	            			if(!tourSites.checkIfSiteExist(sitesPickerValue)) {
		 	            			errors.setVisible(false);
	
		 	            			newSites.add(sitesPickerValue);
		 		            		newTimeEstimations.add(time);
	
		 		            		data.add(sitesPickerValue + " " + time);
		 		            		listOfSites.setItems(data);
		 		            		
		 		            		timeEstimation.setText("");
	 	            			}else {
		 	            			errors.setVisible(true);
			            			errors.setText("Site already exist!");
			            			timeEstimation.setText("");
		 	            		}
		            		}else {
		            			errors.setVisible(true);
		            			errors.setText("Please fill the time estimation!");
		            			timeEstimation.setText("");	
		            		}
	            	}
	            }
			})
		);
	}
	public void addTourListener() {	
		addEditTour.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String tourDescription = description.getText();
	            	
	            	if(tourDescription != null && !tourDescription.isEmpty()) {
	            		errors.setVisible(false);
	            		tourSites.addSites(newSites, newTimeEstimations);
	            		
	            		// call method to add or edit tour GcmDAO.addSitesToTour(tourSites);
	            		
	            	}else{
	            		errors.setVisible(true);
	            		errors.setText("Please add a tour description!");
	            	}
	            }
			})
		);
	}
	public void init() {
		// call function to retrieve list of sites that wasn't added for this map 
		// insert them to sites list
		sitesPicker.setItems(FXCollections.observableArrayList("tel aviv","2","3","4","5","6"));
		
		// set list of sites
		int index = 0;
		for (String site : newSites) {
			data.add(site + " " + tourSites.getTimeEstimationByIndex(index));
			index++;
		}
		
		listOfSites.setItems(data);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		addTourListener();
		addPlaceListener();
	}

}
