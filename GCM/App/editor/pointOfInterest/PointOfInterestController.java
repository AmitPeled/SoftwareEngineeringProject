package editor.pointOfInterest;

import static java.lang.Math.toIntExact;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import dataAccess.users.PurchaseDetails;
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
	
	String selectedRadioBtn;
	RadioButton selectRadio;
	
	int mapId;
	
	public PointOfInterestController(GcmDAO gcmDAO, int mapId) {
		this.gcmDAO = gcmDAO;
		this.mapId = mapId;
	}
	public void initRadioButtons() {
		// Radio 1: disableYes.
		disableYes.setToggleGroup(disableOptions);
		disableYes.setSelected(true);
		
		// Radio 2: disableNo.
		disableNo.setToggleGroup(disableOptions);
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
	            	
	            	if(poiName != null && !poiName.isEmpty() && 
	            		poiXC != null && !poiXC.isEmpty() && 
        				poiYC != null && !poiYC.isEmpty() && 
        				poiType != null && !poiType.isEmpty() &&
						poiDescription != null && !poiDescription.isEmpty()) {
	            		
	            		Site site = new Site(mapId, poiName, poiDescription, new Coordinates(Float.parseFloat(poiXC),Float.parseFloat(poiYC)) );
	            		gcmDAO.addNewSiteToCity(mapId, site);
	            	}
	            }
			})
		);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pointOfInterestListener();		
	}

}
