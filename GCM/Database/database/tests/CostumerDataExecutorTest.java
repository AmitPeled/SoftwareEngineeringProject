package database.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

					java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					add(username);
					add(cityid);
					add(date);
					add(false);
					add(timeInterval);
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

					java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					add(username);
					add(cityid);
					add(date);
					add(true);
					add(timeInterval);
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

					java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					add(username);
					add(cityId);
					add(date);
					add(false);
					add(timeInterval);
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

					java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					add(username);
					add(cityId);
					add(date);
					add(true);
					add(timeInterval);
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

}
