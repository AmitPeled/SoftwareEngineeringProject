package database.execution;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import dataAccess.users.PurchaseDetails;
import maps.Map;

public interface IGcmCustomerExecutor {
	double getMembershipPrice(int cityId, int timeInterval) throws SQLException;
	double getOneTimePurchasePrice(int cityId)throws SQLException;
	boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails, String username) throws SQLException;
	String getSavedCreditCard(String username) throws SQLException;
	boolean repurchaseMembershipBySavedDetails(String username) throws SQLException;
	List<File> purchaseMapOneTime(int cityId, PurchaseDetails purchaseDetails, String username) throws SQLException;
	void notifyMapView(int cityId, String username) throws SQLException;
	File downloadMap(int cityId, String username) throws SQLException;
	List<Map> getPurchasedMaps(String username) throws SQLException;

}
