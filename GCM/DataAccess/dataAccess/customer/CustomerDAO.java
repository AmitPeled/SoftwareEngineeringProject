package dataAccess.customer;

import java.io.File;
import java.util.List;

import dataAccess.users.PurchaseDetails;
import maps.Map;
import users.User;

public interface CustomerDAO {

	/**
	 * @param cityId       id of the city to purchase membership
	 * @param timeInterval time interval of the membership in months
	 * @return membership price
	 */
	double getMembershipPrice(int cityId, int timeInterval);

	/**
	 * @return false if time purchase interval exceeds 6 months or purchase details
	 *         are not valid. by the boolean field saveDetailsForNext (which will
	 *         appear by a checkbox in the client side gui) the system will save the
	 *         user's purchase details for future purchases
	 */
	boolean purchaseMembership(int timeInterval, PurchaseDetails purchaseDetails, boolean saveDetailsForNext);

	/**
	 * repurchase membership for the same time interval as purchased before. by this
	 * the price gets lower by 10%.
	 * 
	 * @return false for invalid purchase details
	 */
	boolean repurchaseMembership(PurchaseDetails purchaseDetails);

	/**
	 * @return false if no valid user purchase details are stored in the database.
	 */
	boolean repurchaseMembershipBySavedDetails();

	/**
	 * @return null if user doens't currently have an active membership, and the
	 *         map's File otherwise.
	 */
	File purchaseMap(int mapId);

	/**
	 * both view and download map functionalities are available for period
	 * memberships only, and returns the same thing - the map's file. the download
	 * and view itself operates in the client side. the reason to do it apart is to
	 * maintain viewing and downloading statistics in the database.
	 */
	File viewMap(int mapId);

	File downloadMap(int mapId);

	List<Map> getPurchasedMaps();

	/**
	 * All the user's details managed in the database, including number of purchases
	 * and membership expire date. (the expire date is null if no current active
	 * membership)
	 */
	User getUserDetails();

}
