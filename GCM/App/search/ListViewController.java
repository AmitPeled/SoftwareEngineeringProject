package search;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.search.CityMaps;
import gcmDataAccess.GcmDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import mainApp.GcmClient;
import mainApp.SceneNames;
import maps.City;
import maps.Map;
import queries.RequestState;

public class ListViewController implements Initializable
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
	private TextField siteBar;
	
	@FXML
	public ToggleGroup searchOptions;
	@FXML
	private RadioButton rCityname;
	@FXML
	private RadioButton rPointofinterest;
	@FXML
	private RadioButton rBoth;
	@FXML
	private Button goTo;
	Boolean permissionsForMap;
	String selectedRadioBtn;
	RadioButton selectRadio;
	private GcmClient gcmClient;
	int cityId;
	CityMaps currentCity = null;
	@FXML 
    private Button editPrice;
	@FXML 
    private Text cityInfo;
	
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
		rBoth.setToggleGroup(searchOptions);
	}
	
	public void radioButtonListener() {
		rBoth.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		            siteBar.setVisible(true);
		        } else {
		        	siteBar.setVisible(false);
		        }
		    }
		});
	}
	
	public void editPriceListener() {	
		editPrice.setOnMouseClicked((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				gcmClient.switchSceneToEditPrice(cityId);
			}
			
		}));
	}
		
	public void searchListener() {	
		searchBtn.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	
	            	String searchText = searchBar.getText();
	            	listView.setItems(null);
	            	if(searchText != null && !searchText.isEmpty()) {
	            		selectRadio = (RadioButton) searchOptions.getSelectedToggle();
		            	selectedRadioBtn = selectRadio.getText();
		            	
		            	if(selectedRadioBtn.equals("City name")) {
		            		currentCity = gcmDAO.getMapsByCityName(searchText);
		            	}else if(selectedRadioBtn.equals("Point of interest")) {
		            		currentCity = gcmDAO.getMapsByDescription(searchText);
		            	}else{
		            		String siteText = siteBar.getText();
		            		currentCity = gcmDAO.getMapsBySiteAndCityNames(searchText, siteText);
		            	} 
			            	if(currentCity != null) {
				        		List<Map> resultList = currentCity.getMaps();
				            	List<MapItem> mapItemsListResults = parseResultSet(resultList);
				            	
				            	if(mapItemsListResults.isEmpty()) {
				            		System.out.println("NO MAPS FOUND");
				            		listView.setItems(null);
				            		addNewMapBtn.setVisible(false);
				            		buySubscriptionBtn.setVisible(false);
		
				            		RequestState userState = gcmClient.getUserInfo().getState();
				            		if(userState == RequestState.editor || userState == RequestState.contentManager || userState == RequestState.generalManager || userState == RequestState.manager){
				            			addNewMapBtn.setVisible(true);
				            		}
				            		
				            	}else {
				            		cityInfo.setText("City name: " + currentCity.getName() + "    Description: "+ currentCity.getDescription());
				                	cityId = currentCity.getId();
		
				            		ObservableList<MapItem> data = FXCollections.observableArrayList();
					            	for (MapItem item : mapItemsListResults) 
					            	{ 
					            		 data.add(item);
					            	}
					                listView.setItems(data);
					                permissions();
				            	}
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
	
	public ArrayList<MapItem> parseResultSet(List<Map> resultCityMaps){
		ArrayList<MapItem> resultList = new ArrayList<MapItem>();
		for (Map item : resultCityMaps) 
    	{ 
			if(item != null) {
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
				
				MapItem currentMapItem = new MapItem(this,id, mapName, description, pointOfInterest, tours, price);
				resultList.add(currentMapItem);
			}
    	}
		return resultList;
	}

	
	public void permissions() {
		buySubscriptionBtn.setVisible(false);
		addNewMapBtn.setVisible(false);
		editPrice.setVisible(false);
		RequestState userState = gcmClient.getUserInfo().getState();
		permissionsForMap = false;
		// permissionsForMap = gcmClient.getDataAccessObject().notifyMapView()
		if(userState == RequestState.customer && !permissionsForMap) {
			buySubscriptionBtn.setVisible(true);
		}else if(userState == RequestState.editor || userState == RequestState.contentManager || userState == RequestState.generalManager || userState == RequestState.manager){
			addNewMapBtn.setVisible(true);
			editPrice.setVisible(true);
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
    	radioButtonListener();
    	editPriceListener();
    	siteBar.setVisible(false);
    	editPrice.setVisible(false);
    	
        assert mapItem != null : "fx:id=\"anchr\" was not injected: check your FXML file 'AxisFxml.fxml'.";

        listView.setCellFactory(new Callback<ListView<MapItem>, ListCell<MapItem>>() {
            @Override
            public ListCell<MapItem> call(ListView<MapItem> listView) {
                return new CustomListCell(gcmClient.getUserInfo().getState(), permissionsForMap);
            }
        });
    } 
    @FXML
    public void onAddNewMap() {
    	if (currentCity == null) return; 
    		gcmClient.switchSceneToAddMap(currentCity.getId());
    }
    @FXML
    public void onBuySubscription() {
    	if (currentCity == null) return; 
    		gcmClient.switchSceneToAddMap(currentCity.getId());
    }
    @FXML
    public void onBack() {gcmClient.back();}

    public void goToOneTimePurchase(int mapId) {
		System.out.println("Going to One Time Purchase: " + mapId);
		gcmClient.loadMapDisplay(mapId);
	}
	public void goToMap(int mapId) {
		System.out.println("Going to map: " + mapId);
		gcmClient.loadMapDisplay(mapId);
	}
}