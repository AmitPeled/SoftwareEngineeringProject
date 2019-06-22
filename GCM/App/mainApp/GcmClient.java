package mainApp;

import dataAccess.mapDownload.MapDownloader;
import gcmDataAccess.GcmDAO;
import userInfo.UserInfo;
import users.User;

/**
 * The GcmClient class's responsibility is encapsulating all the high-level
 * JavaFX/FXML related actions into a simple interface that allows scene
 * switching without using any JavaFX related methods.
 * 
 * @author aagami
 *
 */
public interface GcmClient {
	/**
	 * Used in Main once. Before doing any action, the SceneManager dependency needs
	 * to be set (This is called delayed dependency injection).
	 * 
	 * @param manager
	 *            A SceneManager object reference that contains all the loaded
	 *            scenes
	 */
	void setSceneManager(SceneManager manager);

	/**
	 * Changes the view to the destination scene
	 * 
	 * @param destinationScene
	 *            an Enum of type App.mainApp.Scenes that describes the scene we
	 *            switch to
	 */
	void switchScene(SceneNames destinationScene);

	/**
	 * Goes back to the previous scene. Should be used in "Back" buttons where you
	 * don't necessarily know what the previous scene was.
	 */
	void back();

	/**
	 * Closes the application
	 */
	void shutdown();

	/**
	 * Logs out of the user account and switches to the INTRO scene
	 */
	void Logout();

	/**
	 * Returns the UserInfo module used for login and user related data access
	 * 
	 * @return a UserInfo object reference
	 */
	UserInfo getUserInfo();

	/**
	 * Switches scene to the map display scene
	 * 
	 * @param mapId
	 *            the ID of the map in the database
	 */
	void loadMapDisplay(int mapId);

	/**
	 * Returns the GcmDAO object used for all server communications actions
	 * 
	 * @return a GcmDAO object reference
	 */
	GcmDAO getDataAccessObject();

	void switchSceneToAddSite(int cityId, double widthLocation, double heightLocation);

	void switchSceneToEditPrice(int cityId);

	void switchSceneToCustomerReport();

	MapDownloader getMapDownloader();

}