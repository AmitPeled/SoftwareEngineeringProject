package reports;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.customer.PurchaseHistory;
import dataAccess.generalManager.Report;
import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import mainApp.GcmClient;
import reports.cells.CityListCell;
import reports.cells.CustomerListCell;
import reports.cells.WorkerListCell;
import reports.resultItems.CityItem;
import reports.resultItems.CustomerItem;
import reports.resultItems.WorkerItem;
import users.User;
import users.UserReport;

public class ReportsController implements Initializable {
	 private GcmDAO         gcmDAO;
	 @FXML
	 TextField              searchBar;
	 @FXML
	 RadioButton            rCity;
	 @FXML
	 RadioButton            rWorker;
	 @FXML
	 RadioButton            rCustomer;
	 @FXML
	 DatePicker             dateFrom;
	 @FXML
	 DatePicker             dateUntil;
	 @FXML
	 Button                 search;
	 @FXML
	 Button                 getReportsForAll;
	 @FXML
	 ListView<CityItem>     cityResults;
	 @FXML
	 ListView<WorkerItem>   workerResults;
	 @FXML
	 ListView<CustomerItem> customerResults;
	 @FXML
	 TextField              errors;
	 @FXML
	 public ToggleGroup     searchOptions;
	 String                 selectedRadioValue;
	 RadioButton            selectRadio;
	 GcmClient              gcmClient;
	 LocalDate              startDate;
	 LocalDate              endDate;
	 String                 searchText;

	 public ReportsController(GcmClient gcmClient) {
		  this.gcmClient = gcmClient;
		  this.gcmDAO = gcmClient.getDataAccessObject();
	 }

	 public void initRadioButtons() {
		  // Radio 1: cityName
		  rCity.setToggleGroup(searchOptions);
		  rCity.setSelected(true);

		  // Radio 2: rWorker.
		  rWorker.setToggleGroup(searchOptions);

		  // Radio 3: rCustomer.
		  rCustomer.setToggleGroup(searchOptions);
	 }

	 public void getReportsForAllListener() {
		  getReportsForAll.setOnMouseClicked((new EventHandler<MouseEvent>() {

			   @Override
			   public void handle(MouseEvent event) {
				   if(startDate != null && endDate != null) {
						readInputFromTextFields();
						cityResults.setVisible(true);
						customerResults.setVisible(false);
						workerResults.setVisible(false);
						Date sDate = Date.valueOf(startDate);
						Date eDate = Date.valueOf(endDate);
						List<Report> reports = gcmDAO.getSystemReport(sDate, eDate);
						ObservableList<CityItem> data = FXCollections.observableArrayList();
						reports.forEach((report) -> data.add(reportToCityItem(report)));
	
						cityResults.setItems(data);
				   }
			   }

		  }));
	 }

	 public void searchListener() {
		  search.setOnMouseClicked((new EventHandler<MouseEvent>() {
			   @Override
			   public void handle(MouseEvent event) {
					readInputFromTextFields();
					List<String> list = Arrays.asList(searchText);

					if (checkFilledFields(list) && startDate != null && endDate != null) {
						 errors.setVisible(false);
						 cityResults.setItems(null);
						 customerResults.setItems(null);
						 workerResults.setItems(null);
						 if (selectedRadioValue.equals("City name")) {
							  reportByCityName();
						 } else if (selectedRadioValue.equals("Customer")) {
							  reportsByCustomer();
						 } else {
							  reportsByWorker();
						 }
					} else {
						 setErrors("Please fill all fields!");
					}
			   }

		  }));
	 }

	 private void readInputFromTextFields() {
		  searchText = searchBar.getText();
		  selectRadio = (RadioButton) searchOptions.getSelectedToggle();
		  selectedRadioValue = selectRadio.getText();
		  startDate = dateFrom.getValue();
		  endDate = dateUntil.getValue();
	 }

	 public void setErrors(String error) {
		  errors.setVisible(true);
		  errors.setText(error);
	 }

	 public boolean checkFilledFields(List<String> list) {
		  for (String item : list) {
			   if (item == null || item.isEmpty()) {
					return false;
			   }
		  }
		  return true;
	 }

