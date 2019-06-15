package init.initializers;

import mainApp.GcmClient;
import search.ListViewController;

public final class SearchInitializar implements Initializer {

	ListViewController searchController;
	
	public SearchInitializar(GcmClient gcmClient) {
		searchController = new ListViewController(gcmClient,gcmClient.getDataAccessObject());
	}
	
	@Override
	public Object getController() { return searchController; }

	@Override
	public String getFxmlPath() { return "/fxml/search/SearchScene.fxml"; }

}
