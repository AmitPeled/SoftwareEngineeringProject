package editor.pointOfInterest;


import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import gcmDataAccess.GcmDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import maps.Coordinates;
import maps.Site;

public class PointOfInterestController implements Initializable{
	private GcmDAO gcmDAO;
	
	@FXML
	TextField name;
	@FXML
	TextField xC;
	@FXML
	TextField yC;
	@FXML
	TextField type;
	@FXML
	TextField description;
	@FXML
	RadioButton disableYes;
	@FXML
	RadioButton disableNo;
	@FXML
	Button addEdit;
	@FXML
	public ToggleGroup disableOptions;
	@FXML
	TextField errors;
	
	String selectedRadioBtn;
	RadioButton selectRadio;
	
	int cityId;
	boolean disable;
	
	public PointOfInterestController(GcmDAO gcmDAO, int cityId) {
		this.gcmDAO = gcmDAO;
		this.cityId = cityId;
	}
	public void initRadioButtons() {
		// Radio 1: disableYes.
		disableYes.setToggleGroup(disableOptions);
		
		
		// Radio 2: disableNo.
		disableNo.setToggleGroup(disableOptions);
		disableNo.setSelected(true);
	}
	public void pointOfInterestListener() {	
		addEdit.setOnMouseClicked((new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	String poiName = name.getText();
	            	String poiXC = xC.getText();
	            	String poiYC = yC.getText();
	            	String poiType = type.getText();
	            	String poiDescription = description.getText();
	            	selectRadio = (RadioButton) disableOptions.getSelectedToggle();
	            	selectedRadioBtn = selectRadio.getText();
	            		
	            	List<String> list = Arrays.asList(poiName, poiXC, poiYC, poiType, poiDescription);
	            	if(checkFilledFields(list)) {
		            	List<String> numericList = Arrays.asList(poiXC, poiYC);
	            		String result = areAllFieldsNumeric(numericList);
	            		if(result.equals("yes")) {
	            			if(selectedRadioBtn.equals("disableYES")) {
			            		disable = true;
			            	}else {
			            		disable = false;
			            	}
		            		Site site = new Site(poiName, poiDescription, poiType, disable, new Coordinates(Float.parseFloat(poiXC),Float.parseFloat(poiYC)) );
		            		gcmDAO.addNewSiteToCity(cityId, site);
	            		}else {
	            			errors.setText(result + " should be a numeric value!");
	            		}
	            	}else {
	            		errors.setText("Please fill all fields!");
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
	public static boolean isNumeric(String str) { 
		  try {  
		    Float.parseFloat(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pointOfInterestListener();		
	}

}
