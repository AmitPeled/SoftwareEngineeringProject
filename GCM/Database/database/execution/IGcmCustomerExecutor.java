package database.execution;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Nested;

import dataAccess.customer.PurchaseHistory;
import dataAccess.users.PurchaseDetails;
import maps.Map;

public interface IGcmCustomerExecutor {
	//done
	double getMembershipPrice(int cityId, int timeInterval,String username) throws SQLException;
	//done
	double getOneTimePurchasePrice(int cityId)throws SQLException;
	//done
	boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails, String username) throws SQLException;
	//done
	String getSavedCreditCard(String username) throws SQLException;
	//done
	boolean repurchaseMembershipBySavedDetails(int cityId,int timeInterval,String username) throws SQLException;
	//done
	List<File> purchaseMapOneTime(int cityId, PurchaseDetails purchaseDetails, String username) throws SQLException;
	//done
	void notifyMapView(int cityId) throws SQLException;
	//done
	File downloadMap(int mapId, String username) throws SQLException;
	//done
	List<Map> getPurchasedMaps(String username) throws SQLException;
	
	//done
	List<PurchaseHistory> getPurchaseHistory(String username) throws SQLException; 

}
