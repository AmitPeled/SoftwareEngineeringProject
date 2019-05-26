package testApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class TestApplication extends Application{

	static final int SCREEN_WIDTH = 400;
	static final int SCREEN_HEIGHT = 400;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("GCM Debug App");
		Text helloWorldText = new Text("Hello World!");
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(helloWorldText);
		Scene scene = new Scene(stackPane,SCREEN_WIDTH,SCREEN_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
