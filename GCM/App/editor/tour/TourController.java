package editor.tour;


import java.net.URL;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import mainApp.GcmClient;
import maps.Site;
import maps.Tour;
import utility.TextFieldUtility;

public class TourController  implements Initializable {
	private GcmDAO gcmDAO;
	@FXML
	TextField description;
	@FXML
	ComboBox<Site> sitesPicker;
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
	 
	int counterOfAddedPlaces = 0;
	int tourId;
	int cityId;
	ObservableList<String> data = FXCollections.observableArrayList();
	List<Site> tourSites;
	List<Integer> sitesTimeToVisit;
	Tour tour;
	int mapId;
	TextFieldUtility utilities;
	GcmClient gcmClient;
	
	public TourController(GcmClient gcmClient, int cityId, int mapId, Tour tour, TextFieldUtility utilities) {
		this.gcmClient = gcmClient;
		this.gcmDAO = gcmClient.getDataAccessObject();
		this.mapId = mapId;
		this.tour = tour;
		this.tourId = tour.getId();
		this.cityId = cityId;
		tourSites = tour.getSites();
		sitesTimeToVisit = tour.getSitesTimeToVisit();
		this.utilities = utilities;
	}
	public void addPlaceListener() {	
		addNewPlace.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	Site sitesPickerValue = sitesPicker.getSelectionModel().getSelectedItem();

	            	if(sitesPickerValue != null) {
	            		 int id = sitesPickerValue.getId();
		            	 String time = timeEstimation.getText();
	            		 if(time != null && !time.isEmpty()){
	            			 
	 	            			if(!tour.checkIfSiteExist(sitesPickerValue)) {
		 	            			errors.setVisible(false);
		 	            			tourSites.add(sitesPickerValue);
		 		            		sitesTimeToVisit.add(Integer.parseInt(time));
		 		            		data.add(sitesPickerValue.getName() + " - " + time + " hours");
		 		            		listOfSites.setItems(data);
		 		            		
		 		            		timeEstimation.setText(""); 
		 		            		counterOfAddedPlaces++;
	 	            			}else {
	 	            				utilities.setErrors("Site already exist!", errors);
			            			timeEstimation.setText("");
		 	            		}
	 	            			
		            		}else {
		            			utilities.setErrors("Please fill the time estimation!", errors);
		            			timeEstimation.setText("");	
		            		}
	            	} else {
	            		utilities.setErrors("No site selected!", errors);
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
	            		
	            		Tour newTour = new Tour(tourDescription);
	            		newTour.setSites(tourSites);
	            		newTour.setSitesTimeToVisit(sitesTimeToVisit);
	            		newTour.setNumberOfLastAddedPlaces(counterOfAddedPlaces);
	            		
	            		gcmDAO.tourManager(cityId, newTour);
	            		gcmClient.back();
	            	}else{
            			utilities.setErrors("Please add a tour description!", errors);
	            	}
	            }
			})
		);
	}
	public void init() { 
		// call function to retrieve list of sites that wasn't added for this map 
		// insert them to sites list
		Callback<ListView<Site>, ListCell<Site>> cellFactory = new Callback<ListView<Site>, ListCell<Site>>() {

		    @Override
		    public ListCell<Site> call(ListView<Site> l) {
		        return new ListCell<Site>() {

		            @Override
		            protected void updateItem(Site item, boolean empty) {
		                super.updateItem(item, empty);
		                if (item == null || empty) {
		                    setGraphic(null);
		                } else {
		                    setText(item.getName());
		                }
		            }
		        } ;
		    }
		};

		sitesPicker.setButtonCell(cellFactory.call(null));
		sitesPicker.setCellFactory(cellFactory);

		List<Site> exisitingSites = gcmDAO.getCitySites(cityId);
		sitesPicker.setItems(FXCollections.observableArrayList(exisitingSites));
		System.out.println(exisitingSites);
		System.out.println(tour);
		if(tour.getId() != -1) {
			System.out.println(tour.getId());
			description.setText(tour.getDescription());
			
			// set list of existing sites on tour
			int index = 0;
			for (Site site : tourSites) {
				data.add(site.getName() + " - " + Integer.toString(sitesTimeToVisit.get(index)) + " hours");
				index++;
			}
			
			listOfSites.setItems(data);
		}
		
	}

	@FXML
	public void onBackButton() {
		gcmClient.back();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		addTourListener();
		addPlaceListener();
	}
	public void initalizeControl(int cityId, int mapId, Tour tour) {
	    this.cityId = cityId;
	    this.mapId = mapId;
	    this.tour = tour;
	    this.tourId = tour.getId();
		this.cityId = cityId;
		tourSites = tour.getSites();
		sitesTimeToVisit = tour.getSitesTimeToVisit();
	}
}
