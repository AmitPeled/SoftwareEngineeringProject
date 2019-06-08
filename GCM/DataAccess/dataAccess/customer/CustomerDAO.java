package dataAccess.customer;

import java.io.File;
import java.util.List;

import dataAccess.users.Membership;
import dataAccess.users.PurchaseDetails;
import maps.Map;
import users.User;

public interface CustomerDAO {
	
	boolean purchaseMembership(Membership membershipType, PurchaseDetails purchaseDetails);
	
	/**
	 * returns the map's File if the user currently has an active membership, null
	 * otherwise
	 */
	File purchaseMap(int mapId);

	File getPurchasedMap(int mapId);

	List<Map> getPurchasedMaps();

	/**
	 * All the user's details managed in the database, including number of purchases
	 */
	User getUserDetails();

}
