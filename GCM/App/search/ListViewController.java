package search;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import search.CustomListCell;
import search.MapItem;

public class ListViewController implements Initializable
{
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

			
	public void setRadioButtons() {
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
	            	RadioButton selectRadio = (RadioButton) searchOptions.getSelectedToggle();
	            	selectedRadioBtn = selectRadio.getText();
	            	ObservableList<MapItem> data = FXCollections.observableArrayList();
	                data.addAll(new MapItem(searchBar.getText(), selectedRadioBtn, "2", "3"), new MapItem("london", "1", "2", "3"), new MapItem("jerusalem", "1", "2", "3"));
	                listView.setItems(data);
	                permissions();
	            }
			})
		);
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
    	setRadioButtons();
    	searchListener();
    	
        listView.setCellFactory(new Callback<ListView<MapItem>, ListCell<MapItem>>() {
            @Override
            public ListCell<MapItem> call(ListView<MapItem> listView) {
                return new CustomListCell();
            }
        });

           
    }
}