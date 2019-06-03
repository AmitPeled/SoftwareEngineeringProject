package database.execution;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.metadata.databaseMetaData;
import database.metadata.databaseMetaData.Tables;
import database.objectParse.IParseObjects;
import maps.Map;
import users.User;

/**
 * @author amit
 *
 */
public class GcmDatabaseExecutor implements IGcmDataExecute {
	IExecuteQueries queryExecutor;
	IParseObjects objectParser;

	@SuppressWarnings("serial")
	@Override
	public boolean addUser(String username, String password, User user) throws SQLException {
		if (queryExecutor
				.selectColumnsByValue(databaseMetaData.getTableName(Tables.users), "username", username, "username")
				.isEmpty()) {
			List<Object> userList = new ArrayList<Object>() {
				{
					add(username);
					add(password);
					add(user);
				}
			};
			queryExecutor.insertToTable(databaseMetaData.getTableName(Tables.users), userList);
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyUser(String username, String password) throws SQLException {
		@SuppressWarnings("serial")
		List<Object> valuesList = new ArrayList<Object>() {
			{
				add(username);
				add(password);
			}
		};
		@SuppressWarnings("serial")
		List<String> namesList = new ArrayList<String>() {
			{
				add("username");
				add("password");
			}
		};
		List<List<Object>> rows = queryExecutor.selectColumnsByValues(databaseMetaData.getTableName(Tables.users),
				namesList, valuesList, "username, password");
		if (rows.isEmpty())
			return false;
		else
			return true;
	}

	@SuppressWarnings("serial")
	@Override
	public int addMap(int cityId, Map mapDescription, File mapFile) throws SQLException {
		int mapId = queryExecutor.insertAndGenerateId(databaseMetaData.getTableName(Tables.mapsDetails),
				objectParser.getMapFieldsList(mapDescription));
		List<Object> mapFileRow = new ArrayList<Object>() {
			{
				add(mapId);
				add(mapFile);
			}
		};
		queryExecutor.insertToTable(databaseMetaData.getTableName(Tables.mapsFiles), mapFileRow);
		List<Object> cityRow = new ArrayList<Object>() {
			{
				add(cityId);
				add(mapId);
			}
		};
		queryExecutor.insertToTable("citiesMaps", cityRow);
		return mapId;
	}

	@Override
	public Map getMapDetails(int mapId) throws SQLException {
		List<List<Object>> rows = queryExecutor.selectColumnsByValue(databaseMetaData.getTableName(Tables.mapsDetails), "id", mapId, "*");
		if (rows.isEmpty())
			return null;
		else
			return objectParser.getMap(rows.get(0)); // only one row correspond to this id
	}

	@Override
	public File getMapFile(int mapId) throws SQLException {
		List<List<Object>> rows = queryExecutor.selectColumnsByValue(databaseMetaData.getTableName(Tables.mapsFiles), "id", mapId, "mapFile");
		if (rows.isEmpty())
			return null;
		else
			return (File) rows.get(0).get(0); // only one row correspond to this id
	}

	@Override
	public void deleteMap(int cityId, int mapId) throws SQLException {
		queryExecutor.deleteValueFromTable(databaseMetaData.getTableName(Tables.mapsDetails), "id", mapId);
		queryExecutor.deleteValueFromTable(databaseMetaData.getTableName(Tables.mapsFiles), "id", mapId);
		queryExecutor.deleteValueFromTable(databaseMetaData.getTableName(Tables.citiesMapsIds), "id", mapId);
	}
}
