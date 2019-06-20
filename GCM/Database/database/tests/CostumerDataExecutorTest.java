package database.tests;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dataAccess.customer.PurchaseHistory;
import dataAccess.generalManager.Report;
import dataAccess.users.PurchaseDetails;
import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.IExecuteQueries;
import database.metadata.DatabaseMetaData;
import database.metadata.DatabaseMetaData.Tables;

public class CostumerDataExecutorTest {

	static IExecuteQueries dbExecutor;
	static String tableName;

	@BeforeAll
	static void setAll() {
		dbExecutor = new DatabaseExecutor(DBConnector.connect());
		tableName = DatabaseMetaData.getTableName(Tables.customerUsers);
	}

	@AfterAll
	static void closeConnection() {
		DBConnector.closeConnection();
	}

	@Test
	void approveCitystatus() throws SQLException {
		int cityId = 3;

		dbExecutor.updateTableColumn("citiesMetaDetails", "status", 0, "cityId", cityId);

	}

	@Test
	void getPriceWithCityId() throws SQLException {

		String username = "sonus";
		double price = 0;

		List<List<Object>> check = dbExecutor.selectColumnsByValue("purchaseDeatailsHistory", "username", username,
				"purchaseDate");

		for (int i = 1; i < 7; i++) {

			List<List<Object>> list = dbExecutor.selectColumnsByValue("citysesPrices", "cityId", 1, "Month" + i);
			if (list.isEmpty()) {
				price = -1;
			} else {
				price = (double) list.get(0).get(0);

			}
			if (check.isEmpty()) {
				System.out.println("the price for " + i + " month to cityId 1 is : " + price);
			} else {
				System.out.println(
						"disscount for you mate! the price for " + i + " month to cityId 1 is : " + price * 0.9);
			}
		}
		List<List<Object>> list = dbExecutor.selectColumnsByValue("citysesPrices", "cityId", 1, "oneTimePurchase");
		if (list.isEmpty()) {
			price = -1;
		}

		price = (double) list.get(0).get(0);
		System.out.println("the price for oneTimePurchase cityId 1 is : " + price);

	}

	@Test
	void getSaveCreditCard() throws SQLException {
		String username = "420Booty";
		String res = "";
		List<List<Object>> list = dbExecutor.selectColumnsByValue("costumerPurchaseDetails", "username", username,
				"creditCard");
		if (list.isEmpty()) {
			res = "";
		} else {
			res = (String) list.get(0).get(0);
		}
		res = "XXXX-XXXX-XXXX-" + res.substring(res.length() - 4);
		System.out.println(res);

	}

	@Test
	void purchaseMembershipToCity() throws SQLException {

		String username = "sonus";
		int cityid = 5;
		int timeInterval = 0;
		PurchaseDetails purchaseDetails = new PurchaseDetails("123456789123456", "01/24", "222", "123456789", "eli",
				"levi");
		boolean isseccuss = false;

		// this only happen is they not already exist, need to add if
		List<Object> cotumerPurchaseDetails = new ArrayList<Object>() {
			{
				add(username);
				add(purchaseDetails.getFirstname());
				add(purchaseDetails.getLastname());
				add(purchaseDetails.getCreditCard());
				add(purchaseDetails.getCvv());
				add(purchaseDetails.getCardExpireDate());
			}
		};
		try {
			dbExecutor.insertToTable("costumerPurchaseDetails", cotumerPurchaseDetails);
		} catch (SQLException e) {
			// else give null
			System.out.println(isseccuss);
		}

		// if seccuss
		// update the purchase in his table so we can know all history
		if (timeInterval > 0) {
			List<Object> pDetails = new ArrayList<Object>() {
				{

					int days = 30 * timeInterval;
					java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Date endDate = addDays(startDate, days);
					add(username);
					add(cityid);
					add(startDate);
					add(false);
					add(timeInterval);
					add(endDate);
				}
			};
			try {
				dbExecutor.insertToTable("purchaseDeatailsHistory", pDetails);
			} catch (SQLException e) {
				System.out.println(isseccuss);
			}
		} else {
			List<Object> pDetails = new ArrayList<Object>() {
				{

					int days = 30 * timeInterval;
					java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Date endDate = addDays(startDate, days);
					add(username);
					add(cityid);
					add(startDate);
					add(true);
					add(timeInterval);
					add(endDate);
				}
			};
			try {
				dbExecutor.insertToTable("purchaseDeatailsHistory", pDetails);
			} catch (SQLException e) {
				System.out.println(isseccuss);
			}

		}
		isseccuss = true;
		System.out.println(isseccuss);

	}

