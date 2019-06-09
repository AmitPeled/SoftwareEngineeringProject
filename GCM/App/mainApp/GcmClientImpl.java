package mainApp;

import java.util.EnumMap;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

class GcmClientImpl implements GcmClient {
	/**
	 * Contains the mapping between scenes and their .fxml file path
	 */
	private EnumMap<SceneNames, String> fxmlFilePath;
	
	/**
	 * A reference to the primary stage (used to switch scenes)
	 */
	private Stage primaryStage;
	
	private Stack<Scene> scenesStack;
	
	public GcmClientImpl(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.fxmlFilePath = new EnumMap<SceneNames,String>(SceneNames.class);
		this.scenesStack = new Stack<Scene>();
		fxmlFilePath.put(SceneNames.INTRO, "/fxml/Intro.fxml");
		fxmlFilePath.put(SceneNames.LOGIN, "/fxml/login/LoginScene.fxml");
		fxmlFilePath.put(SceneNames.REGISTER, "/fxml/register/RegisterScene.fxml");
	}
	
	@Override
	public void switchScene(SceneNames destinationScenes) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxmlFilePath.get(destinationScenes)));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Object controller = loader.getController();
			if(controller instanceof Controller) ((Controller)controller).setClient(this);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
}
