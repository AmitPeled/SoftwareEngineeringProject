package dataAccess.customer;

import java.io.File;
import java.util.List;

import dataAccess.users.PurchaseDetails;
import maps.City;
import queries.RequestState;
import users.User;

public interface CustomerDAO {

	/**
	 * the city contains the map
	 */
	City getCityByMapId(int mapId);

//	/**
//	 * @param cityId                 id of the city to purchase
//	 * @param membershipMonthsPeriod period of the membership in months
//	 * @return the city's price for membership period
//	 */
//	double getCityPrice(int cityId, int membershipMonthsPeriod);

	/**
	 * @param timeInterval is the number of months the user buy membership to. if
	 *                     timeInterval is 0, purchase
	 * @return false if time purchase interval exceeds 6 months or purchase details
	 *         are not valid.
	 */
	boolean purchaseCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails);

	/**
	 * @return true if purchase succeeded
	 */
//	boolean purchaseCityOneTime(int cityId, PurchaseDetails purchaseDetails);

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
//	boolean repurchaseMembership(PurchaseDetails purchaseDetails);

	/**
	 * @return false if no valid user purchase details are stored in the database.
	 */
	boolean repurchaseSubsriptionToCity(int cityId);

	/**
	 * @return if user has access to view the map and update the system in
	 *         accordance.
	 */
	boolean notifyMapView(int mapId);

	File downloadMap(int mapId);

	List<City> getActiveCitiesPurchases();

	/**
	 * All the user's details managed in the database, including number of purchases
	 * and membership expire date. (the expire date is null if no current active
	 * membership)
	 */
	User getUserDetails();

RequestState updateUser(User user, String newPassword);

RequestState updateUser(User user);

boolean ownActiveSubsicription(int cityId);

}
