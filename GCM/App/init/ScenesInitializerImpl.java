package init;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import init.initializers.Initializer;
import init.initializers.LoginInitializer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import login.ForgotPasswordcontroller;
import login.ForgotUsernameController;
import login.LoginSceneController;
import mainApp.GcmClient;
import mainApp.IntroController;
import mainApp.SceneNames;
import menu.MenuController;

public final class ScenesInitializerImpl implements ScenesInitializer{
	
	private GcmClient gcmClient;
	private final EnumMap<SceneNames, Initializer> initializers;
	private final EnumMap<SceneNames, Scene> scenes;

	public ScenesInitializerImpl(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
		
		// Add all initializers
		initializers = new EnumMap<SceneNames,Initializer>(SceneNames.class);
		initializers.put(SceneNames.LOGIN, new LoginInitializer(gcmClient));
		initializers.put(SceneNames.INTRO, new IntroInitializer(gcmClient));
		initializers.put(SceneNames.MENU, new MenuInitializer(gcmClient));
		initializers.put(SceneNames.REGISTER, new RegisterInitializer(gcmClient));
		initializers.put(SceneNames.FORGOT_PASSWORD, new ForgotPasswordInitializer(gcmClient));
		initializers.put(SceneNames.FORGOT_USERNAME, new ForgotUsernameInitializer(gcmClient));

		scenes = new EnumMap<SceneNames, Scene>(SceneNames.class);
		populateScenesMap();
	}
	
	@Override
	public EnumMap<SceneNames, Scene> getScenes() { return scenes; }
	
	private void populateScenesMap() {
		for (SceneNames scene : SceneNames.values()) {
			scenes.put(scene, loadScene(scene));
		}
	}
	
	private Scene loadScene(SceneNames targetScene) {
		Scene scene = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(initializers.get(targetScene).getFxmlPath()));
			loader.setController(initializers.get(targetScene).getController());
			Parent root = loader.load();
			scene = new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scene;
	}
}
