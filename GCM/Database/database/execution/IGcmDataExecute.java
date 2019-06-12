package database.execution;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

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

	void deleteMap(int mapId) throws SQLException;

	void updateMap(int mapId, Map newMap) throws SQLException;

	Map getMapDetails(int mapId) throws SQLException;

	File getMapFile(int mapId) throws SQLException;

	int addCity(City city) throws SQLException;

	int addCityWithInitialMap(City city, Map map, File mapFile) throws SQLException;

	void updateCity(int cityId, City city) throws SQLException;

	void deleteCity(City city) throws SQLException;

	int addNewSiteToCity(int cityId, Site site) throws SQLException;

	void addExistingSiteToMap(int mapId, int siteId) throws SQLException;

	void DeleteSiteFromMap(int mapId, int siteId) throws SQLException;

	void UpdateSite(int siteId, Site newSite) throws SQLException;

	void DeleteSite(int siteId) throws SQLException;

	List<Map> getMapsByCityName(String cityName) throws SQLException;

	List<Map> getMapsBySiteName(String siteName) throws SQLException;

	List<Map> getMapsByDescription(String description) throws SQLException;

	User getUserDetails(String username) throws SQLException;

	File downloadMap(int mapId, String username);

	City getCityByMapId(int mapId) throws SQLException;

	List<Map> getPurchasedMaps(String username) throws SQLException;

	File purchaseMap(String username) throws SQLException;

	int updateContent(int contentId, Object content) throws SQLException;

	List<Site> getCitySites(int cityId) throws SQLException;

	void addExistingSiteToTour(int tourId, int siteId, int durnace)throws SQLException;

	int addNewTourToCity(int cityId, Tour tour) throws SQLException;

	// publish map/site/city
	// purchaseMap
}
