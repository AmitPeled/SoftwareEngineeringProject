package database.Execution;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import database.ObjectParse.IParseObjects;
import maps.Map;
import users.User;

public class GcmDatabaseExecutor implements IGcmDataExecute {
	IExecuteQueries queryExecutor;
	IParseObjects objectParser;
	ReentrantLock accessLock = new ReentrantLock();

	// synchronization will be in DataExecutor
//	void synchronousInsert() {
//	}
//
//	void synchronousDelete() {
//	}
//
//	Object synchronousSelect(String tableName, String objectName, Object object, String columnsToSelect) {
//		
//	}

	@SuppressWarnings("serial")
	@Override
	public boolean addUser(String username, String password, User user) throws SQLException {
		if (queryExecutor.selectColumnsByValue("userDetails", "username", username, "username").next()) {
			List<Object> userList = new ArrayList<Object>() {
				{
					add(username);
					add(password);
					add(user);
				}
			};
			queryExecutor.insertToTable("usersDetails", userList);
			return true;
		}
		return false;
	}

	@SuppressWarnings("serial")
	@Override
	public int addMap(int cityId, Map mapDescription, File mapFile) throws SQLException {
		int mapId = queryExecutor.insertAndGenerateId("mapDescriptions", objectParser.MapToObjectList(mapDescription));
		List<Object> mapFileList = new ArrayList<Object>() {
			{
				add(mapId);
				add(mapFile);
			}
		};
		queryExecutor.insertToTable("mapFiles", mapFileList);
		List<Object> cityMapList = new ArrayList<Object>() {
			{
				add(cityId);
				add(mapId);
			}
		};
		queryExecutor.insertToTable("citiesMaps", cityMapList);
		return mapId;
	}

	@Override
	public Map getMapDescription(int mapId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getMapFile(int mapId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyUser(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteMapDescription(int cityId, int mapId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMapFile(int cityId, int mapId) {
		// TODO Auto-generated method stub

	}

}
