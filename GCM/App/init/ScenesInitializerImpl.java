package init;

import java.io.IOException;
import java.util.EnumMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import login.ForgotPasswordcontroller;
import login.ForgotUsernameController;
import login.LoginSceneController;
import mainApp.GcmClient;
import mainApp.IntroController;
import mainApp.SceneNames;
import register.RegisterSceneController;

public final class ScenesInitializerImpl implements ScenesInitializer{
	
	private GcmClient gcmClient;
	private final EnumMap<SceneNames, String> fxmlFilePath;
	private final EnumMap<SceneNames, Scene> scenes;
	private final EnumMap<SceneNames, Object> controllers;
	
	public ScenesInitializerImpl(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
		scenes = new EnumMap<SceneNames, Scene>(SceneNames.class);
		fxmlFilePath = new EnumMap<SceneNames, String>(SceneNames.class);
		controllers = new EnumMap<SceneNames,Object>(SceneNames.class);
		populateFxmlFilesMap();
		initControllers();
		populateScenesMap();
	}
	
	@Override
	public Scene getScene(SceneNames name) { return scenes.get(name); }
	
	private void populateFxmlFilesMap(){
		fxmlFilePath.put(SceneNames.INTRO, "/fxml/Intro.fxml");
		fxmlFilePath.put(SceneNames.LOGIN, "/fxml/login/LoginScene.fxml");
		fxmlFilePath.put(SceneNames.REGISTER, "/fxml/register/RegisterScene.fxml");
		fxmlFilePath.put(SceneNames.FORGOT_PASSWORD, "/fxml/login/ForgotPasswordScene.fxml");
		fxmlFilePath.put(SceneNames.FORGOT_USERNAME, "/fxml/login/ForgotUsernameScene.fxml");
	}
	
	private void initControllers() {
		controllers.put(SceneNames.INTRO,           new IntroController(gcmClient));
		controllers.put(SceneNames.LOGIN,           new LoginSceneController(gcmClient));
		controllers.put(SceneNames.REGISTER,        new RegisterSceneController(gcmClient));
		controllers.put(SceneNames.FORGOT_PASSWORD, new ForgotPasswordcontroller(gcmClient));
		controllers.put(SceneNames.FORGOT_USERNAME, new ForgotUsernameController(gcmClient));	
	}
	
	private void populateScenesMap() {
		for (SceneNames scene : SceneNames.values()) {
			scenes.put(scene, loadScene(scene, controllers.get(scene)));
		}
	}
	
	private Scene loadScene(SceneNames targetScene, Object controller) {
		Scene scene = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxmlFilePath.get(targetScene)));
			loader.setController(controller);
			Parent root = loader.load();
			scene = new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scene;
	}
}
