package menu;

import javafx.fxml.FXML;
import mainApp.GcmClient;
import mainApp.SceneNames;

public final class MenuController {
	
	private GcmClient gcmClient;
	
	public MenuController(GcmClient gcmClient) {
		if(gcmClient == null) throw new IllegalArgumentException("gcmClient is null");
		this.gcmClient = gcmClient;
	}
	
	@FXML
	public void onSearchButton() { gcmClient.switchScene(SceneNames.SEARCH); }
	
	@FXML
	public void onReportsButton() { gcmClient.switchScene(SceneNames.REPORTS); }
	
	@FXML
	public void onAddNewCityButton() { return; }
	
	@FXML
	public void onLogoutButton() { gcmClient.Logout(); }
}
