package search;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class Data
{
	@FXML
    private AnchorPane mapItem;
	@FXML // fx:id="mapName"
    private TextField mapName;
	@FXML // fx:id="description"
    private TextField description;
	@FXML // fx:id="pointOfInterest"
    private TextField pointOfInterest;
	@FXML // fx:id="tours"
    private TextField tours;
	@FXML
	private Button goTo;
	
	
    public Data(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/search/mapItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
        	mapItem = fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(MapItem item)
    {
    	mapName.setText(item.getName());
        description.setText(item.getDescription());
        pointOfInterest.setText(item.getPointOfInterest());
        tours.setText(item.getTours());
        mapItem.setId(item.getId());
    }

    public AnchorPane getBox()
    {
        return mapItem;
    }
}