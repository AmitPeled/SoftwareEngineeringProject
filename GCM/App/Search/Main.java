package Search;
	
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		 // constructing our scene
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("searchScene.fxml"));
		 Parent root = fxmlLoader.load();
		 Scene scene = new Scene( root );
		 
		 ListViewController controller = new ListViewController();
		 controller.initialize();
		 
		 // setting the stage
		 primaryStage.setScene( scene );
		 primaryStage.setTitle( "Search" );

		 primaryStage.show();
	}
	@FXML // fx:id="listViewItems"
	Pane secPane;
	public void loadListView (ActionEvent event) throws IOException {
		Pane newLoadedPane = FXMLLoader.load(getClass().getResource("listView.fxml"));
		secPane.getChildren().add(newLoadedPane); 
	} 
	public static void main(String[] args) {
		launch(args);
	}
}
