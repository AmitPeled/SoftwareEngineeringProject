package database.tests;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dataAccess.customer.CityPurchase;
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
			try {
				dbExecutor.insertToTable("purchaseDeatailsHistory", pDetails);
			} catch (SQLException e) {
				System.out.println(false);
			}

		}

		// if seccuss
		System.out.println(true);

	}

	@Test
	void getAllCitiesReports() throws SQLException {

		List<List<Object>> reports = dbExecutor.selectAllColumns("mangerReports", "*");
		System.out.println(reports.get(0));

		List<Report> allCitiesreports = new ArrayList<>();

		for (int i = 0; i < reports.size(); i++) {
			Report cityReport = new Report((int) reports.get(i).get(0), (String) reports.get(i).get(1),
					(int) reports.get(i).get(2), (int) reports.get(i).get(3), (int) reports.get(i).get(4),
					(int) reports.get(i).get(5), (int) reports.get(i).get(6));
			allCitiesreports.add(cityReport);
		}
		for (int i = 0; i < allCitiesreports.size(); i++) {
			allCitiesreports.get(i).print();
		}

	}
	
	@Test
	void  getOneCityReport() throws SQLException {
		String cityName = "Tel-Aviv";
		List<List<Object>> report = dbExecutor.selectColumnsByValue("mangerReports", "cityName", cityName,
				"*");
		System.out.println(report.get(0));
		Report cityReport = new Report((int) report.get(0).get(0), (String) report.get(0).get(1),
				(int) report.get(0).get(2), (int) report.get(0).get(3), (int) report.get(0).get(4),
				(int) report.get(0).get(5), (int) report.get(0).get(6));
		
		cityReport.print();
	
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

		List<CityPurchase> purchaseHistories = new ArrayList<>();

		for (int i = 0; i < history.size(); i++) {
			CityPurchase purchaseHistory = new CityPurchase((Date) history.get(i).get(2),
					(Date) history.get(i).get(5), (int) history.get(i).get(1));
			purchaseHistories.add(purchaseHistory);
		}
		for (int i = 0; i < purchaseHistories.size(); i++) {
			purchaseHistories.get(i).print();
		}

	}

	@Test
	void updateTableColumn() throws SQLException {
		int cityId = 1;
		String tableToUpdate = "oneTimePurchase";
		updateMangerReports(cityId, tableToUpdate);

		tableToUpdate = "subscribes";
		updateMangerReports(cityId, tableToUpdate);

		tableToUpdate = "resubscribers";
		updateMangerReports(cityId, tableToUpdate);

		tableToUpdate = "viewsNum";
		updateMangerReports(cityId, tableToUpdate);

		tableToUpdate = "downloads";
		updateMangerReports(cityId, tableToUpdate);

	}

	private void updateMangerReports(int cityId, String tableToUpdate) throws SQLException {

		int plusOne;

		List<List<Object>> updateListCulomn = dbExecutor.selectColumnsByValue("mangerReports", "cityId", cityId,
				"oneTimePurchase");
		if (updateListCulomn.isEmpty()) {
			System.out.println("wtf is not supposed to be empty");
		} else {
			plusOne = (int) updateListCulomn.get(0).get(0) + 1;

			dbExecutor.updateTableColumn("mangerReports", tableToUpdate, plusOne, "cityId", cityId);
		}

	}

	private void addCityManagerReport(int cityId, String cityName) throws SQLException {

		List<Object> objects = new ArrayList<Object>() {
			{
				add(cityId);
				add(cityName);
				add(0);
				add(0);
				add(0);
				add(0);
				add(0);

			}
		};

		dbExecutor.insertToTable("mangerReports", objects);

	}

	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}

}
