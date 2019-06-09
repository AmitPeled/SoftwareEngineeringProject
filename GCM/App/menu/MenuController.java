package menu;

import javafx.fxml.FXML;
import mainApp.GcmClient;

public final class MenuController {
	
	private GcmClient gcmClient;
	
	public MenuController(GcmClient gcmClient) {
		if(gcmClient == null) throw new IllegalArgumentException("gcmClient is null");
		this.gcmClient = gcmClient;
	}
	
	@FXML
	public void onSearchButton() { return; }
	
	@FXML
	public void onReportsButton() { return; }
	
	@FXML
	public void onAddNewCityButton() { return; }
	
	@FXML
	public void onLogoutButton() { gcmClient.Logout(); }
}
