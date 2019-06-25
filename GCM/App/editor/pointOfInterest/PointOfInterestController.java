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
import mainApp.GcmClient;
import maps.City;
import maps.Coordinates;
import maps.Site;
import utility.TextFieldUtility;

public class PointOfInterestController implements Initializable{
	private GcmDAO gcmDAO;
	
	@FXML
	TextField name;
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
	
	private String selectedRadioBtn;
	private RadioButton selectRadio;
	private Coordinates coordinates;
	private int cityId;
	private int siteId;
	private boolean disable;

	private GcmClient gcmClient;
	
	public PointOfInterestController(GcmClient gcmClient, GcmDAO gcmDAO, int siteId, int cityId, Coordinates coordinates, TextFieldUtility utilities) {
		this.gcmClient = gcmClient;
		this.gcmDAO = gcmDAO;
		this.siteId = siteId;
		this.cityId = cityId;
		this.utilities = utilities;
		this.coordinates = coordinates;
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
	            	String poiType = type.getText();
	            	
	            	String poiDescription = description.getText();
	            	selectRadio = (RadioButton) disableOptions.getSelectedToggle();
	            	selectedRadioBtn = selectRadio.getText();
	            		
	            	List<String> list = Arrays.asList(poiName, poiType, poiDescription);
	            	if(utilities.checkFilledFields(list)) {
            			errors.setVisible(false);
            			if(selectedRadioBtn.equals("disableYES")) {
		            		disable = true;
		            	}else {
		            		disable = false;
		            	}
	            		Site site = new Site(poiName, poiDescription, poiType, disable, coordinates);
	            		if(siteId != -1) {
		            		gcmDAO.addNewSiteToCity(cityId, site);
	            		}else {
	            			gcmDAO.UpdateSite(cityId, site);
	            		}
	            		System.out.println("Adding site["+coordinates.x+","+coordinates.y+"]: cityId: "+cityId+", name: "+poiName+", type: "+poiType+", description: "+poiDescription);
	            		gcmClient.back();
	            	}else {
	            		utilities.setErrors("Please fill all fields!", errors);
	            	}
	            }
			})
		);
	}
	@FXML
	public void onBackButton() {
		gcmClient.back(); 
	}
	public void init() {
		Site site = gcmDAO.getSiteById(siteId);

		if(cityId != -1 && site != null) {
			name.setText(site.getName());
			type.setText(site.getSiteType());
			description.setText(site.getDescription());
			if(site.isAccessibleForDisabled()) {
				disableYes.setSelected(true);
				disableNo.setSelected(false);
			}else {
				disableYes.setSelected(false);
				disableNo.setSelected(true);
			}
		}else {
			name.setText("");
			type.setText("");
			description.setText("");
			disableYes.setSelected(false);
			disableNo.setSelected(true);
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		errors.setVisible(false);
		pointOfInterestListener();		
	}
	
	public void initalizeControl(int cityId, int siteId, double widthLocation, double heightLocation) {
		this.cityId = cityId;
		this.siteId = siteId;
		this.coordinates = new Coordinates(widthLocation,heightLocation);
		init();
	}

}
