package approvalReports;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.cityApprovalReports.CityTableCell;
import approvalReports.mapApprovalReports.MapSubmission;
import approvalReports.mapApprovalReports.MapTableCell;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.sitesApprovalReports.SiteTableCell;
import approvalReports.tourApprovalReports.TourSitesTableCell;

import approvalReports.tourApprovalReports.TourSubmission;
import approvalReports.tourApprovalReports.TourTableCell;
import gcmDataAccess.GcmDAO;
import init.initializers.Initializer;
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
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import mainApp.GcmClient;
import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;

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
	TableView<MapSubmission> mapTable;
	@FXML
	TableColumn<MapSubmission, String> mapName;
	@FXML
	TableColumn<MapSubmission, String> mapDescription;
	@FXML
	TableColumn<MapSubmission, String> mapPrice;
	@FXML
	TableColumn<MapSubmission, String> mapActionTaken;
	@FXML
	TableColumn<MapSubmission, Button> mapApprovalDisapproval;
	
	
	@FXML
	Button cityReports;
	@FXML
	Button siteReports;
	@FXML
	Button tourReports;
	@FXML
	Button mapReports;

	
	private List<CitySubmission> citySubmissions;
	private List<SiteSubmission> siteSubmissions;
	private List<TourSubmission> tourSubmission;
	private List<MapSubmission> mapSubmission;
	private GcmClient gcmClient;

	private ApprovalReportsController(GcmClient gcmClient,
			GcmDAO gcmDAO, 
			List<CitySubmission> citySubmissions, 
			List<SiteSubmission> siteSubmissions, 
			List<TourSubmission> tourSubmissions, 
			List<MapSubmission> mapSubmissions) {
		this.gcmClient = gcmClient;
		this.gcmDAO = gcmDAO;
		this.citySubmissions = citySubmissions == null ? new ArrayList<CitySubmission>() : citySubmissions;
		this.siteSubmissions = siteSubmissions == null ? new ArrayList<SiteSubmission>() : siteSubmissions;
		this.tourSubmission = tourSubmissions  == null ? new ArrayList<TourSubmission>() : tourSubmissions;
		this.mapSubmission = mapSubmission  == null ? new ArrayList<MapSubmission>() : mapSubmission;

	}
	
	public void initSiteTableView() {
	        siteName.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getSite().getName()));
	        siteDescription.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getSite().getDescription()));
	        siteType.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getSite().getSiteType()));
	        siteActionTaken.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getActionTaken()));
	        
	        siteApprovalDisapproval.setCellFactory(new Callback<TableColumn<SiteSubmission, Button>, TableCell<SiteSubmission, Button>>() {
	            @Override
	            public TableCell<SiteSubmission, Button> call(TableColumn<SiteSubmission, Button> param) {
	            	return new SiteTableCell(gcmDAO);
	            }
	        });
	        
	        
	        ObservableList<SiteSubmission> details = siteSubmissions.isEmpty() ? FXCollections.observableArrayList() : FXCollections.observableArrayList(siteSubmissions);
	        siteTable.setItems(details);
	}
	public void initCityTableView() {
        cityName.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getCity().getName()));
        cityDescription.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getCity().getDescription()));
        cityActionTaken.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getActionTaken()));
        
        cityApprovalDisapproval.setCellFactory(new Callback<TableColumn<CitySubmission, Button>, TableCell<CitySubmission, Button>>() {
            @Override
            public TableCell<CitySubmission, Button> call(TableColumn<CitySubmission, Button> param) {
                return new CityTableCell(gcmDAO);
            }
        });
        
        
        ObservableList<CitySubmission> details = citySubmissions.isEmpty() ? FXCollections.observableArrayList() : FXCollections.observableArrayList(citySubmissions);
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

        ObservableList<TourSubmission> details = tourSubmission.isEmpty() ? FXCollections.observableArrayList() : FXCollections.observableArrayList(tourSubmission);
        tourTable.setItems(details);
	}

	public void initMapTableView() {
        mapName.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getMap().getName()));
        mapDescription.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getMap().getDescription()));
        mapPrice.setCellValueFactory(data ->  new ReadOnlyStringWrapper(Double.toString(data.getValue().getMap().getPrice())));
        mapActionTaken.setCellValueFactory(data ->  new ReadOnlyStringWrapper(data.getValue().getActionTaken()));
        
        mapApprovalDisapproval.setCellFactory(new Callback<TableColumn<MapSubmission, Button>, TableCell<MapSubmission, Button>>() {
            @Override
            public TableCell<MapSubmission, Button> call(TableColumn<MapSubmission, Button> param) {
                return new MapTableCell(gcmDAO);
            }
        });
        
        
        ObservableList<MapSubmission> details = FXCollections.observableArrayList(mapSubmission);
        mapTable.setItems(details);
	}

	
	public void cityBtnListener() {
		cityReports.setOnMouseClicked((new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				cityTable.setVisible(true);
				siteTable.setVisible(false);
				tourTable.setVisible(false);
				mapTable.setVisible(false);

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
				mapTable.setVisible(false);

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
				mapTable.setVisible(false);

			}
			
		}));
	}
	public void mapBtnListener() {
		mapReports.setOnMouseClicked((new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent arg0) {
				siteTable.setVisible(false);
				cityTable.setVisible(false);
				tourTable.setVisible(false);
				mapTable.setVisible(true);
			}
			
		}));
	}	

	
	public void initTables() {
		cityTable.setVisible(false);
		siteTable.setVisible(false);
		tourTable.setVisible(false);
		mapTable.setVisible(false);
		
		initCityTableView();
		initSiteTableView();
		initTourTableView();
		initMapTableView();

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTables();
		
		siteBtnListener();
		cityBtnListener();
		tourBtnListener();
		mapBtnListener();
	}
	
	public void initialize() {
		System.out.println("Initializing approval screen");
		citySubmissions = fetchCitySubmissions(gcmDAO); 
		siteSubmissions = fetchSiteSubmissions(gcmDAO);

		initialize(null, null);
	}
	
	@FXML
	public void onBackButton() { gcmClient.back(); }

	public static ApprovalReportsController getConrollerObject(GcmClient gcmClient){
		GcmDAO gcmDAO = gcmClient.getDataAccessObject();
		List<CitySubmission> citySubmissions = fetchCitySubmissions(gcmDAO); 
		List<SiteSubmission> siteSubmissions = fetchSiteSubmissions(gcmDAO);
		List<MapSubmission> mapSubmissions = fetchMapSubmissions(gcmDAO);
		List<TourSubmission> tourSubmissions = fetchTourSubmissions(gcmDAO);
		
		return new ApprovalReportsController(gcmClient, 
				gcmDAO, 
				citySubmissions, 
				siteSubmissions, 
				tourSubmissions,
				mapSubmissions);
	}
	private static List<TourSubmission> fetchTourSubmissions(GcmDAO gcmDAO) {
		List<TourSubmission> mapSubmissions = new ArrayList<TourSubmission>();
		List<Tour> tourAdded = gcmDAO.getToursAddEdits();
		List<Tour> tourModified = gcmDAO.getToursUpdateEdits();
		List<Tour> tourDeleted = gcmDAO.getToursDeleteEdits();

		if(tourAdded != null && !tourAdded.isEmpty()) {
			for (Tour tour : tourAdded) {
				mapSubmissions.add(new TourSubmission(tour, ActionTaken.ADD));
			}
		}
		if(tourModified!= null && !tourModified.isEmpty()) {
			for (Tour tour : tourModified) {
				mapSubmissions.add(new TourSubmission(tour, ActionTaken.UPDATE));
			}
		} 
		if(tourDeleted!= null && !tourDeleted.isEmpty()) {
			for (Tour tour : tourDeleted) {
				mapSubmissions.add(new TourSubmission(tour, ActionTaken.DELETE));
			}
		}
		return mapSubmissions;
	}
	private static List<MapSubmission> fetchMapSubmissions(GcmDAO gcmDAO) {
		List<MapSubmission> mapSubmissions = new ArrayList<MapSubmission>();
		List<Map> mapsAdded = gcmDAO.getMapsAddEdits();
		List<Map> mapsModified = gcmDAO.getMapsUpdateEdits();
		List<Map> mapsDeleted = gcmDAO.getMapsDeleteEdits();

		if(mapsAdded != null && !mapsAdded.isEmpty()) {
			for (Map map : mapsAdded) {
				mapSubmissions.add(new MapSubmission(map, ActionTaken.ADD));
			}
		}
		if(mapsModified!= null && !mapsModified.isEmpty()) {
			for (Map map : mapsModified) {
				mapSubmissions.add(new MapSubmission(map, ActionTaken.UPDATE));
			}
		} 
		if(mapsDeleted!= null && !mapsDeleted.isEmpty()) {
			for (Map map : mapsDeleted) {
				mapSubmissions.add(new MapSubmission(map, ActionTaken.DELETE));
			}
		}
		return mapSubmissions;
	}
	private static List<CitySubmission> fetchCitySubmissions(GcmDAO gcmDAO) {
		List<CitySubmission> citySubmissions = new ArrayList<CitySubmission>();
		List<City> citiesAdded = gcmDAO.getCitiesAddEdits();
		List<City> citiesModified = gcmDAO.getCitiesUpdateEdits();
		List<City> citiesDeleted = gcmDAO.getCitiesDeleteEdits();
		if(citiesAdded != null && !citiesAdded.isEmpty()) {
			for (City city : citiesAdded) {
				citySubmissions.add(new CitySubmission(city, ActionTaken.ADD));
			}
		}
		if(citiesModified!= null && !citiesModified.isEmpty()) {
			for (City city : citiesModified) {
				citySubmissions.add(new CitySubmission(city, ActionTaken.UPDATE));
			}
		} 
		if(citiesDeleted!= null && !citiesDeleted.isEmpty()) {
			for (City city : citiesDeleted) {
				citySubmissions.add(new CitySubmission(city, ActionTaken.DELETE));
			}
		}
		return citySubmissions;
	}
	
	private static List<SiteSubmission> fetchSiteSubmissions(GcmDAO gcmDAO) {
		List<SiteSubmission> siteSubmissions = new ArrayList<SiteSubmission>();
		List<Site> sitesAdded = gcmDAO.getSitesAddEdits();
		List<Site> sitesModified = gcmDAO.getSitesUpdateEdits();
		List<Site> sitesDeleted = gcmDAO.getSitesDeleteEdits();
		if(sitesAdded != null && !sitesAdded.isEmpty()) {
			for (Site site : sitesAdded) {
				siteSubmissions.add(new SiteSubmission(site, ActionTaken.ADD));
			}
		}
		if(sitesModified!= null && !sitesModified.isEmpty()) {
			for (Site site : sitesModified) {
				siteSubmissions.add(new SiteSubmission(site, ActionTaken.UPDATE));
			}
		} 
		if(sitesDeleted!= null && !sitesDeleted.isEmpty()) {
			for (Site site : sitesDeleted) {
				siteSubmissions.add(new SiteSubmission(site, ActionTaken.DELETE));
			}
		}
		return siteSubmissions;
	}
}

