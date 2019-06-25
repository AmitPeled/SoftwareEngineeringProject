package approvalReports;

import java.net.URL;
import java.sql.SQLException;
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

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import mainApp.GcmClient;
import users.User;

public class ApprovalReportsController implements Initializable {
	 private GcmDAO                      gcmDAO;
	 @FXML
	 TableView<SiteSubmission>           siteTable;
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
	 TableView<CitySubmission>           cityTable;
	 @FXML
	 TableColumn<CitySubmission, String> cityName;
	 @FXML
	 TableColumn<CitySubmission, String> cityDescription;
	 @FXML
	 TableColumn<CitySubmission, String> cityActionTaken;
	 @FXML
	 TableColumn<CitySubmission, Button> cityApprovalDisapproval;

	 @FXML
	 TableView<TourSubmission>           tourTable;
	 @FXML
	 TableColumn<TourSubmission, String> tourDescription;
	 @FXML
	 TableColumn<TourSubmission, String> tourActionTaken;
	 @FXML
	 TableColumn<TourSubmission, Button> tourApprovalDisapproval;

	 @FXML
	 TableView<MapSubmission>           mapTable;
	 @FXML
	 TableColumn<MapSubmission, String> mapName;
	 @FXML
	 TableColumn<MapSubmission, String> mapDescription;
	 @FXML
	 TableColumn<MapSubmission, String> mapActionTaken;
	 @FXML
	 TableColumn<MapSubmission, Button> mapApprovalDisapproval;

	 @FXML
	 TableView<User>           notifyUsersList;
	 @FXML
	 TableColumn<User, String> email;
	 @FXML
	 TableColumn<User, String> username;

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
	 private List<TourSubmission> tourSubmissions;
	 private List<MapSubmission>  mapSubmissions;
	 private GcmClient            gcmClient;

	 public ApprovalReportsController(GcmClient gcmClient, GcmDAO gcmDAO) {
		  this.gcmClient = gcmClient;
		  this.gcmDAO = gcmDAO;
	 }

	 public String getActionTaken(String objectName, ActionTaken actionTaken, String objectRelatedTo) {
		  String completeAction;
		  String action;
		  if (actionTaken.equals(ActionTaken.ADD)) {
			   action = "added to ";
			   if (objectRelatedTo.isEmpty()) {
					action = "added";
			   }
		  } else if (actionTaken.equals(ActionTaken.UPDATE)) {
			   action = "updated in ";
			   if (objectRelatedTo.isEmpty()) {
					action = "updated";
			   }
		  } else {
			   action = "removed from ";
			   if (objectRelatedTo.isEmpty()) {
					action = "removed";
			   }
		  }
		  completeAction = objectName + " was " + action + objectRelatedTo;

		  return completeAction;
	 }

