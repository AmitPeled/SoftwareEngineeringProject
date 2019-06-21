package menu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import mainApp.GcmClient;
import mainApp.SceneNames;
import queries.RequestState;
import userInfo.UserInfoImpl;
import users.User;

public final class MenuController implements Initializable{
	
	private GcmClient gcmClient;
	@FXML
	Button searchBtn;
	@FXML
	Button addanewcityBtn;
	@FXML
	Button reportsBtn;
	@FXML
	Button approvalReportsBtn;
	@FXML
	Button customerreportbtn;
	
	public MenuController(GcmClient gcmClient) {
		if(gcmClient == null) throw new IllegalArgumentException("gcmClient is null");
		this.gcmClient = gcmClient;
	}
	
	@FXML
	public void onSearchButton() { gcmClient.switchScene(SceneNames.SEARCH); }
	
	@FXML
	public void onReportsButton() { gcmClient.switchScene(SceneNames.REPORTS); }
	
	@FXML
	public void onAddNewCityButton() { gcmClient.switchScene(SceneNames.ADD_CITY); }
	
	@FXML
	public void onApprovalRequestsButton() {gcmClient.switchScene(SceneNames.APPROVAL_REPORTS);}
	
	@FXML
	public void onLogoutButton() { gcmClient.Logout(); }
	
	@FXML 
	public void onCustomerReportButton() { gcmClient.switchSceneToCustomerReport();}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addanewcityBtn.setVisible(false);
		reportsBtn.setVisible(false);
		approvalReportsBtn.setVisible(false);
	}
	public void updateVisiblity() {
		RequestState userState = gcmClient.getUserInfo().getState();
		if(userState.equals(RequestState.editor) || userState.equals(RequestState.contentManager) || userState.equals(RequestState.generalManager) || userState.equals(RequestState.manager)) {
			addanewcityBtn.setVisible(true);
			reportsBtn.setVisible(true);
			approvalReportsBtn.setVisible(true);
		}
	}
	
}
