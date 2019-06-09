package mainApp;


/**
 * The GcmClient class's responsibility is encapsulating all the high-level JavaFX/FXML related 
 * actions into a simple interface that allows scene switching without using any JavaFX related
 * methods.
 * @author aagami
 *
 */
public interface GcmClient {
	/**
	 * Before doing any action, the SceneManager dependency needs to be set (This is called delayed dependency injection).
	 * @param manager A SceneManager object reference that contains all the loaded scenes
	 */
	void setSceneManager(SceneManager manager);
	
	/**
	 * Changes the view to the destination scene
	 * @param destinationScene an Enum of type App.mainApp.Scenes that describes the scene we switch to
	 */
	void switchScene(SceneNames destinationScene);

	/**
	 * Goes back to the previous scene. Should be used in "Back" buttons where you don't necessarily know what the previous scene was.
	 */
	void back();

	/**
	 * Closes the application
	 */
	void shutdown();
}