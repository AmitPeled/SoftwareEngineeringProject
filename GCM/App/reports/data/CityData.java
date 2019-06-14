package reports.data;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import reports.resultItems.CityItem;

import java.io.IOException;

public class CityData
{
	@FXML
	private AnchorPane anchorpane;
	@FXML // fx:id="numberOfMaps"
    private TextField numberOfMaps;
	@FXML // fx:id="oneTimeSubscribers"
    private TextField oneTimeSubscribers;
	@FXML // fx:id="subscribers"
    private TextField subscribers;
	@FXML // fx:id="renewableSubscriptions"
    private TextField renewableSubscriptions;
	@FXML // fx:id="downloads"
    private TextField downloads;
	
    public CityData(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reports/CityItem.fxml"));
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

    public void setInfo(CityItem item)
    {
    	numberOfMaps.setText(item.getNumberOfMaps());
    	oneTimeSubscribers.setText(item.getDescription());
    	subscribers.setText(item.getSubscribers());
    	renewableSubscriptions.setText(item.getRenewableSubscriptions());
    	downloads.setText(item.getDownloads());
    }

    public AnchorPane getBox()
    {
        return anchorpane;
    }
}