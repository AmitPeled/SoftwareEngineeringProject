package database.execution;

import java.io.File;
import java.sql.SQLException;

import maps.Map;
import users.User;

/**
 * @author amit
 *
 */
public interface IGcmDataExecute {
	boolean addUser(String username, String password, User user) throws SQLException;

	boolean verifyUser(String username, String password) throws SQLException;

	int addMap(int cityId, Map mapDescription, File mapFile) throws SQLException;

	void deleteMap(int cityId, int mapId) throws SQLException;

	Map getMapDetails(int mapId) throws SQLException;

	File getMapFile(int mapId) throws SQLException;

	// publish map/site/city
	// purchaseMap
}
