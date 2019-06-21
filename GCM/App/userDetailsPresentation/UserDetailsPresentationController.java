package userDetailsPresentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.customer.PurchaseHistory;
import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import mainApp.GcmClient;
import users.User;

public class UserDetailsPresentationController implements Initializable {

	private User user;
	private List<PurchaseHistory> purchaseHistories;
	private GcmClient gcmClient;
	// private GcmDAO gcmDAO;

	@FXML
	private Text nametxt, lastnametxt, emailtxt, phonenumbertxt, usernametxt;
	@FXML
	private ListView<String> listviewLV;

	public UserDetailsPresentationController(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}

	@FXML
	public void onBackButton() {
		gcmClient.back();
	}

	public void setDeteails() {
		user = gcmClient.getUserInfo().getUserDetailes();
		nametxt.setText(user.getFirstName());
		lastnametxt.setText(user.getLastName());
		emailtxt.setText(user.getEmail());
		phonenumbertxt.setText(user.getPhoneNumber());
		usernametxt.setText(user.getUsername());

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
	}

	public void initalizeControl() {
		setDeteails();
	}

}
