package menu;

import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.customer.PurchaseHistory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import mainApp.GcmClient;
import mainApp.SceneNames;
import queries.RequestState;

public final class MenuController implements Initializable {

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
		if (gcmClient == null)
			throw new IllegalArgumentException("gcmClient is null");
		this.gcmClient = gcmClient;
	}

	@FXML
	public void onSearchButton() {
		gcmClient.switchScene(SceneNames.SEARCH);
	}

	@FXML
	public void onReportsButton() {
		gcmClient.switchScene(SceneNames.REPORTS);
	}

	@FXML
	public void onAddNewCityButton() {
		gcmClient.switchScene(SceneNames.ADD_CITY);
	}

	@FXML
	public void onApprovalRequestsButton() {
		gcmClient.switchScene(SceneNames.APPROVAL_REPORTS);
	}

	@FXML
	public void onLogoutButton() {
		gcmClient.Logout();
	}

	@FXML
	public void onCustomerReportButton() {
		gcmClient.switchSceneToCustomerReport();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addanewcityBtn.setVisible(false);
		reportsBtn.setVisible(false);
		approvalReportsBtn.setVisible(false);
	}
	public void updateVisiblity() {
		addanewcityBtn.setVisible(false);
		reportsBtn.setVisible(false);
		approvalReportsBtn.setVisible(false);
		customerreportbtn.setVisible(true);

		RequestState userState = gcmClient.getUserInfo().getState();
		System.out.println(userState);
		if (userState.equals(RequestState.editor) || userState.equals(RequestState.contentManager)
				|| userState.equals(RequestState.generalManager) || userState.equals(RequestState.manager)) {
			addanewcityBtn.setVisible(true);
			reportsBtn.setVisible(true);
			approvalReportsBtn.setVisible(true);
		} else {
			// i know it stupid to do it like this but for now that what happens
			List<PurchaseHistory> purchaseHistories = gcmClient.getDataAccessObject().getPurchaseHistory();
			java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			java.sql.Date exprieCheck = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			exprieCheck = addDays(exprieCheck, 3);
			java.sql.Date endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			for (int i = 0; i < purchaseHistories.size(); i++) {
				endDate = purchaseHistories.get(i).getEndDate();
				if (endDate.after(todayDate) && endDate.before(exprieCheck)) {
					// popUp for customer that subsricptopn is about to end
					AlertBox.display("System message", "Your subsription is about to exprie");
					break;
				}

			}
		}
	}

	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}
}
