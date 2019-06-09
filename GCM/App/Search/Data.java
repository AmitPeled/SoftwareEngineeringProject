package search;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class Data
{
	@FXML
    private AnchorPane anchorpane;
	@FXML // fx:id="mapName"
    private TextField mapName;
	@FXML // fx:id="description"
    private TextField description;
	@FXML // fx:id="pointOfInterest"
    private TextField pointOfInterest;
	@FXML // fx:id="tours"
    private TextField tours;

    public Data(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mapItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
        	anchorpane = fxmlLoader.load();
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
    }

    public AnchorPane getBox()
    {
        return anchorpane;
    }
}