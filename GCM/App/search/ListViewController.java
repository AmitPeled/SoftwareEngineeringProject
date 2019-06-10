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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import mainApp.GcmClient;
import maps.Map;
import search.CustomListCell;
import search.MapItem;

public class ListViewController implements Initializable
{
	private GcmClient gcmClient;
	private GcmDAO ISearch = new GcmDAO();
	private int permission;
	
	@FXML 
    private ListView<MapItem> listView;
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
	
	String selectedRadioBtn;
	RadioButton selectRadio;
	public ListViewController(GcmClient gcmClient) {
		if(gcmClient == null) throw new IllegalArgumentException("gcmClient is null");
		this.gcmClient = gcmClient;
	}
	public ListViewController() {

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
		            		resultSet = ISearch.getMapsByCityName(searchText);
		            	}else if(selectedRadioBtn.equals("Description")) {
		            		resultSet = ISearch.getMapsBySiteName(searchText);
		            	}else{
		            		resultSet = ISearch.getMapsByDescription(searchText);
		            	}
		            	List<MapItem> results = parseResultSet(resultSet);
		            	
		            	ObservableList<MapItem> data = FXCollections.observableArrayList();
		            	for (MapItem item : results) 
		            	{ 
		            		 data.add(item);
		            	}
		               
		                listView.setItems(data);
		                permissions();
	            	}
	            }
			})
		);
	}
	
	public ArrayList<MapItem> parseResultSet(List<Map> resultSet){
		ArrayList<MapItem> resultList = new ArrayList<MapItem>();

		for (Map item : resultSet) 
    	{ 
			int id = item.getId();
			String description = item.getDescription();
			String pointOfInterest = Integer.toString(item.getSites().size());
			String tours = Integer.toString(item.getTours().size());
			double price = item.getPrice();
			MapItem currentMapItem = new MapItem("london", description, pointOfInterest, tours, price);
			resultList.add(currentMapItem);
    	}
		return resultList;
	}
	
	public int checkForPermissions() {
		return 1;
	}
	
	public void permissions() {
		permission = checkForPermissions();
		if(permission == 0) {
			buySubscriptionBtn.setVisible(true);
		}else if(permission == 1) {
			addNewMapBtn.setVisible(true);
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
    	
        listView.setCellFactory(new Callback<ListView<MapItem>, ListCell<MapItem>>() {
            @Override
            public ListCell<MapItem> call(ListView<MapItem> listView) {
                return new CustomListCell();
            }
        });

           
    }
}