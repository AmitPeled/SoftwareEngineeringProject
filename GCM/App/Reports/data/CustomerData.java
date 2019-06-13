package Reports.data;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

	
    public CustomerData(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/search/CustomerItem.fxml"));
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
    }

    public AnchorPane getBox()
    {
        return anchorpane;
    }
}