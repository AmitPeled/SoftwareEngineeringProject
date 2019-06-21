package search;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mainApp.GcmClient;
import queries.RequestState;

import java.io.IOException;

public class Data
{
	private String id;
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
	@FXML // fx:id="oneTimePurchase"
	private Button oneTimePurchase;
	@FXML // fx:id="goTo"
	private Button goTo;
	
	private ListViewController listViewController;
	Boolean permissionsForMap;
	
    public Data(ListViewController listViewController, RequestState userState, Boolean permissionsForMap){ 
        this.listViewController = listViewController;
        this.permissionsForMap = permissionsForMap;
        
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/search/mapItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
        	mapItem = fxmlLoader.load();
        	checkPermissions(userState);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void checkPermissions(RequestState userState) {	
    	goTo.setVisible(false);
		oneTimePurchase.setVisible(false);
		
		if(userState == RequestState.guest) {
			if(permissionsForMap) {
				oneTimePurchase.setVisible(true);
			}
		}else if(userState == RequestState.customer){
			// check if customer as subscription for this city
			if(permissionsForMap) {
				goTo.setVisible(true);
			}
		}else {
			goTo.setVisible(true);
		}
    }
    public void setInfo(MapItem item)
    {
    	id = item.getId();
    	mapName.setText(item.getName());
        description.setText(item.getDescription());
        pointOfInterest.setText(item.getPointOfInterest());
        tours.setText(item.getTours());
        goToMapListener();
    }
    
    public void oneTimePurchaseListener() { 
    	oneTimePurchase.setOnMouseClicked((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					listViewController.goToOneTimePurchase(Integer.parseInt(id));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}));
	}
	public void goToMapListener() { 
		goTo.setOnMouseClicked((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					listViewController.goToMap(Integer.parseInt(id));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}));
	}
    public AnchorPane getBox()
    {
        return mapItem;
    }
}