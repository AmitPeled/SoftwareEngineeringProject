package editor.buySubscription;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import dataAccess.users.PurchaseDetails;
import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import maps.Coordinates;
import maps.Map;
import utility.TextFieldUtility;

import static java.lang.Math.toIntExact;


public class BuySubscriptionController implements Initializable
{
	private GcmDAO gcmDAO;

	@FXML
	Button buy;
	@FXML
	ComboBox<String> monthsPicker;
	@FXML
	TextField price;
	
	int mapId;
	TextFieldUtility utilities;
	
	public BuySubscriptionController(GcmDAO gcmDAO, int mapId) {
		this.gcmDAO = gcmDAO;
		this.mapId = mapId;
	}
	 

	public void buyListener() {	
		buy.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String monthPickerValue = monthsPicker.getSelectionModel().getSelectedItem();

	            	if(!monthPickerValue.isEmpty()) {
	            		// add the correct pruchase details
	            		PurchaseDetails purchaseDetails = new PurchaseDetails("1","2","3","4");
	            		// need to insert cityId to buy membership to
//	            		gcmDAO.purchaseMembership(Integer.parseInt(monthPickerValue), purchaseDetails);
	            	}
	            }
			})
		);
	}
	
	public void monthsPickerListener() {
		monthsPicker.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newMonth) {
				String currentPrice = "0";
				switch(newMonth) {
				  case "1":
					  currentPrice = "10$";
					  break;
				  case "2":
					  currentPrice = "10$";
					  break;
				  case "3":
					  currentPrice = "10$";
					  break;
				  case "4":
					  currentPrice = "10$";
					  break;
				  case "5":
					  currentPrice = "10$";
					  break;
				  case "6":
					  currentPrice = "10$";
					  break;
				  default:
				}
				price.setText(currentPrice);
			} 
		});
	}
		
	public void initMonthsPicker() {
		monthsPicker.setItems(FXCollections.observableArrayList("1","2","3","4","5","6"));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initMonthsPicker();
		monthsPickerListener();
		buyListener();
	}
}