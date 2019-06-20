package mainApp;

import java.util.Stack;

import editor.editPrice.EditPriceController;
import editor.pointOfInterest.PointOfInterestController;
import gcmDataAccess.GcmDAO;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mapViewerScene.MapViewerSceneController;
import userDetailsPresentation.UserDetailsPresentationController;
import userInfo.UserInfo;
import users.User;

/**
 * The GcmClient class's responsibility is encapsulating all the high-level JavaFX/FXML related 
 * actions into a simple interface that allows scene switching without using any JavaFX related
 * methods.
 * @author aagami
 *
 */
class GcmClientImpl implements GcmClient {
	
	private static final String gcmAppTitle = "GCM";
	private final UserInfo userInfo;
	private final GcmDAO dataAccess;
	private SceneManager manager;
	
	/**
	 * A reference to the primary stage (used to switch scenes)
	 */
	private Stage primaryStage;
	
	private Stack<Scene> scenesStack;
	
	public GcmClientImpl(Stage primaryStage, UserInfo userInfo,GcmDAO dataAccess) {
		this.primaryStage = primaryStage;
		this.scenesStack = new Stack<Scene>();
		this.userInfo = userInfo;
		this.dataAccess = dataAccess;
	}
	
	@Override
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	@Override
	public void setSceneManager(SceneManager manager) { this.manager = manager; }
	
	@Override
	public void switchScene(SceneNames targetScenes) {
		try {
			Scene scene = manager.getScene(targetScenes);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle(gcmAppTitle);
			scenesStack.push(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void loadMapDisplay(int mapId) {
		System.out.println("Switching scene to map viewer with map id: " + mapId);
		int cityId = dataAccess.getCityByMapId(mapId).getId();
		Scene scene = MapViewerSceneController.getMapViewerScene(this, mapId, cityId);
		primaryStage.setScene(scene);
		scenesStack.push(scene);
	}
	
	/**
	 * Goes back to the previous scene
	 */
	@Override
	public void back() {
		scenesStack.pop();
		primaryStage.setScene(scenesStack.peek());
		primaryStage.show();
	}
	
	@Override
	public void shutdown() {
		Platform.exit();
	}
	
	@Override
	public void Logout() {
		//TODO - logout related stuff goes here
		switchScene(SceneNames.INTRO);
	}
	
	@Override
	public GcmDAO getDataAccessObject() { return dataAccess; }

	@Override
	public void switchSceneToAddSite(int mapId,double widthLocation, double heightLocation) {
		Scene scene = manager.getScene(SceneNames.ADD_SITE);
		try {
			PointOfInterestController controller = (PointOfInterestController)manager.getController(SceneNames.ADD_SITE);
			controller.initalizeControl(mapId,widthLocation,heightLocation);
			primaryStage.setScene(scene);
			scenesStack.push(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	@Override
	public void switchSceneToEditPrice(int cityId) {
		Scene scene = manager.getScene(SceneNames.EDIT_PRICE);
		try {
			EditPriceController controller = (EditPriceController)manager.getController(SceneNames.EDIT_PRICE);
			controller.initalizeControl(cityId);
			primaryStage.setScene(scene);
			scenesStack.push(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void switchSceneToCustomerReport() {
		
		Scene scene = manager.getScene(SceneNames.CUSTOMER_REPORT);
		try {
			UserDetailsPresentationController controller = (UserDetailsPresentationController) manager.getController(SceneNames.CUSTOMER_REPORT);
			controller.initalizeControl();
			primaryStage.setScene(scene);
			scenesStack.push(scene);
		}catch (Exception e) {
		
			e.printStackTrace();
		}
		
	} 

}

