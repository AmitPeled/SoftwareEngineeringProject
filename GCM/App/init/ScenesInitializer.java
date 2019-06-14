package init;

import java.util.EnumMap;

import javafx.scene.Scene;
import mainApp.SceneNames;

/**
 * Initializes all the Scene objects and sets all their dependencies (fxml files, controllers...).
 * Produces a mapping between SceneNames and Scene objects.
 * @author aagami
 *
 */
public interface ScenesInitializer {

	/**
	 * Gets all the Scene objects for all the Scenes in the program
	 * @return an EnumMap (a dictionary where the key is an enum) with mapping between SceneNames and Scene objects
	 */
	EnumMap<SceneNames, Scene> getScenes();
	
	/**
	 * Gets all the Controller objects for all the Scenes in the program
	 * @return an EnumMap (a dictionary where the key is an enum) with mapping between SceneNames and Controller objects
	 */
	EnumMap<SceneNames, Object> getControllers();
}
