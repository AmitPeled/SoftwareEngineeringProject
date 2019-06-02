package database.Execution;

import java.io.File;
import java.sql.SQLException;

import maps.Map;
import users.User;

public interface IGcmDataExecute {
	boolean addUser(String username, String password, User user) throws SQLException;

	boolean verifyUser(String username, String password);

	int addMap(int cityId, Map mapDescription, File mapFile) throws SQLException;

	void deleteMapDescription(int cityId, int mapId);

	void deleteMapFile(int cityId, int mapId);

	Map getMapDescription(int mapId);

	File getMapFile(int mapId);

	// publish map/site/city
	// purchaseMap
}