	 public void initSiteTableView() {
			siteName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSite().getName()));
		  siteDescription
		            .setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSite().getDescription()));
		  siteType.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSite().getSiteType()));
		  siteActionTaken.setCellValueFactory(data -> {
			   String objectName = data.getValue().getSite().getName();
			   ActionTaken actionTaken = data.getValue().getAction();
			   int objectRelatedToId = data.getValue().getContainingObjectID();
			   ObjectsEnum objectType = data.getValue().getContainingObjectType();
			   String objectRelatedTo = "";

			   if (actionTaken.equals(ActionTaken.ADD)) {
					if (objectType.equals(ObjectsEnum.CITY)) {
						 objectRelatedTo = gcmDAO.getCity(objectRelatedToId).getName();
					} else if (objectType.equals(ObjectsEnum.MAP)) {
						 objectRelatedTo = gcmDAO.getMapDetails(objectRelatedToId).getName();
					} else {
						 objectRelatedTo = gcmDAO.getTour(objectRelatedToId).getDescription();
					}
			   } else if (actionTaken.equals(ActionTaken.UPDATE)) {
					objectRelatedTo = gcmDAO.getCity(objectRelatedToId).getName();
			   } else {
					if (objectType.equals(ObjectsEnum.CITY)) {
						 objectRelatedTo = gcmDAO.getCity(objectRelatedToId).getName();
					} else if (objectType.equals(ObjectsEnum.MAP)) {
						 objectRelatedTo = gcmDAO.getMapDetails(objectRelatedToId).getName();
					} else {
						 objectRelatedTo = gcmDAO.getTour(objectRelatedToId).getDescription();
					}
			   }
			   String action = getActionTaken(objectName, actionTaken, objectRelatedTo);
			   return new ReadOnlyStringWrapper(action);
		  });
		  siteApprovalDisapproval.setCellFactory(
		            new Callback<TableColumn<SiteSubmission, Button>, TableCell<SiteSubmission, Button>>() {
			             @Override
			             public TableCell<SiteSubmission, Button> call(TableColumn<SiteSubmission, Button> param) {
					          return new SiteTableCell(gcmDAO);
			             }
		            });

	 }

	 public void initCityTableView() {
		  cityName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCity().getName()));
		  cityDescription
		            .setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCity().getDescription()));
		  cityActionTaken.setCellValueFactory(data -> {
			   String objectName = data.getValue().getCity().getName();
			   ActionTaken actionTaken = data.getValue().getAction();
			   String objectRelatedTo = "";

			   String action = getActionTaken(objectName, actionTaken, objectRelatedTo);
			   return new ReadOnlyStringWrapper(action);
		  });
		  cityApprovalDisapproval.setCellFactory(
		            new Callback<TableColumn<CitySubmission, Button>, TableCell<CitySubmission, Button>>() {
			             @Override
			             public TableCell<CitySubmission, Button> call(TableColumn<CitySubmission, Button> param) {
					          return new CityTableCell(gcmDAO);
			             }
		            });

	 }
	
	public void initTourTableView() {
		tourDescription
				.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTour().getDescription()));
		tourActionTaken.setCellValueFactory(data -> {
			String objectName = data.getValue().getTour().getDescription();
			ActionTaken actionTaken = data.getValue().getAction();
			int objectRelatedToId = data.getValue().getContainingObjectID();
			ObjectsEnum objectType = data.getValue().getContainingObjectType();
			String objectRelatedTo = "";
			
			if(actionTaken.equals(ActionTaken.ADD) && objectType.equals(ObjectsEnum.MAP)) {
				objectRelatedTo = gcmDAO.getMapDetails(objectRelatedToId).getName();
			}else {
				objectRelatedTo = gcmDAO.getCity(objectRelatedToId).getName();
			}
			
			String action = getActionTaken(objectName, actionTaken, objectRelatedTo);
			return new ReadOnlyStringWrapper(action);
		});

		tourApprovalDisapproval
				.setCellFactory(new Callback<TableColumn<TourSubmission, Button>, TableCell<TourSubmission, Button>>() {
					@Override
					public TableCell<TourSubmission, Button> call(TableColumn<TourSubmission, Button> param) {
						return new TourTableCell(gcmDAO);
					}
				});

	 }

	 public void initMapTableView() {
		  mapName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMap().getName()));
		  mapDescription
		            .setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMap().getDescription()));
		mapActionTaken.setCellValueFactory(data -> {
			String objectName = data.getValue().getMap().getName();
			ActionTaken actionTaken = data.getValue().getAction();
			int objectRelatedToId = data.getValue().getContainingCityID();
			String objectRelatedTo = "";
			if(gcmDAO.getCity(objectRelatedToId) != null) {
				objectRelatedTo = gcmDAO.getCity(objectRelatedToId).getName();
			}
			String action = getActionTaken(objectName, actionTaken, objectRelatedTo);
			return new ReadOnlyStringWrapper(action);
		});
		  mapApprovalDisapproval.setCellFactory(
		            new Callback<TableColumn<MapSubmission, Button>, TableCell<MapSubmission, Button>>() {
			             @Override
			             public TableCell<MapSubmission, Button> call(TableColumn<MapSubmission, Button> param) {
						return new MapTableCell(gcmDAO, notifyUsersList);
			             }
		            });

	}
	public void initUsersTableView() {
		username.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getUsername()));
		email.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));
	}

	public void getDataForCityTableView() {
		citySubmissions = fetchCitySubmissions(gcmDAO);
		ObservableList<CitySubmission> details = citySubmissions.isEmpty() ? FXCollections.observableArrayList()
				: FXCollections.observableArrayList(citySubmissions);
		cityTable.setItems(details);
	}

	public void getDataForMapTableView() {
		mapSubmissions = fetchMapSubmissions(gcmDAO);
		  ObservableList<MapSubmission> details = FXCollections.observableArrayList(mapSubmissions);
		  mapTable.setItems(details);
	 }
	public void getDataForTourTableView() {
	 public void getDataForTourTableView() {
		tourSubmissions = fetchTourSubmissions(gcmDAO);
		ObservableList<TourSubmission> details = tourSubmissions.isEmpty() ? FXCollections.observableArrayList()
				: FXCollections.observableArrayList(tourSubmissions);
		tourTable.setItems(details);
	}

	public void getDataForSiteTableView() {
		siteSubmissions = fetchSiteSubmissions(gcmDAO);
		ObservableList<SiteSubmission> details = siteSubmissions.isEmpty() ? FXCollections.observableArrayList()
				: FXCollections.observableArrayList(siteSubmissions);
		siteTable.setItems(details);
	}
	
	public void setUsersTableView(List<User> users) {
		notifyUsersList.setVisible(true);
		ObservableList<User> details = FXCollections.observableArrayList(users);
		notifyUsersList.setItems(details);
	}
	 public void cityBtnListener() {
		  cityReports.setOnMouseClicked((new EventHandler<MouseEvent>() {

			   @Override
			   public void handle(MouseEvent arg0) {
				getDataForCityTableView();
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
				getDataForSiteTableView();
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
				getDataForTourTableView();
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
				getDataForMapTableView();
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
		  notifyUsersList.setVisible(false);
		notifyUsersList.setVisible(false);
		
		initCityTableView();
		  initSiteTableView();
		  initTourTableView();
		  initMapTableView();
		initUsersTableView();
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
		
		  initialize(null, null);
	 }

	 @FXML
	 public void onBackButton() {
		  gcmClient.back();
	 }

	 public static ApprovalReportsController getConrollerObject(GcmClient gcmClient) {
		  GcmDAO gcmDAO = gcmClient.getDataAccessObject();
		List<CitySubmission> citySubmissions = fetchCitySubmissions(gcmDAO);
		List<SiteSubmission> siteSubmissions = fetchSiteSubmissions(gcmDAO);
		List<MapSubmission> mapSubmissions = fetchMapSubmissions(gcmDAO);
		List<TourSubmission> tourSubmissions = fetchTourSubmissions(gcmDAO);
		  return new ApprovalReportsController(gcmClient, gcmDAO);
	 }

	 private static List<TourSubmission> fetchTourSubmissions(GcmDAO gcmDAO) {
		  return gcmDAO.getTourSubmissions();
	 }

	 private static List<MapSubmission> fetchMapSubmissions(GcmDAO gcmDAO) {
		  return gcmDAO.getMapSubmissions();
	 }

	 private static List<CitySubmission> fetchCitySubmissions(GcmDAO gcmDAO) {
		  return gcmDAO.getCitySubmissions();
	 }

	 private static List<SiteSubmission> fetchSiteSubmissions(GcmDAO gcmDAO) {
		  List<SiteSubmission> siteSubmissions = gcmDAO.getSiteSubmissions();
		  return siteSubmissions;
	 }
}
