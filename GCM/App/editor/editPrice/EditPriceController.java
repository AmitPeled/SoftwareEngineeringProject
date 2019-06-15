package editor.editPrice;

import java.net.URL;
import java.util.ResourceBundle;

import dataAccess.contentManager.ContentManagerDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mainApp.GcmClient;
import utility.TextFieldUtility;

public class EditPriceController implements Initializable
{
	private ContentManagerDAO contentManagerDAO;
	
	@FXML
	TextField price;
	@FXML
	Button editPrice;
	int cityId;
	@FXML
	TextField errors;
	TextFieldUtility utilities;

	private GcmClient gcmClient; 
	
	public EditPriceController(GcmClient gcmClient,
			ContentManagerDAO contentManagerDAO, 
			int cityId, 
			TextFieldUtility utilities) {
		this.gcmClient = gcmClient;
		this.contentManagerDAO = contentManagerDAO;
		this.cityId = cityId;
		this.utilities = utilities;
	}
	 
	public void editPriceListener() {	
		editPrice.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	String newPriceStr = price.getText();
	            	if(newPriceStr != null && !newPriceStr.isEmpty()) {
	            		if(utilities.isNumeric(newPriceStr)) {
	            			errors.setVisible(false);
	            			double newPrice = Double.parseDouble(newPriceStr);
			            	contentManagerDAO.changeMapPrice(cityId, newPrice);
			            	gcmClient.back();
	            		}else {
	            			utilities.setErrors("Price should be numeric value!", errors);
	            		}
	            	}else {
	            		utilities.setErrors("Please fill all fields!", errors);
	            	}
	            	
	            }
			})
		);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		errors.setVisible(false);
		editPriceListener();
		
	}

	public void initalizeControl(int cityId) {
		this.cityId = cityId;
	}
}