package purchase;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mainApp.SceneNames;

public class PurchaseController implements Initializable {

	private PurchaseModel puchaseModel;
	private final int MAX_CVV_LENGTH = 3;
	private final int MAX_CREDIT_CARD_NUMBER = 16;

	/** happen when i will connect with gabri code **/

	// private UserInfoImpl userInfoImpl;
	// private User user = userInfoImpl.getUserDetailes();

	public PurchaseController(PurchaseModel model) {
		this.puchaseModel = model;
	}

	@FXML
	private ComboBox<String> yearPicker, mounthPicker;

	@FXML
	private TextField cardnumtxt, cvvtxt, nametxt, lastnametxt;

	ObservableList<String> Monthlist = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08",
			"09", "10", "11", "12");
	ObservableList<String> Yearlist = FXCollections.observableArrayList("19", "20", "21", "22", "23", "24", "25");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mounthPicker.setItems(Monthlist);
		yearPicker.setItems(Yearlist);
		addTextLimiter(cvvtxt, MAX_CVV_LENGTH);
		addTextLimiter(cardnumtxt, MAX_CREDIT_CARD_NUMBER);
		numericTextOnly(cvvtxt);
		numericTextOnly(cardnumtxt);

		/**
		 * setting values if user already purchase once check with amit about how he
		 * keep user data -> mabybe change date type we are saving in user?
		 **/
		// nametxt.setText(user.getFirstName());
		// lastnametxt.setText(user.getLastName());

	}

	public void back(ActionEvent event) {

		puchaseModel.back();
	}

	public void purchase(ActionEvent event) {
		System.out.println("Purchase Map!");
		// Need to validate all fileds of buyer.
		// need to send to mail note about purchase of the user buyer
		// need to update user payment details
		puchaseModel.switchScene(SceneNames.MENU);
	}

	public void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}
			}
		});
	}

	public void numericTextOnly(TextField tf) {

		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					tf.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

	}
}
