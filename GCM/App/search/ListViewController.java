package search;

import java.net.URL;
import java.util.ResourceBundle;

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
	ToggleGroup rBtnGroup;
	@FXML
	private RadioButton button1;
	@FXML
	private RadioButton button2;
	@FXML
	private RadioButton button3;
	
	String selectedRadioBtn;
	
	public void setRadioButtons() {
		rBtnGroup = new ToggleGroup();
		// Radio 1: cityName
		button1 = new RadioButton("rCityName");
		button1.setToggleGroup(rBtnGroup);
		button1.setSelected(true);
		 
		// Radio 2: pointOfInterest.
		button2 = new RadioButton("rPointOfInterest");
		button2.setToggleGroup(rBtnGroup);
		// Radio 3: description.
		button3 = new RadioButton("rDescription");
		button3.setToggleGroup(rBtnGroup);
	}
	public void searchListener() {	
		searchBtn.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	RadioButton selectedRadioButton = (RadioButton) rBtnGroup.getSelectedToggle();
	            	selectedRadioBtn = selectedRadioButton.getText();
	            	ObservableList<MapItem> data = FXCollections.observableArrayList();
	                data.addAll(new MapItem(searchBar.getText(), selectedRadioBtn, "2", "3"), new MapItem("london", "1", "2", "3"), new MapItem("jerusalem", "1", "2", "3"));
	                listView.setItems(data);
	                permissions();
	            }
			})
		);
	}
	public void radioBtnListener() {
//		rBtnGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
//	    {
//	    @Override
//	    public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1)
//	        {
//	        RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
//	        System.out.println("Selected Radio Button - "+chk.getText());
//	        }
//	    });

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
    	radioBtnListener();
    	searchListener();
    	
        listView.setCellFactory(new Callback<ListView<MapItem>, ListCell<MapItem>>() {
            @Override
            public ListCell<MapItem> call(ListView<MapItem> listView) {
                return new CustomListCell();
            }
        });

           
    }
}