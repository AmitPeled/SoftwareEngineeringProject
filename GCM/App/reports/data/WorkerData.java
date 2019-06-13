package reports.data;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import reports.resultItems.WorkerItem;

import java.io.IOException;

public class WorkerData
{
	@FXML
    private AnchorPane anchorpane;
	@FXML // fx:id="firstName"
    private TextField firstName;
	@FXML // fx:id="lastName"
    private TextField lastName;
	@FXML // fx:id="employeeId"
    private TextField employeeId;
	@FXML // fx:id="email"
    private TextField email;
	@FXML // fx:id="jobDescription"
    private TextField jobDescription;
	
	
    public WorkerData(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reports/WorkerItem.fxml"));
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

    public void setInfo(WorkerItem item)
    {
    	firstName.setText(item.getFirstName());
    	lastName.setText(item.getLastName());
    	employeeId.setText(item.getEmployeeId());
    	email.setText(item.getEmail());
    	jobDescription.setText(item.getJobDescription());
    }

    public AnchorPane getBox()
    {
        return anchorpane;
    }
}