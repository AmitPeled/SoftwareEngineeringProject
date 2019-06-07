package Search;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SearchResults extends Application {
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
		URL url = getClass().getResource("SearchResultsScene.fxml");
		AnchorPane pane = FXMLLoader.load( url );
		Scene scene = new Scene( pane );
        ObservableList<MapItem> data = FXCollections.observableArrayList();
        data.addAll(new MapItem("holland", "1", "2", "3"), new MapItem("london", "1", "2", "3"), new MapItem("jerusalem", "1", "2", "3"));

        final ListView<MapItem> listView = new ListView<MapItem>(data);
        listView.setCellFactory(new Callback<ListView<MapItem>, ListCell<MapItem>>() {
            @Override
            public ListCell<MapItem> call(ListView<MapItem> listView) {
                return new CustomListCell();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(listView);
        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.show();
    }

    



}