package dataAccess.customer;

import java.io.File;
import java.util.List;

import dataAccess.users.PurchaseDetails;
import maps.City;
import maps.Map;
import users.User;

public interface CustomerDAO {

	/**
	 * the city contains the map
	 */
	City getCityByMapId(int mapId);

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
	 * 
	 *         time interval in months
	 */
	boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails);

	/**
	 * @return true if purchase succeeded
	 */
	boolean purchaseCityOneTime(int cityId, PurchaseDetails purchaseDetails);

	/**
	 * @return 4 last digits of saved credit card.
	 */
	String getSavedCreditCard();

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
	 * @return if user has access to view the map and update the system in
	 *         accordance.
	 */
	boolean notifyMapView(int mapId);

	File downloadMap(int mapId);

	List<Map> getPurchasedMaps();

	/**
	 * All the user's details managed in the database, including number of purchases
	 * and membership expire date. (the expire date is null if no current active
	 * membership)
	 */
	User getUserDetails();

}
