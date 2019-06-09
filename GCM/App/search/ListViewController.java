package Search;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import Search.CustomListCell;
import Search.MapItem;

public class ListViewController implements Initializable
{
	@FXML 
    private ListView<MapItem> listView;
    /**
	* @param url
	* @param rb
	**/
    @Override
	public void initialize(URL url, ResourceBundle rb) {
    	ObservableList<MapItem> data = FXCollections.observableArrayList();
        data.addAll(new MapItem("holland", "1", "2", "3"), new MapItem("london", "1", "2", "3"), new MapItem("jerusalem", "1", "2", "3"));

        listView.setCellFactory(new Callback<ListView<MapItem>, ListCell<MapItem>>() {
            @Override
            public ListCell<MapItem> call(ListView<MapItem> listView) {
                return new CustomListCell();
            }
        });

        listView.setItems(data);   
    }
}