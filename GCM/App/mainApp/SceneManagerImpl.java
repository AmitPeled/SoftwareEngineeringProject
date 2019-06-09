package mainApp;

import java.util.EnumMap;

import javafx.scene.Scene;

final class SceneManagerImpl implements SceneManager {
	private EnumMap<SceneNames, Scene> scenes;
	
	public SceneManagerImpl(EnumMap<SceneNames, Scene> scenes) {
		this.scenes = scenes;
	}
	
	/**
	 * Gets a JavaFX Scene object for the appropriate scene
	 * @param name The SceneNames enum representation of the desired Scene
	 * @return An initiated Scene object 
	 */
	@Override
	public Scene getScene(SceneNames name) { return scenes.get(name); }
}
