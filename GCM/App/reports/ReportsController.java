package reports;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import maps.Map;
import reports.cells.CityListCell;
import reports.cells.CustomerListCell;
import reports.cells.WorkerListCell;
import reports.resultItems.CityItem;
import reports.resultItems.CustomerItem;
import reports.resultItems.WorkerItem;
import search.CustomListCell;
import search.MapItem;

public class ReportsController implements Initializable{
	private GcmDAO gcmDAO;
	@FXML
	TextField searchBar;
	@FXML
	RadioButton rCity;
	@FXML
	RadioButton rWorker;
	@FXML
	RadioButton rCustomer;
	@FXML
	DatePicker dateFrom;
	@FXML
	DatePicker dateUntil;
	@FXML
	Button search;
	@FXML
	Button getReportsForAll;
	@FXML
	ListView<CityItem> cityResults;
	@FXML
	ListView<WorkerItem> workerResults;
	@FXML
	ListView<CustomerItem> customerResults;
	@FXML
	TextField errors;
	@FXML
	public ToggleGroup searchOptions;
	String selectedRadioValue;
	RadioButton selectRadio;
	
	public ReportsController(GcmDAO gcmDAO) { 
		this.gcmDAO = gcmDAO;
	}
	
	public void initRadioButtons() {
		// Radio 1: cityName
		rCity.setToggleGroup(searchOptions);
		rCity.setSelected(true);

		// Radio 2: rWorker.
		rWorker.setToggleGroup(searchOptions);
		
		// Radio 3: rCustomer.
		rCustomer.setToggleGroup(searchOptions);
	}
	public void getReportsForAllListener() {
		getReportsForAll.setOnMouseClicked((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				cityResults.setVisible(true);
    			customerResults.setVisible(false);
    			workerResults.setVisible(false);
        		//List<Map> resultSet;
        		//resultSet = gcmDAO.getMapsByCityName(searchText);
        		CityItem city1 = new CityItem(1, "1", "2", "3", "4", "5");
    			List<CityItem> result = Arrays.asList(city1);
        		ObservableList<CityItem> data = FXCollections.observableArrayList();
            	for (CityItem item : result) 
            	{ 
            		 data.add(item);
            	}
            	cityResults.setItems(data);
			}
		
		}));
	}
	public void searchListener() {	
		search.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	String searchText = searchBar.getText();
	            	selectRadio = (RadioButton) searchOptions.getSelectedToggle();
	            	selectedRadioValue = selectRadio.getText();
	            	LocalDate datefrom = dateFrom.getValue();
	            	LocalDate dateuntil = dateUntil.getValue();
	            	
	            	List<String> list = Arrays.asList(searchText);
	            	
	            	if(checkFilledFields(list) && datefrom != null && dateuntil != null) {
	            		errors.setVisible(false);
	            		cityResults.setItems(null);
	            		customerResults.setItems(null);
	            		workerResults.setItems(null);
	            		if(selectedRadioValue.equals("City name")) {
	            			reportByCityName();
		            	}else if(selectedRadioValue.equals("Customer")){
		            		reportsByCustomer();
		            	}else {
		            		reportsByWorker();
		            	}
	            	}else {
	            		setErrors("Please fill all fields!");
	            	}
	            }
		}));
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
	
	public void initCellFactories(){
		cityResults.setCellFactory(new Callback<ListView<CityItem>, ListCell<CityItem>>() {
            @Override
            public ListCell<CityItem> call(ListView<CityItem> listView) {
                return new CityListCell();
            }
        });
		workerResults.setCellFactory(new Callback<ListView<WorkerItem>, ListCell<WorkerItem>>() {
            @Override
            public ListCell<WorkerItem> call(ListView<WorkerItem> listView) {
                return new WorkerListCell();
            }
        });
		customerResults.setCellFactory(new Callback<ListView<CustomerItem>, ListCell<CustomerItem>>() {
            @Override
            public ListCell<CustomerItem> call(ListView<CustomerItem> listView) {
                return new CustomerListCell();
            }
        });
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		errors.setVisible(false);
		getReportsForAllListener();
		initRadioButtons();
		searchListener();
		initCellFactories();
	}
	
	private void reportByCityName() {
		cityResults.setVisible(true);
		customerResults.setVisible(false);
		workerResults.setVisible(false);
		//List<Map> resultSet;
		//resultSet = gcmDAO.getMapsByCityName(searchText);
		CityItem city1 = new CityItem(1, "1", "2", "3", "4", "5");
		CityItem city2 = new CityItem(1, "5", "6", "7", "8", "9");
		CityItem city3 = new CityItem(1, "10", "11", "12", "13", "14");
		List<CityItem> result = Arrays.asList(city1, city2, city3);
		ObservableList<CityItem> data = FXCollections.observableArrayList();
		for (CityItem item : result) 
		{ 
			 data.add(item);
		}
		cityResults.setItems(data);
	}
	

	private void reportsByWorker() {
		cityResults.setVisible(false);
		customerResults.setVisible(false);
		workerResults.setVisible(true);
		//List<Map> resultSet;
		//resultSet = gcmDAO.getMapsByDescription(searchText);
		WorkerItem city1 = new WorkerItem(1, "1", "2", "3", "4", "5");
		WorkerItem city2 = new WorkerItem(1, "5", "6", "7", "8", "9");
		WorkerItem city3 = new WorkerItem(1, "10", "11", "12", "13", "14");
		List<WorkerItem> result = Arrays.asList(city1, city2, city3);
		ObservableList<WorkerItem> data = FXCollections.observableArrayList();
		for (WorkerItem item : result) 
		{ 
			 data.add(item);
		}
		workerResults.setItems(data);
	}

	private void reportsByCustomer() {
		cityResults.setVisible(false);
		customerResults.setVisible(true);
		workerResults.setVisible(false);
		//List<Map> resultSet;
		//resultSet = gcmDAO.getMapsByDescription(searchText);
		List<String> purchaseHistory = Arrays.asList("haifa", "tel aviv");
		CustomerItem city1 = new CustomerItem(1, "1", "2", "3", purchaseHistory);
		CustomerItem city2 = new CustomerItem(1, "5", "6", "7", purchaseHistory);
		CustomerItem city3 = new CustomerItem(1, "10", "11", "12", purchaseHistory);
		List<CustomerItem> result = Arrays.asList(city1, city2, city3);
		ObservableList<CustomerItem> data = FXCollections.observableArrayList();
		for (CustomerItem item : result) 
		{ 
			 data.add(item);
		}
		customerResults.setItems(data);
	}
}
