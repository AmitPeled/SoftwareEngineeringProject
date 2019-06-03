package database.execution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import database.metadata.DatabaseMetaData;
import database.metadata.DatabaseMetaData.Tables;
import database.objectParse.IParseObjects;
import maps.City;
import maps.Map;
import maps.Site;
import users.User;

/**
 * @author amit
 *
 */
@SuppressWarnings("serial")
public class GcmDataExecutor implements IGcmDataExecute {
	IExecuteQueries queryExecutor;
	IParseObjects objectParser;

	public GcmDataExecutor(IExecuteQueries queryExecutor, IParseObjects objectParser) {
		this.queryExecutor = queryExecutor;
		this.objectParser = objectParser;
	}

	@Override
	public boolean addUser(String username, String password, User user) throws SQLException {
		if (queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.users), "username", username, "*")
				.isEmpty()) {
			List<Object> userList = new ArrayList<Object>() {
				{
					add(username);
					add(password);
				}
			};
			userList.addAll(objectParser.getUserFieldsList(user));
			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.users), userList);
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyUser(String username, String password) throws SQLException {
		List<Object> valuesList = new ArrayList<Object>() {
			{
				add(username);
				add(password);
			}
		};
		List<String> namesList = new ArrayList<String>() {
			{
				add("username");
				add("password");
			}
		};
		List<List<Object>> rows = queryExecutor.selectColumnsByValues(DatabaseMetaData.getTableName(Tables.users),
				namesList, valuesList, "username, password");
		if (rows.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public int addMapToCity(int cityId, Map mapDescription, File mapFile/* , String pathToFilesFolder */)
			throws SQLException {
		int mapId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.mapsMetaDetails),
				objectParser.getMapFieldsList(mapDescription));
		List<Object> mapFileRow = new ArrayList<Object>() {
			{
				add(mapId);
				add(getBytes(mapFile));
			}
		};
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsFiles), mapFileRow);
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
		List<List<Object>> metaDetailsRows = queryExecutor
				.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId, "*");
		if (metaDetailsRows.isEmpty())
			return null;
		else {
			List<List<Object>> mapSitesRows = queryExecutor
					.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsSites), "mapId", mapId, "siteId");
			SortedSet<Site> mapsSites = new TreeSet<>();
			for (List<Object> list : mapSitesRows) {
				int siteId = (int) list.get(0);
				mapsSites.add(getSite(siteId));
			}
			return objectParser.getMap(metaDetailsRows.get(0), mapsSites); // only one row correspond to this id
		}
	}

	private Site getSite(int siteId) throws SQLException {
		List<List<Object>> metaDetailsRows = queryExecutor
				.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.sites), "siteId", siteId, "*");
		if (metaDetailsRows.isEmpty())
			return null;
		else {
			return objectParser.getSite(metaDetailsRows.get(0)); // only one site row correspond to this id
		}
	}

	@Override
	public File getMapFile(/* String pathToFilesFolder, */int mapId) throws SQLException {
		List<List<Object>> rows = queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsFiles),
				"mapId", mapId, "mapFile");
		if (rows.isEmpty())
			return null;
		else
			return (File) getObject((byte[]) rows.get(0).get(0)); // only one row correspond to this id
	}

	@Override
	public void deleteMap(int mapId) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsFiles), "mapId", mapId);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsSites), "mapId", mapId);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds), "mapId", mapId);
	}

	@Override
	public int addCity(City city) throws SQLException {
		int id = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
				objectParser.getCityMetaFieldsList(city));
//		for (Map map : city.getMaps()) {
//			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds),
//					new ArrayList<Object>() {
//						{
//							add(id);
//							add(map.getId());
//						}
//					});
//		}
//		for (Site site : city.getSites()) {
//			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
//					new ArrayList<Object>() {
//				{
//					add(id);
//					add(site.getId());
//				}
//			});
		return id;
	}

	public static byte[] getBytes(Object object) {
		byte[] objectBytes = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.close();
			bos.close();
			objectBytes = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return objectBytes;
		}
		return objectBytes;
	}

	public static Object getObject(byte[] bytes) {

		Object object = bytes;
		ByteArrayInputStream bais;
		ObjectInputStream ins;
		try {
			bais = new ByteArrayInputStream(bytes);
			ins = new ObjectInputStream(bais);
			object = (Object) ins.readObject();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	// Returns the contents of the file in a byte array.
	public static byte[] getBytesFromFile(File file) throws IOException {
		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
			throw new IOException("File is too large!");
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;

		InputStream is = new FileInputStream(file);
		try {
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
		} finally {
			is.close();
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		return bytes;
	}

	@Override
	public int addNewSiteToCity(int cityId, Site site) throws SQLException {
		int siteId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.sites),
				objectParser.getSiteFieldsList(site));
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesSitesIds), new ArrayList<Object>() {
			{
				add(cityId);
				add(siteId);
			}
		});
		return siteId;
	}

	@Override
	public void addExistingSiteToMap(int mapId, int siteId) throws SQLException {
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsSites), new ArrayList<Object>() {
			{
				add(mapId);
				add(siteId);
			}
		});
	}

	@Override
	public void DeleteSiteFromMap(int mapId, int siteId) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsSites), "siteId", siteId);
	}

	@Override
	public void DeleteSite(int siteId) throws SQLException {
		// TODO Auto-generated method stub
	}

}
