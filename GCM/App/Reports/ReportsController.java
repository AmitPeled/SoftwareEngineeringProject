package Reports;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Reports.cells.CityListCell;
import Reports.cells.CustomerListCell;
import Reports.cells.WorkerListCell;
import Reports.resultItems.CityItem;
import Reports.resultItems.CustomerItem;
import Reports.resultItems.WorkerItem;
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
	            		if(selectedRadioValue.equals("City name")) {
		            		List<Map> resultSet;
		            		resultSet = gcmDAO.getMapsByCityName(searchText);
		            	}else if(selectedRadioValue.equals("Customer")){
		            		List<Map> resultSet;
		            		resultSet = gcmDAO.getMapsByDescription(searchText);
		            	}else {
		            		
		            	}
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
		initRadioButtons();
		searchListener();
		initCellFactories();
	}
}
