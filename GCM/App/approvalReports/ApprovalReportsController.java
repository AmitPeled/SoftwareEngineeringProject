package approvalReports;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.cityApprovalReports.CityTableCell;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.sitesApprovalReports.SiteTableCell;
import gcmDataAccess.GcmDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import maps.City;
import maps.Coordinates;
import maps.Site;
import maps.Tour;
import search.CustomListCell;
import search.MapItem;
import utility.TextFieldUtility;

public class ApprovalReportsController  implements Initializable {
	private GcmDAO gcmDAO;
	@FXML
	TableView<SiteSubmission> siteTable;
	@FXML
	TableColumn<SiteSubmission, String> siteName;
	@FXML
	TableColumn<SiteSubmission, String> siteDescription;
	@FXML
	TableColumn<SiteSubmission, String> siteType;
	@FXML
	TableColumn<SiteSubmission, String> siteActionTaken;
	@FXML
	TableColumn<SiteSubmission, Button> siteApprovalDisapproval;
	
	@FXML
	TableView<CitySubmission> cityTable;
	@FXML
	TableColumn<CitySubmission, String> cityName;
	@FXML
	TableColumn<CitySubmission, String> cityDescription;
	@FXML
	TableColumn<CitySubmission, String> cityActionTaken;
	@FXML
	TableColumn<CitySubmission, Button> cityApprovalDisapproval;
	
	@FXML
	Button cityReports;
	@FXML
	Button siteReports;
	
	private List<CitySubmission> citySubmissions;
	private List<SiteSubmission> siteSubmissions;
	
	public ApprovalReportsController(GcmDAO gcmDAO, List<CitySubmission> citySubmissions, List<SiteSubmission> siteSubmissions) {
		this.gcmDAO = gcmDAO;
		this.citySubmissions = citySubmissions;
		this.siteSubmissions = siteSubmissions;
	}
	
	public void initSiteTableView() {
	        siteName.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getSite().getName()));
	        siteDescription.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getSite().getDescription()));
	        siteType.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getSite().getSiteType()));
	        siteActionTaken.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getActionTaken()));
	        
	        siteApprovalDisapproval.setCellFactory(new Callback<TableColumn<SiteSubmission, Button>, TableCell<SiteSubmission, Button>>() {
	            @Override
	            public TableCell<SiteSubmission, Button> call(TableColumn<SiteSubmission, Button> param) {
	                return new SiteTableCell();
	            }
	        });
	        
	        
	        ObservableList<SiteSubmission> details = FXCollections.observableArrayList(siteSubmissions);
	        siteTable.setItems(details);
	}
	public void initCityTableView() {
        cityName.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getCity().getName()));
        cityDescription.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getCity().getDescription()));
        cityActionTaken.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getActionTaken()));
        
        cityApprovalDisapproval.setCellFactory(new Callback<TableColumn<CitySubmission, Button>, TableCell<CitySubmission, Button>>() {
            @Override
            public TableCell<CitySubmission, Button> call(TableColumn<CitySubmission, Button> param) {
                return new CityTableCell();
            }
        });
        
        
        ObservableList<CitySubmission> details = FXCollections.observableArrayList(citySubmissions);
        cityTable.setItems(details);
}
	public void cityBtnListener() {
		cityReports.setOnMouseClicked((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				cityTable.setVisible(true);
				siteTable.setVisible(false);
			}
			
		}));
	}
	public void siteBtnListener() {
		siteReports.setOnMouseClicked((new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent arg0) {
				siteTable.setVisible(true);
				cityTable.setVisible(false);
			}
			
		}));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCityTableView();
		initSiteTableView();
		siteBtnListener();
		cityBtnListener();
	}

}
