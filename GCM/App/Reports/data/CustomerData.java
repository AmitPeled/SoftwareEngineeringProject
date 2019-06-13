package Reports.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import Reports.resultItems.CustomerItem;

public class CustomerData
{
	@FXML
    private AnchorPane anchorpane;
	@FXML // fx:id="name"
    private TextField name;
	@FXML // fx:id="phone"
    private TextField phone;
	@FXML // fx:id="email"
    private TextField email;
	@FXML // fx:id="email"
    private ListView<String> purchaseHistory;
	
    public CustomerData(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reports/CustomerItem.fxml"));
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

    public void setInfo(CustomerItem item)
    {
    	name.setText(item.getName());
    	phone.setText(item.getPhone());
    	email.setText(item.getEmail());
    	ObservableList<String> data = FXCollections.observableArrayList();
    	for (String currItem : item.getPurchaseHistory()) 
    	{ 
    		 data.add(currItem);
    	}
    	purchaseHistory.setItems(data);
    }

    public AnchorPane getBox()
    {
        return anchorpane;
    }
}