	@Test
	void repurchaseMembershipBySavedDetails() throws SQLException {
		int cityId = 24, timeInterval = 4;
		String username = "420Booty";
		if (timeInterval > 0) {
			List<Object> pDetails = new ArrayList<Object>() {
				{

					int days = 30 * timeInterval;
					java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Date endDate = addDays(startDate, days);
					add(username);
					add(cityId);
					add(startDate);
					add(false);
					add(timeInterval);
					add(endDate);
				}
			};
			try {
				dbExecutor.insertToTable("purchaseDeatailsHistory", pDetails);
			} catch (SQLException e) {
				System.out.println(false);
			}
		} else {
			List<Object> pDetails = new ArrayList<Object>() {
				{

					int days = 30 * timeInterval;
					java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Date endDate = addDays(startDate, days);
					add(username);
					add(cityId);
					add(startDate);
					add(true);
					add(timeInterval);
					add(endDate);
				}
			};
			try

			{
				dbExecutor.insertToTable("purchaseDeatailsHistory", pDetails);
			} catch (SQLException e) {
				System.out.println(false);
			}

		}

		// if seccuss
		System.out.println(true);

	}

	@Test
	void getPurchaseHistory() throws SQLException {

		String username = "420Booty";
		List<List<Object>> history = dbExecutor.selectColumnsByValue("purchaseDeatailsHistory", "username", username,
				"*");
		System.out.println(history.get(0));
		System.out.println(history.get(1));
		System.out.println(history.size());
		System.out.println(history.get(0).get(2));

		List<PurchaseHistory> purchaseHistories = new ArrayList<>();

		for (int i = 0; i < history.size(); i++) {
			PurchaseHistory purchaseHistory = new PurchaseHistory((Date) history.get(i).get(2),
					(Date) history.get(i).get(5), (int) history.get(i).get(1));
			purchaseHistories.add(purchaseHistory);
		}
		for (int i = 0; i < purchaseHistories.size(); i++) {
			purchaseHistories.get(i).print();
		}

	}

