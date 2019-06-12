package editor.editPrice;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import dataAccess.contentManager.ContentManagerDAO;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EditPriceController implements Initializable
{
	private ContentManagerDAO contentManagerDAO;
	
	@FXML
	TextField price;
	@FXML
	Button editPrice;
	int mapId;
	@FXML
	TextField errors;
	
	public EditPriceController(ContentManagerDAO contentManagerDAO, int mapId) {
		this.contentManagerDAO = contentManagerDAO;
		this.mapId = mapId;
	}
	
	public void editPriceListener() {	
		editPrice.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	String newPriceStr = price.getText();
	            	if(newPriceStr != null && !newPriceStr.isEmpty()) {
	            		if(isNumeric(newPriceStr)) {
	            			errors.setVisible(false);
	            			double newPrice = Double.parseDouble(newPriceStr);
			            	System.out.println(newPrice);
			            	contentManagerDAO.changeMapPrice(mapId, newPrice);
	            		}else {
	            			setErrors("Price should be numeric value!");
	            		}
	            	}
	            	
	            }
			})
		);
	}
	public static boolean isNumeric(String str) { 
		  try {  
		    Float.parseFloat(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	public void setErrors(String error) {
		errors.setVisible(true);
		errors.setText(error);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		editPriceListener();
		
	}
}