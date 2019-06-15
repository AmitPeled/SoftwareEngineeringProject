package approvalReports;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.cityApprovalReports.CityTableCell;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.sitesApprovalReports.SiteTableCell;
import approvalReports.tourApprovalReports.TourSubmission;
import approvalReports.tourApprovalReports.TourTableCell;
import gcmDataAccess.GcmDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import mainApp.GcmClient;

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
	TableView<TourSubmission> tourTable;
	@FXML
	TableColumn<TourSubmission, String> tourDescription;
	@FXML
	TableColumn<TourSubmission, String> tourActionTaken;
	@FXML
	TableColumn<TourSubmission, Button> tourApprovalDisapproval;
	
	@FXML
	Button cityReports;
	@FXML
	Button siteReports;
	@FXML
	Button tourReports;
	
	private List<CitySubmission> citySubmissions;
	private List<SiteSubmission> siteSubmissions;
	private List<TourSubmission> tourSubmission;

	public ApprovalReportsController(GcmClient gcmClient,
			GcmDAO gcmDAO, 
			List<CitySubmission> citySubmissions, 
			List<SiteSubmission> siteSubmissions, 
			List<TourSubmission> tourSubmission) {
		this.gcmDAO = gcmDAO;
		this.citySubmissions = citySubmissions;
		this.siteSubmissions = siteSubmissions;
		this.tourSubmission = tourSubmission;
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
	public void initTourTableView() {
        
		tourDescription.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getTour().getDescription()));
        tourActionTaken.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getActionTaken()));

        tourApprovalDisapproval.setCellFactory(new Callback<TableColumn<TourSubmission, Button>, TableCell<TourSubmission, Button>>() {
            @Override
            public TableCell<TourSubmission, Button> call(TableColumn<TourSubmission, Button> param) {
                return new TourTableCell();
            }
        });

        ObservableList<TourSubmission> details = FXCollections.observableArrayList(tourSubmission);
        tourTable.setItems(details);
	}
	
	
	public void cityBtnListener() {
		cityReports.setOnMouseClicked((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				cityTable.setVisible(true);
				siteTable.setVisible(false);
				tourTable.setVisible(false);
			}
			
		}));
	}
	public void siteBtnListener() {
		siteReports.setOnMouseClicked((new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent arg0) {
				siteTable.setVisible(true);
				cityTable.setVisible(false);
				tourTable.setVisible(false);
			}
			
		}));
	}
	public void tourBtnListener() {
		tourReports.setOnMouseClicked((new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent arg0) {
				siteTable.setVisible(false);
				cityTable.setVisible(false);
				tourTable.setVisible(true);
			}
			
		}));
	}
	
	public void initTables() {
		cityTable.setVisible(false);
		siteTable.setVisible(false);
		tourTable.setVisible(false);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTables();
		initCityTableView();
		initSiteTableView();
		initTourTableView();
		siteBtnListener();
		cityBtnListener();
		tourBtnListener();
	}

}
