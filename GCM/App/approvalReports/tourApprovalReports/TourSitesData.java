package approvalReports.tourApprovalReports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import maps.Site;
import maps.Tour;

import java.io.IOException;
import java.util.List;

public class TourSitesData
{
	private String id;
	@FXML
    private AnchorPane anchorPane;
	@FXML 
    private ListView<String> tourSites;

	
	 // pass search controller to constractor or GCM client
    public TourSitesData(){ 
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/search/tourSites.fxml"));
        fxmlLoader.setController(this);
        try
        {
        	anchorPane = fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(List<Site> sites)
    {
    	ObservableList<String> data = FXCollections.observableArrayList();
    	for (Site item : sites) 
    	{ 
    		 data.add(item.getDescription());
    	}
    	tourSites.setItems(data);
    }

    public AnchorPane getBox()
    {
        return anchorPane;
    }
}