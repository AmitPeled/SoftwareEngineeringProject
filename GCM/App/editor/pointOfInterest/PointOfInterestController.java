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
import utility.TextFieldUtility;

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
	TextFieldUtility utilities;
	
	String selectedRadioBtn;
	RadioButton selectRadio;
	
	int cityId;
	boolean disable;
	
	public PointOfInterestController(GcmDAO gcmDAO, int cityId, TextFieldUtility utilities) {
		this.gcmDAO = gcmDAO;
		this.cityId = cityId;
		this.utilities = utilities;
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
	            	if(utilities.checkFilledFields(list)) {
		            	List<String> numericList = Arrays.asList(poiXC, poiYC);
	            		String result = utilities.areAllFieldsNumeric(numericList);
	            		if(result.equals("yes")) {
	            			errors.setVisible(false);
	            			if(selectedRadioBtn.equals("disableYES")) {
			            		disable = true;
			            	}else {
			            		disable = false;
			            	}
		            		Site site = new Site(poiName, poiDescription, poiType, disable, new Coordinates(Float.parseFloat(poiXC),Float.parseFloat(poiYC)) );
		            		gcmDAO.addNewSiteToCity(cityId, site);
	            		}else {
	            			utilities.setErrors(result + " should be a numeric value!", errors);
	            		}
	            	}else {
	            		utilities.setErrors("Please fill all fields!", errors);
	            	}
	            }
			})
		);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errors.setVisible(false);
		pointOfInterestListener();		
	}

}
