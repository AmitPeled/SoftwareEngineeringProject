package database.execution;

import java.io.File;
import java.sql.SQLException;

import maps.City;
import maps.Map;
import maps.Site;
import users.User;

/**
 * @author amit
 *
 */
public interface IGcmDataExecute {
	boolean addUser(String username, String password, User user) throws SQLException;

	boolean verifyUser(String username, String password) throws SQLException;

	int addMapToCity(int cityId, Map mapDescription, File mapFile) throws SQLException;

	void deleteMap(int mapId) throws SQLException;

	Map getMapDetails(int mapId) throws SQLException;

	File getMapFile(int mapId) throws SQLException;

	int addCity(City city) throws SQLException;

	int addNewSiteToCity(int cityId, Site site) throws SQLException;

	void addExistingSiteToMap(int mapId, int siteId) throws SQLException;

	void DeleteSiteFromMap(int mapId, int siteId) throws SQLException;

	void DeleteSite(int siteId) throws SQLException;

	// publish map/site/city
	// purchaseMap
}
