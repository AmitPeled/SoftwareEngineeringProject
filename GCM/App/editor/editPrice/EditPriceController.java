package editor.editPrice;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.contentManager.ContentManagerDAO;
import gcmDataAccess.GcmDAO;
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
	TextField oneTime;
	@FXML
	TextField firstMonth;
	@FXML
	TextField secondMonth;
	@FXML
	TextField thirdMonth;
	@FXML
	TextField fourthMonth;
	@FXML
	TextField fifthMonth;
	@FXML
	TextField sixMonth;
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
	            	String oneTimePrice = oneTime.getText();
	            	String firstMonthPrice = firstMonth.getText();
	            	String secondMonthPrice = secondMonth.getText();
	            	String thirdMonthPrice = thirdMonth.getText();
	            	String fourthMonthPrice = fourthMonth.getText();
	            	String fifthMonthPrice = fifthMonth.getText();
	            	String sixMonthPrice = sixMonth.getText();
	            	List<String> priceList = Arrays.asList(oneTimePrice, firstMonthPrice, 
	            			secondMonthPrice, thirdMonthPrice, 
	            			fourthMonthPrice, fifthMonthPrice, sixMonthPrice);
	            	
	            	if(utilities.checkFilledFields(priceList)) {
	            		
	            		String numericResult = utilities.areAllFieldsNumeric(priceList);
	            		if(!numericResult.equals("no")) {
	            			errors.setVisible(false);
	            			List<Double> listPricesDouble = new ArrayList<Double>();
	            			for (String price : priceList) {
		            			double newPrice = Double.parseDouble(price);
		            			listPricesDouble.add(newPrice);
							}
			            	//contentManagerDAO.editCityPrice(cityId, listPricesDouble);
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
	
	public void initPrices() {
		oneTime.setText("15");
		List<TextField> textFieldsList = Arrays.asList(firstMonth, secondMonth, thirdMonth, fourthMonth, fifthMonth, sixMonth);
		int i = 1;
		for (TextField textField : textFieldsList) {
			//now all relevant prices can be retrieved by cityObject.getPrices()
			String price = Double.toString(-1);//Double.toString(gcmClient.getDataAccessObject().getCityPrice(cityId, 1));
			textField.setText(price);
			i++;
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		errors.setVisible(false);
		editPriceListener();
		initPrices();
	}

	public void initalizeControl(int cityId) {
		this.cityId = cityId;
	}
}