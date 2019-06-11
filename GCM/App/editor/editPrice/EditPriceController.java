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
	
	public EditPriceController( int mapId) {
//		this.contentManagerDAO = contentManagerDAO;
		this.mapId = mapId;
	}
	
	public void editPriceListener() {	
		editPrice.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	String newPriceStr = price.getText();
	            	if(newPriceStr != null && !newPriceStr.isEmpty()) {
		            	double newPrice = Double.parseDouble(newPriceStr);;
		            	//contentManagerDAO.changeMapPrice(mapId, newPrice);
	            	}
	            	
	            }
			})
		);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		editPriceListener();
		
	}
}