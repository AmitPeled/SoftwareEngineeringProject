package editor.buySubscription;

import java.net.URL;
import java.util.ResourceBundle;

import dataAccess.users.PurchaseDetails;
import gcmDataAccess.GcmDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import utility.TextFieldUtility;


public class BuySubscriptionController implements Initializable
{
	private GcmDAO gcmDAO;

	@FXML
	Button buy;
	@FXML
	ComboBox<String> monthsPicker;
	@FXML
	TextField price;
	
	int cityId;
	int mapId;
	TextFieldUtility utilities;
	
	public BuySubscriptionController(GcmDAO gcmDAO, int mapId, int cityId) {
		this.gcmDAO = gcmDAO;
		this.mapId = mapId;
		this.cityId = cityId;
	}
	 

	public void buyListener() {	
		buy.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String monthPickerValue = monthsPicker.getSelectionModel().getSelectedItem();

	            	if(!monthPickerValue.isEmpty()) {
	            		String username = gcmDAO.getUserDetails().getUsername();
	            		//gcmDAO.repurchaseMembershipBySavedDetails(cityId, Integer.parseInt(monthPickerValue), username);
	            	}
	            }
			})
		);
	} 
	
	public void monthsPickerListener() {
		monthsPicker.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newMonth) {
				String updatedPrice = Double.toString(gcmDAO.getMembershipPrice(cityId, Integer.parseInt(newMonth)));
				price.setText(updatedPrice);
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