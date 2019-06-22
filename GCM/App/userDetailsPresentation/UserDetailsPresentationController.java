package userDetailsPresentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.customer.PurchaseHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mainApp.GcmClient;
import queries.RequestState;
import users.User;
import utility.TextFieldUtility;

public class UserDetailsPresentationController implements Initializable {

	private User                  user;
	private List<PurchaseHistory> purchaseHistories;
	private GcmClient             gcmClient;
	private final int             MAX_PASSWORD_LENGTH = 12;
	private final int             MIN_PASSWOR_LENGTH  = 6;
	private final int             MAX_USERNAME_LENGTH = 10;
	private final int             MIN_USERNAME_LENGTH = 4;
	private boolean               flag                = true;
	// private GcmDAO gcmDAO;

	@FXML
	private TextField        nametf, lastnametf, emailtf, phonenumbertf, usernametf;
	@FXML
	private PasswordField    passwordtf, confirmpasswordtf;
	@FXML
	private ListView<String> listviewLV;
	@FXML
	private Button           editbtn, updatebtn;
	@FXML
	private Label            passwordlabel, confirmpasswordlabel, changelabel;

	public UserDetailsPresentationController(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}

	@FXML
	public void onBackButton() {
		gcmClient.back();
	}

	@FXML
	public void onEditButton() {

		updatebtn.setVisible(true);
		emailtf.setEditable(true);
		phonenumbertf.setEditable(true);
		passwordlabel.setVisible(true);
		confirmpasswordlabel.setVisible(true);
		passwordtf.setVisible(true);
		confirmpasswordtf.setVisible(true);
		passwordtf.setEditable(true);
		confirmpasswordtf.setEditable(true);
		changelabel.setVisible(true);
		usernametf.setEditable(true);

	}

	@FXML
	public void onUpdateButton(ActionEvent event) {
		if (!TextFieldUtility.validEmail(emailtf.getText())) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
			        "invalid email address");
		} else if (!validpassword(passwordtf.getText())) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
			        "password length need to be between 6 to 10 characters");
		} else if (!validConfirmPassword(passwordtf.getText(), confirmpasswordtf.getText())) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
			        "password and confirm password dont match");
		} else if (!Validusername(usernametf.getText())) {
			TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event), "Form Error!",
			        "username length need to be between 4 to 10 characters");
		} else {

			// build new user and send and the new password
			User userToUpdate = new User(usernametf.getText(), user.getFirstName(), user.getLastName(),
			        emailtf.getText(), phonenumbertf.getText());

			// after check with amit about this funtions
			// need to send update in dataBase
			String newPassword = passwordtf.getText();
			if (!newPassword.equals("")) {
				if (gcmClient.getDataAccessObject().updateUser(userToUpdate,
				        newPassword) == RequestState.usernameAlreadyExists) {
					TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event),
					        "invalid username", "Sorry, Username already exist plaese chose another one");
					flag = false;
				}
			} else {
				// need to send update in dataBase
				if (gcmClient.getDataAccessObject().updateUser(userToUpdate) == RequestState.usernameAlreadyExists) {
					TextFieldUtility.ShowAlert(AlertType.ERROR, TextFieldUtility.getStageWindow(event),
					        "invalid username", "Sorry, Username" + " already exist plaese chose another one");
					flag = false;
				}
			}
			if (flag) {
				updatebtn.setVisible(false);
				emailtf.setEditable(false);
				phonenumbertf.setEditable(false);
				passwordlabel.setVisible(false);
				confirmpasswordlabel.setVisible(false);
				passwordtf.setVisible(false);
				confirmpasswordtf.setVisible(false);
				passwordtf.setEditable(false);
				confirmpasswordtf.setEditable(false);
				changelabel.setVisible(false);
				usernametf.setEditable(false);
			}

			flag = true;

		}

	}

	public void setDeteails() {
		user = gcmClient.getUserInfo().getUserDetailes();
		nametf.setText(user.getFirstName());
		lastnametf.setText(user.getLastName());
		emailtf.setText(user.getEmail());
		phonenumbertf.setText(user.getPhoneNumber());
		usernametf.setText(user.getUsername());

		List<PurchaseHistory> purchaseHistories = gcmClient.getDataAccessObject().getPurchaseHistory();
		List<String> cityPurchaseStrings = new ArrayList<>();
		purchaseHistories.forEach((purchaseHistory) -> {
			cityPurchaseStrings.add(purchaseHistory.toString());
			System.out.println(purchaseHistory.toString());
		});
		ObservableList<String> list = FXCollections.observableArrayList(cityPurchaseStrings);
		listviewLV.setItems(list);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// setDeteails();
		TextFieldUtility.numericTextOnly(phonenumbertf);
		TextFieldUtility.addTextLimiter(phonenumbertf, 10);
	}

	public void initalizeControl() {
		setDeteails();
	}

	public boolean validpassword(String password) {

		if (password.length() == 0) {
			return true;
		}

		if (password.length() > MAX_PASSWORD_LENGTH || password.length() < MIN_PASSWOR_LENGTH) {
			return false;
		}
		return true;
	}

	public boolean validConfirmPassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return false;
		}
		return true;
	}

	public boolean Validusername(String username) {
		if (username.length() > MAX_USERNAME_LENGTH || username.length() < MIN_USERNAME_LENGTH) {
			return false;
		}

		return true;
	}

}
