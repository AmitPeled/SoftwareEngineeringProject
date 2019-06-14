package database.execution;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import dataAccess.users.PurchaseDetails;
import maps.Map;

public interface IGcmCustomerExecutor {
	double getMembershipPrice(int cityId, int timeInterval) throws SQLException;
	boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails, String username) throws SQLException;
	String getSavedCreditCard() throws SQLException;
	boolean repurchaseMembership(PurchaseDetails purchaseDetails, String username) throws SQLException;
	boolean repurchaseMembershipBySavedDetails(String username) throws SQLException;
	File purchaseMapOneTime(int mapId, PurchaseDetails purchaseDetails, String username) throws SQLException;
	void notifyMapView(int mapId, String username) throws SQLException;
	File downloadMap(int mapId, String username) throws SQLException;
	List<Map> getPurchasedMaps(String username) throws SQLException;

}
