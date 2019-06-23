package database.execution;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
import approvalReports.priceApprovalReports.PriceSubmission;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.tourApprovalReports.TourSubmission;
import dataAccess.customer.PurchaseHistory;
import dataAccess.generalManager.Report;
import dataAccess.users.PurchaseDetails;
import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;
import queries.RequestState;
import users.User;

/**
 * @author amit
 *
 */
public interface IGcmDataExecute {
	boolean addUser(String username, String password, User user) throws SQLException;

	RequestState verifyUser(String username, String password) throws SQLException;

	int addMapToCity(int cityId, Map mapDescription, File mapFile) throws SQLException;

	void deleteMapEdit(int mapId) throws SQLException;


	Map getMapDetails(int mapId) throws SQLException;

	File getMapFile(int mapId) throws SQLException;

	int addCity(City city) throws SQLException;

	int addNewSiteToCity(int cityId, Site site) throws SQLException;

	void addExistingSiteToMap(int mapId, int siteId) throws SQLException;

	List<Map> getMapsByCityName(String cityName) throws SQLException;

	List<Map> getMapsBySiteName(String siteName) throws SQLException;

	List<Map> getMapsByDescription(String description) throws SQLException;

	User getUserDetails(String username) throws SQLException;

	City getCityByMapId(int mapId) throws SQLException;

	List<Site> getCitySites(int cityId) throws SQLException;

	int addNewTourToCity(int cityId, Tour tour) throws SQLException;

	void addExistingTourToMap(int mapId, int tourId) throws SQLException;

	double getMembershipPrice(int cityId, int timeInterval, String username) throws SQLException;

	double getOneTimePurchasePrice(int cityId) throws SQLException;

	boolean purchaseCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails, String username)
			throws SQLException;

	String getSavedCreditCard(String username) throws SQLException;

	boolean repurchaseMembershipBySavedDetails(int cityId, int timeInterval, String username) throws SQLException;

	List<File> purchaseCityOneTime(int cityId, PurchaseDetails purchaseDetails, String username) throws SQLException;

	File downloadMap(int cityId, String username) throws SQLException;

	List<Map> getPurchasedMaps(String username) throws SQLException;

	void actionMapAddEdit(Map map, boolean action) throws SQLException;

	void actionMapUpdateEdit(Map map, boolean action) throws SQLException;

	void actionMapDeleteEdit(Map map, boolean action) throws SQLException;

	void actionCityAddEdit(City city, boolean action) throws SQLException;

	void actionCityUpdateEdit(City city, boolean action) throws SQLException;

	void actionCityDeleteEdit(City city, boolean action) throws SQLException;

	void actionSiteAddEdit(Site site, boolean action) throws SQLException;

	void actionSiteUpdateEdit(Site site, boolean action) throws SQLException;

	void actionSiteDeleteEdit(Site site, boolean action) throws SQLException;

	/**
	 * editors content editons. you can take with EditorDAO the original object
	 * before the edition.
	 */
	List<Map> getMapsAddEdits() throws SQLException;

	List<Map> getMapsUpdateEdits() throws SQLException;

	List<Map> getMapsDeleteEdits() throws SQLException;

	List<Site> getSitesAddEdits() throws SQLException;

	List<Site> getSitesUpdateEdits() throws SQLException;

	List<Site> getSitesDeleteEdits() throws SQLException;

	List<City> getCitiesAddEdits() throws SQLException;

	List<City> getCitiesUpdateEdits() throws SQLException;

	List<City> getCitiesDeleteEdits() throws SQLException;

	void editCityPrice(int cityId, double newPrice) throws SQLException;

	List<Tour> getToursDeleteEdits() throws SQLException;

	List<Tour> getToursUpdateEdits() throws SQLException;

	List<Tour> getToursAddEdits() throws SQLException;

	void actionTourAddEdit(Site site, boolean action) throws SQLException;

	void actionTourUpdateEdit(Site site, boolean action) throws SQLException;

	void actionTourDeleteEdit(Site site, boolean action) throws SQLException;

	List<Map> getMapsObjectAddedTo(int contentId) throws SQLException; // gets list of the maps that the object is added
																		// to

	List<City> getCitiesObjectAddedTo(int contentId) throws SQLException;

	List<Tour> getToursObjectAddedTo(int contentId) throws SQLException;

	List<PurchaseHistory> getPurchaseHistory(String username) throws SQLException;

	Report getCityReport(Date startDate, Date endDate, int cityId) throws SQLException;

	List<Report> getAllcitiesReport(Date startDate, Date endDate) throws SQLException;

	List<SiteSubmission> getSiteSubmissions() throws SQLException;

	List<MapSubmission> getMapSubmissions() throws SQLException;

	List<TourSubmission> getTourSubmissions() throws SQLException;

	void actionTourEdit(TourSubmission tour, boolean action) throws SQLException;

	List<User> actionMapEdit(MapSubmission tour, boolean action) throws SQLException;

	void actionCityEdit(CitySubmission tour, boolean action) throws SQLException;

	void actionSiteEdit(SiteSubmission tour, boolean action) throws SQLException;

	void updateCity(int cityId, City city) throws SQLException; // TOCHECK
	void deleteCityEdit(int cityId) throws SQLException; // TOCHECK
	void UpdateSite(int siteId, Site newSite) throws SQLException; // TOCHECK
	void addExistingSiteToTour(int tourId, int siteId, int durnace) throws SQLException; // TOCHECK
	void deleteSiteFromTourEdit(int tourId, int siteId) throws SQLException;// TOCHECK
	void deleteTourFromMapEdit(int mapId, int tourId) throws SQLException;// TOCHECK
	void deleteTourFromCity(int tourId) throws SQLException;// TOCHECK
	List<Tour> getCityTours(int cityId) throws SQLException;// TOCHECK
	void updateTour(int tourId, Tour tour) throws SQLException; // TOCHECK
	public void deleteSiteFromCity(int siteId) throws SQLException; // TOCHECK
	void deleteSiteFromMapEdit(int mapId, int siteId) throws SQLException; // TOCHECK
	void updateMap(int mapId, Map newMap) throws SQLException; // TOCHECK

	void changeCityPrices(int id, List<Double> prices) throws SQLException;// TOCHECK

	List<PriceSubmission> getPriceSubmissions() throws SQLException;// TOCHECK

	void approveCityPrice(int cityId, List<Double> prices, boolean approve) throws SQLException;// TOCHECK

	boolean notifyMapView(String username, int mapId) throws SQLException;

	City getCityById(int cityId) throws SQLException;

	Tour getTour(int tourId) throws SQLException;

	void updateSite(int siteId, Site site) throws SQLException;

}