	@Test
	void ReportList() throws SQLException {
		int days = 90;
		java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Date endDate = addDays(startDate, days);
		startDate = addDays(startDate, -50);
		List<Report> list = getAllcitiesReport(startDate, endDate);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).print();
		}

	}

	@Test
	void getPurchaseHistoryManager() throws SQLException {

		String tableName = "purchaseDeatailsHistory";
		String columnToSelect = "*";
		String columnCondition = "expiredDate";
		int days = 90;
		java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Date endDate = addDays(startDate, days);
		startDate = addDays(startDate, -50);
		System.out.println(startDate);
		System.out.println(endDate);

		List<List<Object>> history = dbExecutor.betweenDates(tableName, columnToSelect, startDate, columnCondition,
				endDate);

		System.out.println(history.size());
		// System.out.println(history.get(0));
		// System.out.println(history.get(1));
		// System.out.println(history.size());
		// System.out.println(history.get(0).get(2));

		List<PurchaseHistory> purchaseHistories = new ArrayList<>();

		for (int i = 0; i < history.size(); i++) {
			PurchaseHistory purchaseHistory = new PurchaseHistory((Date) history.get(i).get(2),
					(Date) history.get(i).get(5), (int) history.get(i).get(1));
			purchaseHistories.add(purchaseHistory);
		}
		for (int i = 0; i < purchaseHistories.size(); i++) {
			purchaseHistories.get(i).print();
		}

	}

	@Test
	void getCityName() throws SQLException {

		String tableName = "citiesMetaDetails";
		String columnsToSelect = "*";
		String objectName = "cityId";
		int object = 239;
		String cityName;

		List<List<Object>> list = dbExecutor.selectColumnsByValue(tableName, objectName, object, columnsToSelect);
		System.out.println(list);
		cityName = (String) list.get(0).get(1);
		System.out.println(cityName);

	}

	@Test
	void addRowToMangerReport() throws SQLException {

		// need to get cityId and table that need to be 1 -> get the name and than
		// insert new row and than update the table
		int cityId = 361;
		addCityManagerReport(cityId, "subscribes");

	}

	@Test
	void cityReport() throws SQLException {
		int days = 30;
		java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		java.sql.Date endDate = addDays(startDate, days);
		startDate = addDays(startDate, -50);

		createMangerReportOnOneCity(239, startDate, endDate);
	}

	@Test
	void getAll() throws SQLException {
		List<List<Object>> list = dbExecutor.selectAllColumns("citiesMetaDetails", "cityId");

		System.out.println(list);
	}

	private void updateMangerReports(int cityId, String tableToUpdate) throws SQLException {

		dbExecutor.updateTableColumn("mangerReports", tableToUpdate, 1, "cityId", cityId);

	}

	private void addCityManagerReport(int cityId, String tableToUpdate) throws SQLException {

		String cityName = getCityNameToMe(cityId);

		List<Object> objects = new ArrayList<Object>() {
			{
				java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				add(cityId);
				add(cityName);
				add(0);
				add(0);
				add(0);
				add(0);
				add(0);
				add(date);

			}
		};

		dbExecutor.insertToTable("mangerReports", objects);
		updateMangerReports(cityId, tableToUpdate);

	}

	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}

	private String getCityNameToMe(int cityId) throws SQLException {

		String tableName = "citiesMetaDetails";
		String columnsToSelect = "*";
		String objectName = "cityId";
		String cityName;

		List<List<Object>> list = dbExecutor.selectColumnsByValue(tableName, objectName, cityId, columnsToSelect);
		cityName = (String) list.get(0).get(1);
		return cityName;

	}

	private void createMangerReportOnOneCity(int cityId, Date date1, Date date2) throws SQLException {
		String cityName = getCityNameToMe(cityId);

		Report report = new Report();
		report.setCityId(cityId);
		report.setCityName(cityName);

		List<String> tableNames = new ArrayList<>();
		tableNames.add("oneTimePurchase");
		tableNames.add("subscribes");
		tableNames.add("resubscribers");
		tableNames.add("viewsNum");
		tableNames.add("downloads");
		int oneTimePurchase = 0, subscribes = 0, resubscribers = 0, viewsNum = 0, downloads = 0;

		for (int i = 0; i < tableNames.size(); i++) {

			List<List<Object>> list = dbExecutor.betweenDatesAndConditions("mangerReports", "*", date1,
					"occurrenceDate", date2, "cityId", tableNames.get(i), cityId, 1);
			if (!list.isEmpty()) {

				if (tableNames.get(i).equals("oneTimePurchase")) {
					oneTimePurchase = list.size();
				} else if (tableNames.get(i).equals("subscribes")) {
					subscribes = list.size();
				} else if (tableNames.get(i).equals("resubscribers")) {
					resubscribers = list.size();
				} else if (tableNames.get(i).equals("viewsNum")) {
					viewsNum = list.size();
				} else if (tableNames.get(i).equals("downloads")) {
					downloads = list.size();
				}
			}

		}

		report.setOneTimePurchase(oneTimePurchase);
		report.setSubscribes(subscribes);
		report.setResubscribers(resubscribers);
		report.setViewsNum(viewsNum);
		report.setDownloads(downloads);
		report.print();

	}

	private List<Report> getAllcitiesReport(Date startDate, Date endDate) throws SQLException {

		String tableName = "mangerReports";
		String columnCondition = "occurrenceDate";
		List<List<Object>> cityIdList = dbExecutor.selectAllColumns("citiesMetaDetails", "cityId");
		List<Report> allCitiesreports = new ArrayList<>();
		for (int i = 0; i < cityIdList.size(); i++) {
			List<List<Object>> list = dbExecutor.betweenDates(tableName, "*", startDate, columnCondition, endDate);

			if (!list.isEmpty()) {
				Report report = reportCreateMangerReportOnOneCity((int) cityIdList.get(i).get(0), startDate, endDate);

				if (emptyfields(report)) {
					allCitiesreports.add(report);
				}
			}
		}

		return allCitiesreports;
	}

	private Report reportCreateMangerReportOnOneCity(int cityId, Date date1, Date date2) throws SQLException {
		String cityName = getCityNameToMe(cityId);

		Report report = new Report();
		report.setCityId(cityId);
		report.setCityName(cityName);

		List<String> tableNames = new ArrayList<>();
		tableNames.add("oneTimePurchase");
		tableNames.add("subscribes");
		tableNames.add("resubscribers");
		tableNames.add("viewsNum");
		tableNames.add("downloads");
		int oneTimePurchase = 0, subscribes = 0, resubscribers = 0, viewsNum = 0, downloads = 0;

		for (int i = 0; i < tableNames.size(); i++) {

			List<List<Object>> list = dbExecutor.betweenDatesAndConditions("mangerReports", "*", date1,
					"occurrenceDate", date2, "cityId", tableNames.get(i), cityId, 1);
			if (!list.isEmpty()) {

				if (tableNames.get(i).equals("oneTimePurchase")) {
					oneTimePurchase = list.size();
				} else if (tableNames.get(i).equals("subscribes")) {
					subscribes = list.size();
				} else if (tableNames.get(i).equals("resubscribers")) {
					resubscribers = list.size();
				} else if (tableNames.get(i).equals("viewsNum")) {
					viewsNum = list.size();
				} else if (tableNames.get(i).equals("downloads")) {
					downloads = list.size();
				}

			}
		}
		report.setOneTimePurchase(oneTimePurchase);
		report.setSubscribes(subscribes);
		report.setResubscribers(resubscribers);
		report.setViewsNum(viewsNum);
		report.setDownloads(downloads);

		return report;

	}

	private boolean emptyfields(Report report) {

		if (report.getDownloads() == 0 && report.getOneTimePurchase() == 0 && report.getResubscribers() == 0
				&& report.getSubscribes() == 0 && report.getViewsNum() == 0) {
			return false;
		}
		return true;
	}

}
