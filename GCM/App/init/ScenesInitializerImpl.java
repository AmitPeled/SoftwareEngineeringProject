package init;

import java.io.IOException;
import java.util.EnumMap;

import init.initializers.*;
import init.initializers.editor.AddCityInitializer;
import init.initializers.editor.AddMapInitializer;
import init.initializers.editor.AddSiteInitializer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import mainApp.GcmClient;
import mainApp.SceneNames;

public final class ScenesInitializerImpl implements ScenesInitializer{
	
	private final GcmClient gcmClient;
	private final EnumMap<SceneNames, Initializer> initializers;
	private final EnumMap<SceneNames, Scene> scenes;
	private EnumMap<SceneNames, Object> controllers;

	public ScenesInitializerImpl(GcmClient gcmClient) {
		this.gcmClient = gcmClient;
		initializers = new EnumMap<SceneNames,Initializer>(SceneNames.class);
		scenes = new EnumMap<SceneNames, Scene>(SceneNames.class);
		controllers = new EnumMap<SceneNames,Object>(SceneNames.class);
		
		populateInitializersMap();
		populateScenesMap();
		populateControllersMap();
	}

	private void populateControllersMap() {
		for (SceneNames scene : SceneNames.values()) {
			controllers.put(scene,initializers.get(scene).getController());
		}
	}

	private void populateInitializersMap() {
		initializers.put(SceneNames.INTRO, new IntroInitializer(gcmClient));
		initializers.put(SceneNames.MENU, new MenuInitializer(gcmClient));
		initializers.put(SceneNames.LOGIN, new LoginInitializer(gcmClient));
		initializers.put(SceneNames.REGISTER, new RegisterInitializer(gcmClient));
		initializers.put(SceneNames.FORGOT_PASSWORD, new ForgotPasswordInitializer(gcmClient));
		initializers.put(SceneNames.FORGOT_USERNAME, new ForgotUsernameInitializer(gcmClient));
		initializers.put(SceneNames.PURCHASE, new PurchaseInitializer(gcmClient));
		initializers.put(SceneNames.SEARCH, new SearchInitializar(gcmClient));
		initializers.put(SceneNames.REPORTS, new ReportsInitializer(gcmClient));
		initializers.put(SceneNames.ADD_CITY, new AddCityInitializer(gcmClient));
		initializers.put(SceneNames.ADD_MAP, new AddMapInitializer(gcmClient));
		initializers.put(SceneNames.ADD_SITE, new AddSiteInitializer(gcmClient));
		initializers.put(SceneNames.EDIT_PRICE, new EditPriceInitializer(gcmClient));
		initializers.put(SceneNames.APPROVAL_REPORTS, new ApprovalReportsInitializer(gcmClient));
		initializers.put(SceneNames.CUSTOMER_REPORT, new CustomerReportInitializer(gcmClient));
	}
	
	@Override
	public EnumMap<SceneNames, Scene> getScenes() { return scenes; }
	
	@Override
	public EnumMap<SceneNames, Object> getControllers() {return controllers; }
	
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
