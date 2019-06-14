package approvalReports;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import gcmDataAccess.GcmDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import maps.City;
import maps.Coordinates;
import maps.Site;
import maps.Tour;
import search.CustomListCell;
import search.MapItem;
import utility.TextFieldUtility;

public class ApprovalReportsController  implements Initializable {
	private GcmDAO gcmDAO;
	@FXML
	TableView<City> cityTable;
	@FXML
	TableColumn<City, String> cityName;
	@FXML
	TableColumn<City, String> cityDescription;
	@FXML
	TableColumn<City, String> actionTaken;
	@FXML
	TableColumn<City, Button> approvalDisapproval;
	
	public ApprovalReportsController(GcmDAO gcmDAO) {
		this.gcmDAO = gcmDAO;
	}
	
	public void setColumns() {
		
	        List<City> cityLists = new ArrayList<City>();
	        cityLists.add(new City(1, "haifa", "this city sucks"));
	        cityLists.add(new City(2, "tel aviv", "well this city doesn't suck"));
//	        List<String> cityName = new ArrayList<String>();
//	        for (City city : cityLists) {
//		        cityName.add(city.getName());
//			}
	        cityName.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getName()));
	        cityDescription.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getDescription()));
	        //actionTaken.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getDescription()));
	        
	        
	        Callback<TableColumn<City, Button>, TableCell<City, Button>> cellFactory 
	        = new Callback<TableColumn<City, Button>, TableCell<City, Button>>() {
	        	@Override
		        public TableCell call(final TableColumn<City, Button> param) {
		            final TableCell<City, Button> cell = new TableCell<City, Button>() {
		
		                Button approve = new Button("Approve!");
		                Button disapprove = new Button("Disapprove!");
		                
		                @Override
		                public void updateItem(Button item, boolean empty) {
		                    super.updateItem(item, empty);
		                    if (empty) {
		                        setGraphic(null);
		                        setText(null);
		                    } else {
		                    	approve.setOnAction(event -> {
		                        	City city = getTableView().getItems().get(getIndex());
		                        	System.out.println(city.getId());
		                        });
		                    	disapprove.setOnAction(event -> {
		                        	City city = getTableView().getItems().get(getIndex());
		                        	System.out.println(city.getId());
		                        });
		                    	HBox pane = new HBox(approve, disapprove);

		                        setGraphic(pane);
		                        setText(null);
		                    }
		                }
		            };
		            return cell;
		        }
	        };
	    	approvalDisapproval.setCellFactory(cellFactory);
	        
	        
	        ObservableList<City> details = FXCollections.observableArrayList(cityLists);
	        cityTable.setItems(details);
	}
	public void initTableView() {
		//cityName.setCellFactory(TextFieldTableCell.forTableColumn()) 

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTableView();
		setColumns();
	}

}
