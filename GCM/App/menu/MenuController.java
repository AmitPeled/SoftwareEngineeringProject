package menu;

import gcmDataAccess.GcmDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import mainApp.GcmClient;
import mainApp.SceneNames;
import queries.RequestState;
import userInfo.UserInfoImpl;

public final class MenuController {
	@FXML
	Button reportsBtn;
	@FXML
	Button addanewcityBtn;
	@FXML
	Button approvalreportsBtn;
	private GcmClient gcmClient;
	
	public MenuController(GcmClient gcmClient) {
		if(gcmClient == null) throw new IllegalArgumentException("gcmClient is null");
		this.gcmClient = gcmClient;
		checkPermissions();
	}
	
	public void checkPermissions() {
		RequestState state = new UserInfoImpl(new GcmDAO(), new GcmDAO()).getState();
		if(state.equals(RequestState.customer)) {
			reportsBtn.setVisible(false);
			addanewcityBtn.setVisible(false);
			approvalreportsBtn.setVisible(false);
		}
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
	
}
