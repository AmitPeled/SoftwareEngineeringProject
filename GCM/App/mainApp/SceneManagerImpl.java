package mainApp;

import java.util.EnumMap;

import approvalReports.ApprovalReportsController;
import javafx.scene.Scene;
import menu.MenuController;

final class SceneManagerImpl implements SceneManager {
	private EnumMap<SceneNames, Scene> scenes;
	private EnumMap<SceneNames, Object> controllers;
	
	public SceneManagerImpl(EnumMap<SceneNames, Scene> scenes, EnumMap<SceneNames, Object> controllers) {
		this.scenes = scenes;
		this.controllers = controllers;
	}
	
	/**
	 * Gets a JavaFX Scene object for the appropriate scene
	 * @param name The SceneNames enum representation of the desired Scene
	 * @return An initiated Scene object 
	 */
	@Override 
	public Scene getScene(SceneNames name) { 
		if(name == SceneNames.APPROVAL_REPORTS) {
			((ApprovalReportsController)controllers.get(name)).initialize();
		}else if(name == SceneNames.MENU) {
			((MenuController)controllers.get(name)).updateVisiblity();
		}
		return scenes.get(name); 
	}
	
	@Override
	public Object getController(SceneNames name) { return controllers.get(name); }
}