	 public void initCellFactories() {
		  cityResults.setCellFactory(new Callback<ListView<CityItem>, ListCell<CityItem>>() {
			   @Override
			   public ListCell<CityItem> call(ListView<CityItem> listView) {
					return new CityListCell();
			   }
		  });
		  workerResults.setCellFactory(new Callback<ListView<WorkerItem>, ListCell<WorkerItem>>() {
			   @Override
			   public ListCell<WorkerItem> call(ListView<WorkerItem> listView) {
					return new WorkerListCell();
			   }
		  });
		  customerResults.setCellFactory(new Callback<ListView<CustomerItem>, ListCell<CustomerItem>>() {
			   @Override
			   public ListCell<CustomerItem> call(ListView<CustomerItem> listView) {
					return new CustomerListCell();
			   }
		  });
	 }

	 @Override
	 public void initialize(URL arg0, ResourceBundle arg1) {
		  errors.setVisible(false);
		  getReportsForAllListener();
		  initRadioButtons();
		  searchListener();
		  initCellFactories();
	 }

	 private void reportByCityName() {
		  cityResults.setVisible(true);
		  customerResults.setVisible(false);
		  workerResults.setVisible(false);
		  Date sDate = Date.valueOf(startDate);
		  Date eDate = Date.valueOf(endDate);
		  Report report = gcmDAO.getCityReport(sDate, eDate, searchText);
		  if (report != null) {
			   CityItem item = reportToCityItem(report);
			   ObservableList<CityItem> data = FXCollections.observableArrayList();
			   data.add(item);
			   cityResults.setItems(data);
		  }
	 }

	 private CityItem reportToCityItem(Report report) {
		  return new CityItem(report.getCityId(), Integer.toString(report.getViewsNum()),
		            Integer.toString(report.getOneTimePurchase()), Integer.toString(report.getSubscribes()),
		            Integer.toString(report.getResubscribers()), Integer.toString(report.getDownloads()));

	 }

	 private void reportsByWorker() {
		  cityResults.setVisible(false);
		  customerResults.setVisible(false);
		  workerResults.setVisible(true);
		  readInputFromTextFields();
		  Date sDate = Date.valueOf(startDate);
		  Date eDate = Date.valueOf(endDate);
		  UserReport workerReport = gcmDAO.getReportOnUser(sDate, eDate, searchText);

		  WorkerItem worker = userReportToWorkerItem(workerReport);
		  ObservableList<WorkerItem> data = FXCollections.observableArrayList();
		  data.add(worker);
		  workerResults.setItems(data);
	 }

	 private WorkerItem userReportToWorkerItem(UserReport workerReport) {
		  User user = workerReport.getUser();
		  try {
			   return new WorkerItem(1, user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(),
			             workerReport.getUserType());
		  } catch (Exception e) {
			   return null;
		  }
	 }

	 private void reportsByCustomer() {
		  cityResults.setVisible(false);
		  customerResults.setVisible(true);
		  workerResults.setVisible(false);
		  readInputFromTextFields();
		  // UserReport customerReport = gcmDAO.getCustomerReport(searchText);

		  Date sDate = Date.valueOf(startDate);
		  Date eDate = Date.valueOf(endDate);
		  UserReport customerReport = gcmDAO.getReportOnUser(sDate, eDate, searchText);
		  CustomerItem customer = userReportToCustomerItem(customerReport);
		  ObservableList<CustomerItem> data = FXCollections.observableArrayList();
		  data.add(customer);
		  customerResults.setItems(data);
	 }

	 private CustomerItem userReportToCustomerItem(UserReport customerReport) {
		  try {
			   User user = customerReport.getUser();
			   List<String> purchaseHistory = new ArrayList<>();
			   customerReport.getPurchaseHistory().forEach((purchase) -> purchaseHistory.add(purchase.toString()));
			   ;

			   return new CustomerItem(1, user.getUsername(), user.getPhoneNumber(), user.getEmail(), purchaseHistory);
		  } catch (Exception e) {
			   return null;
		  }
	 }

	 @FXML
	 public void onBackButton() {
		  gcmClient.back();
	 }
}
