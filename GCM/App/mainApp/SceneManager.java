package mainApp;

import javafx.scene.Scene;

interface SceneManager {

	/**
	 * Gets a JavaFX Scene object for the appropriate scene
	 * @param name The SceneNames enum representation of the desired Scene
	 * @return An initiated Scene object 
	 */
	Scene getScene(SceneNames name);

	Scene getMapViewerScene(int mapId);

}