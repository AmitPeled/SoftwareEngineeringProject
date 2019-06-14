package search;

import mainApp.GcmClient;
import mainApp.SceneNames;
import queries.RequestState;

public class SearchModel {

	private GcmClient gcmClient;
	
	public SearchModel(GcmClient gcmClient) { 
		if(gcmClient == null) throw new IllegalArgumentException("GCM client is null");
		this.gcmClient = gcmClient; 
	}
	GcmClient getGCM(){
		return gcmClient;
	}
	public void switchScene(SceneNames scene) { gcmClient.switchScene(scene); }

	public void back() { gcmClient.back(); }
}

