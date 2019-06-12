package editor.addMap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import editor.FileChooserInit;
import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import maps.City;
import maps.Coordinates;
import maps.Map;
import search.MapItem;

public class AddMapController implements Initializable
{
	private GcmDAO gcmDAO;
	
	@FXML
	TextField mapName;
	@FXML
	TextField mapDescription;
	@FXML
	TextField height;
	@FXML
	TextField width;
	@FXML
	TextField xCor;
	@FXML
	TextField yCor;
	@FXML
	TextField errors;
	@FXML
	Button uploadMap;
	@FXML
	Button addMap;
	
	File file;
	int cityId;
	Image image;
	private FileChooser fileChooser;
	BufferedImage bufferedImage;
	
	public AddMapController(GcmDAO gcmDAO, int cityId) {
		this.gcmDAO = gcmDAO;
		fileChooser = new FileChooserInit().getFileChooser();
		this.cityId = cityId;

	}
	
	public void addMapListener() {	
		addMap.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String name = mapName.getText();
	            	String description = mapDescription.getText();
	            	String mapHeight = height.getText();
	            	String mapWidth = width.getText();
	            	String xCoordinates = xCor.getText();
	            	String yCoordinates = yCor.getText();
	            	
	            	List<String> list = Arrays.asList(name, description, mapHeight, mapWidth, xCoordinates, yCoordinates);;
	            	if(checkFilledFields(list)) {
	            		List<String> numericList = Arrays.asList(mapHeight, mapWidth, xCoordinates, yCoordinates);
	            		String checkResult = areAllFieldsNumeric(numericList);
	            		if(checkResult.equals("yes")) {

		            		if(file != null) {
		            			errors.setVisible(false);
			            		Map newMap = new Map(name, description, Float.parseFloat(mapWidth), Float.parseFloat(mapHeight), new Coordinates(Float.parseFloat(xCoordinates), Float.parseFloat(yCoordinates)));
			            		gcmDAO.addMapToCity(cityId, newMap, file);
		            		
		            		}else {
		            			setErrors("No file added");
		            		}
	            		}else {
	            			setErrors(checkResult + " is not numeric value!");
	            		}
	            		
	            	}else {
	            		setErrors("Please fill all the fields");
	            	}
	            }
			})
		);
	}
	public boolean checkFilledFields(List<String> list) {
		for (String item : list) {
			if(item == null || item.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	public String areAllFieldsNumeric(List<String> list) {
		for (String item : list) {
			if(!isNumeric(item)) {
				return item;
			}
		}
		return "yes";
	}
	public void setErrors(String error) {
		errors.setVisible(true);
		errors.setText(error);
	}
	public static boolean isNumeric(String str) { 
		  try {  
		    Float.parseFloat(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	public void uploadMapListener() {	
		uploadMap.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) { 
	            	 //Show open file dialog
	                file = fileChooser.showOpenDialog(null);
	                if(file != null) {
	                	try {
							bufferedImage = ImageIO.read(file);
		                    image = SwingFXUtils.toFXImage(bufferedImage, null);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            }
			})
		);
	}
	
    /**
	* @param url
	* @param rb
	**/
    @Override 
	public void initialize(URL url, ResourceBundle rb) {
    	addMapListener();
    	uploadMapListener();
    }
}