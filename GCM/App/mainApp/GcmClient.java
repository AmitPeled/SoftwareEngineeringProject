package mainApp;

public interface GcmClient {

	/**
	 * Changes the view to the destination scene
	 * @param destinationScene an Enum of type App.mainApp.Scenes that describes the scene we switch to
	 */
	void switchScene(SceneNames destinationScene);

	/**
	 * Goes back to the previous scene
	 */
	void back();
}