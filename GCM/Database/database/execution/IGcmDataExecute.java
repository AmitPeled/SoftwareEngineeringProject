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
import dataAccess.search.CityMaps;
import dataAccess.users.PurchaseDetails;
import database.serverObjects.MapSubmissionContent;
import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;
import queries.RequestState;
import users.User;
import users.UserReport;

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

	 byte[] getMapFile(int mapId) throws SQLException;

	 int addCity(City city) throws SQLException;

	 int addNewSiteToCity(int cityId, Site site) throws SQLException;

	 void addExistingSiteToMap(int mapId, int siteId) throws SQLException;

	 CityMaps getMapsByCityName(String cityName) throws SQLException;

	 CityMaps getMapsBySiteName(String siteName) throws SQLException;

	 CityMaps getMapsByDescription(String description) throws SQLException;

	 CityMaps getMapsBySiteAndCityNames(String string, String string2) throws SQLException;

	 User getUserDetails(String username) throws SQLException;

	 City getCityByMapId(int mapId) throws SQLException;

	 List<Site> getCitySites(int cityId) throws SQLException;

	 int addNewTourToCity(int cityId, Tour tour) throws SQLException;

	 void addExistingTourToMap(int mapId, int tourId) throws SQLException;

	 boolean purchaseCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails, String username)
	           throws SQLException;

	 String getSavedCreditCard(String username) throws SQLException;

	 boolean repurchaseMembershipBySavedDetails(int cityId, int timeInterval, String username) throws SQLException;

	 List<byte[]> purchaseCityOneTime(int cityId, PurchaseDetails purchaseDetails, String username) throws SQLException;

	 byte[] downloadMap(int cityId, String username) throws SQLException;

	 void editCityPrice(int cityId, double newPrice) throws SQLException;

	List<PurchaseHistory> getPurchaseHistory(String username) throws SQLException;

//	 Report getCityReport(java.util.Date startDate, java.util.Date endDate, String cityName) throws SQLException;

	 List<Report> getAllcitiesReport(java.sql.Date date, java.sql.Date date2) throws SQLException;

	 List<SiteSubmission> getSiteSubmissions() throws SQLException;

	 List<MapSubmissionContent> getMapSubmissions() throws SQLException;

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

	 public void deleteSiteFromCityEdit(int siteId) throws SQLException; // TOCHECK

	 void deleteSiteFromMapEdit(int mapId, int siteId) throws SQLException; // TOCHECK

	 void updateMap(int mapId, Map newMap) throws SQLException; // TOCHECK

	 void changeCityPrices(int id, List<Double> prices) throws SQLException;// TOCHECK

	 List<PriceSubmission> getPriceSubmissions() throws SQLException;// TOCHECK

	 void approveCityPrice(int cityId, List<Double> prices, boolean approve) throws SQLException;// TOCHECK

	 boolean notifyMapView(String username, int mapId) throws SQLException;

	 City getCityById(int cityId) throws SQLException;

	 Tour getTour(int tourId) throws SQLException;

	 void updateSite(int siteId, Site site) throws SQLException;

	 List<Map> getPurchasedMaps(String username) throws SQLException;

	 List<CitySubmission> getCitySubmissions() throws SQLException;

	 RequestState editUser(String oldUsername, String oldPassword, User user, String newPassword) throws SQLException;

	 List<Report> getUserReports(java.sql.Date date, java.sql.Date date2, String string) throws SQLException;

	UserReport getUserReport(java.sql.Date date, java.sql.Date date2, String string) throws SQLException;

	 Report getCityReport(Date startDate, Date endDate, String cityName) throws SQLException;

	 Site getSite(int siteId) throws SQLException;

	 boolean ownActiveSubsicription(int i, String username) throws SQLException;

}
