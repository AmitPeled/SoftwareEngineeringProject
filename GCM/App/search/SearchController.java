package search;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dataAccess.search.SearchDAO;
import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import login.LoginModel;
import mainApp.GcmClient;
import maps.Map;
import queries.RequestState;
import search.CustomListCell;
import search.MapItem;
import userInfo.UserInfoImpl;

public class SearchController implements Initializable
{
	private GcmDAO gcmDAO;
	private int permission;
	 
	@FXML 
    private ListView<MapItem> listView;
	@FXML
    private AnchorPane mapItem;
	@FXML 
    private Button buySubscriptionBtn;
	@FXML 
    private Button addNewMapBtn;
	@FXML 
    private Button searchBtn;
	@FXML
	private TextField searchBar;
	
	@FXML
	public ToggleGroup searchOptions;
	@FXML
	private RadioButton rCityname;
	@FXML
	private RadioButton rPointofinterest;
	@FXML
	private RadioButton rDescription;
	@FXML
	private Button goTo;
	
	String selectedRadioBtn;
	RadioButton selectRadio;
	private GcmClient gcmClient;
	
	public ListViewController(GcmClient gcmClient,GcmDAO gcmDAO) {
		this.gcmClient = gcmClient;
		this.gcmDAO = gcmDAO;
	}

	
	public void initRadioButtons() {
		// Radio 1: cityName
		rCityname.setToggleGroup(searchOptions);
		rCityname.setSelected(true);

		// Radio 2: pointOfInterest.
		rPointofinterest.setToggleGroup(searchOptions);
		
		// Radio 3: description.
		rDescription.setToggleGroup(searchOptions);
	}

	
	public void searchListener() {	
		searchBtn.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	
	            	String searchText = searchBar.getText();
	            	
	            	if(searchText != null && !searchText.isEmpty()) {
	            		selectRadio = (RadioButton) searchOptions.getSelectedToggle();
		            	selectedRadioBtn = selectRadio.getText();
		            	
		            	List<Map> resultSet;
		            	if(selectedRadioBtn.equals("City name")) {
		            		resultSet = gcmDAO.getMapsByCityName(searchText);
		            	}else if(selectedRadioBtn.equals("Description")) {
		            		resultSet = gcmDAO.getMapsByDescription(searchText);
		            	}else{
		            		resultSet = gcmDAO.getMapsBySiteName(searchText);
		            	}
		            	List<MapItem> results = parseResultSet(resultSet);
		            	
		            	if(results.isEmpty()) {
		            		System.out.println("NO MAPS FOUND");
		            		listView.setItems(null);
		            		addNewMapBtn.setVisible(false);
		            		buySubscriptionBtn.setVisible(false);
		            	}else {
		            		ObservableList<MapItem> data = FXCollections.observableArrayList();
			            	for (MapItem item : results) 
			            	{ 
			            		 data.add(item);
			            	}
			                listView.setItems(data);
			                permissions();
		            	}
	            	}else {
	            		listView.setItems(null);
	            		addNewMapBtn.setVisible(false);
	            		buySubscriptionBtn.setVisible(false);
	            	}
	            }
			})
		);
	}
	
	public ArrayList<MapItem> parseResultSet(List<Map> resultSet){
		ArrayList<MapItem> resultList = new ArrayList<MapItem>();

		for (Map item : resultSet) 
    	{ 
			String id = Integer.toString(item.getId());
			String mapName = item.getName();
			String description = item.getDescription();
			String pointOfInterest;
			
			if(item.getSites() != null) {
				pointOfInterest = Integer.toString(item.getSites().size());
			}else {
				pointOfInterest = "0";
			}
			String tours;
			if(item.getTours() != null) {
				tours = Integer.toString(item.getTours().size());
			}else {
				tours = "0";
			}
			double price = item.getPrice();
			
			MapItem currentMapItem = new MapItem(id, mapName, description, pointOfInterest, tours, price);
			resultList.add(currentMapItem);
    	}
		return resultList;
	}

	
	public void permissions() {
		//RequestState userState = new UserInfoImpl().getState();
		if(permission == 0) {
			buySubscriptionBtn.setVisible(true);
		}else {
			addNewMapBtn.setVisible(true);
		}
	}

    /**
	* @param url
	* @param rb
	**/
    @Override 
	public void initialize(URL url, ResourceBundle rb) {
    	initRadioButtons();
    	searchListener();
    	
        assert mapItem != null : "fx:id=\"anchr\" was not injected: check your FXML file 'AxisFxml.fxml'.";

        listView.setCellFactory(new Callback<ListView<MapItem>, ListCell<MapItem>>() {
            @Override
            public ListCell<MapItem> call(ListView<MapItem> listView) {
                return new CustomListCell();
            }
        });
    }
    
    @FXML
    public void onBack() {gcmClient.back();}
}