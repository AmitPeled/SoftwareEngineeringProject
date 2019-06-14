package utility;

import java.util.List;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class TextFieldUtility {

	/**
	 * validate if email addres is legal
	 * 
	 * @param email
	 *            - String
	 * @return true if email is legel false otherwise
	 */
	public static boolean validEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	} 

	/** 
	 * set limit length to text field object
	 * 
	 * @param textField
	 *            TextField refrence
	 * @param maxLength
	 */
	public static void addTextLimiter(final TextField textField, final int maxLength) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (textField.getText().length() > maxLength) {
					String s = textField.getText().substring(0, maxLength);
					textField.setText(s);
				}
			}
		});
	}

	/**
	 * set TextField object to have numeric char only
	 * 
	 * @param textField
	 */ 
	public static void numericTextOnly(TextField textField) {

		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					textField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});   

	}
	
	/**
	 * poo up of alert box
	 * 
	 * @param alertType
	 * @param owner
	 * @param title
	 * @param message
	 *            when called pop to the screen alertbox
	 */
	public static void ShowAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();

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
	public boolean isNumeric(String str) { 
		  try {  
		    Float.parseFloat(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	public void setErrors(String error, TextField errors) {
		errors.setVisible(true);
		errors.setText(error);
	}

	/**
	 * give you current window of actionEvent screen
	 * 
	 * @param event
	 * @return Window
	 * 
	 */
	public static Window getStageWindow(ActionEvent event) {
		Node source = (Node) event.getSource();
		Window theStage = source.getScene().getWindow();
		return theStage;
	}

}



