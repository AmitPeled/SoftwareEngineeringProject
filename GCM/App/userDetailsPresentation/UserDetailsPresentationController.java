package userDetailsPresentation;

import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.customer.PurchaseHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mainApp.GcmClient;
import users.User;

public class UserDetailsPresentationController implements Initializable {

	private User user;
	private List<PurchaseHistory> purchaseHistories;
	private GcmClient gcmClient;
	//private GcmDAO gcmDAO;

	@FXML
	private TextField nametxt, lastnametxt, emailtxt, phonenumbertxt, usernametxt;
	@FXML
	private ListView<String> listviewLV;
	@FXML 
	private Button updatebtn;

	public UserDetailsPresentationController(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
	}
	
	@FXML
	public void onBackButton() {
		gcmClient.back();
	}
	
	@FXML
	public void onEditButton() {
		emailtxt.setEditable(true);
		phonenumbertxt.setEditable(true);
		updatebtn.setVisible(true);
	}
	
	@FXML void onUpdateButton() {
		emailtxt.setEditable(false);
		phonenumbertxt.setEditable(false);
		updatebtn.setVisible(false);
		
		//execution query and update deatails
		//update user and call set deatails!
		
	}
	

	public void setDeteails() {

		nametxt.setText(user.getFirstName());
		lastnametxt.setText(user.getLastName());
		emailtxt.setText(user.getEmail());
		phonenumbertxt.setText(user.getPhoneNumber());
		usernametxt.setText(user.getUsername());

		
		//suppose to call real data and initilaze like we do in garbageData function
		
		
		// List<PurchaseHistory> purchaseHistories = gcmDAO.getPurchaseHistory(user.getUsername());
		
		
		garbageData();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// setDeteails();
	}

	public void initalizeControl(User user) {
		this.user = user;
		setDeteails();
	}

	public void garbageData() {
		java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		PurchaseHistory history1 = new PurchaseHistory(startDate, startDate, 1);
		PurchaseHistory history2 = new PurchaseHistory(startDate, startDate, 2);
		PurchaseHistory history3 = new PurchaseHistory(startDate, startDate, 3);
		PurchaseHistory history4 = new PurchaseHistory(startDate, startDate, 4);

		ObservableList<String> list = FXCollections.observableArrayList(history1.toString(), history2.toString(), history3.toString(),
				history4.toString());

		listviewLV.setItems(list);
	}

}
