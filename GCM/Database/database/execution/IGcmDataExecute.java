package database.execution;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
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

	void updateMap(int mapId, Map newMap) throws SQLException;

	Map getMapDetails(int mapId) throws SQLException;

	File getMapFile(int mapId) throws SQLException;

	int addCity(City city) throws SQLException;

	public void deleteSiteFromCity(int siteId) throws SQLException;

	void updateCity(int cityId, City city) throws SQLException;

	void deleteCityEdit(int cityId) throws SQLException;

	int addNewSiteToCity(int cityId, Site site) throws SQLException;

	void addExistingSiteToMap(int mapId, int siteId) throws SQLException;

	void deleteSiteFromMap(int mapId, int siteId) throws SQLException;

	void UpdateSite(int siteId, Site newSite) throws SQLException;

	List<Map> getMapsByCityName(String cityName) throws SQLException;

	List<Map> getMapsBySiteName(String siteName) throws SQLException;

	List<Map> getMapsByDescription(String description) throws SQLException;

	User getUserDetails(String username) throws SQLException;

	City getCityByMapId(int mapId) throws SQLException;

	List<Site> getCitySites(int cityId) throws SQLException;

	void addExistingSiteToTour(int tourId, int siteId, int durnace) throws SQLException;

	int addNewTourToCity(int cityId, Tour tour) throws SQLException;

	void addExistingTourToMap(int mapId, int tourId) throws SQLException;

	double getMembershipPrice(int cityId, int timeInterval, String username) throws SQLException;

	double getOneTimePurchasePrice(int cityId) throws SQLException;

	boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails, String username)
			throws SQLException;

	String getSavedCreditCard(String username) throws SQLException;

	boolean repurchaseMembershipBySavedDetails(int cityId, int timeInterval, String username) throws SQLException;

	List<File> purchaseCityOneTime(int cityId, PurchaseDetails purchaseDetails, String username) throws SQLException;

	void notifyMapView(int cityId) throws SQLException;

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

	List<City> getCitiesObjectAddedTo(int contentId) throws SQLException;// gets list of the cities that the object is
																			// added to

	List<Tour> getToursObjectAddedTo(int contentId) throws SQLException;// gets list of the tours that the object is
																		// added to

	// publish map/site/city
	// purchaseMap

	List<PurchaseHistory> getPurchaseHistory(String username) throws SQLException;

	Report getOneCityReport(String cityName) throws SQLException;

	List<Report> getAllcitiesReport() throws SQLException;

	List<SiteSubmission> getSiteSubmissions() throws SQLException;

	List<MapSubmission> getMapSubmissions() throws SQLException;// TODO

	List<TourSubmission> getTourSubmissions() throws SQLException;// TODO

	void actionTourEdit(TourSubmission tour, boolean action) throws SQLException;// TODO

	void actionMapEdit(MapSubmission tour, boolean action) throws SQLException;// TODO

	void actionCityEdit(CitySubmission tour, boolean action) throws SQLException;// TODO

	void actionSiteEdit(SiteSubmission tour, boolean action) throws SQLException;// TODO

	void deleteSiteFromTour(int tourId, int siteId) throws SQLException;// TODO

	void deleteTourFromMap(int mapId, int tourId) throws SQLException;// TODO

	void deleteTourFromCity(int tourId) throws SQLException;// TODO

	List<Tour> getCityTours(int cityId) throws SQLException;// TODO

}
