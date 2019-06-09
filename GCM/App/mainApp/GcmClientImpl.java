package mainApp;

import java.util.Stack;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The GcmClient class's responsibility is encapsulating all the high-level JavaFX/FXML related 
 * actions into a simple interface that allows scene switching without using any JavaFX related
 * methods.
 * @author aagami
 *
 */
class GcmClientImpl implements GcmClient {
	
	private SceneManager manager;
	
	/**
	 * A reference to the primary stage (used to switch scenes)
	 */
	private Stage primaryStage;
	
	private Stack<Scene> scenesStack;
	
	public GcmClientImpl(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.scenesStack = new Stack<Scene>();
	}
	
	@Override
	public void setSceneManager(SceneManager manager) { this.manager = manager; }
	
	@Override
	public void switchScene(SceneNames targetScenes) {
		try {
			Scene scene = manager.getScene(targetScenes);
			primaryStage.setScene(scene);
			primaryStage.show();
			scenesStack.push(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
}
