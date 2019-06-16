package database.execution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.cj.xdevapi.Table;

import dataAccess.customer.PurchaseHistory;
import dataAccess.generalManager.Report;
import dataAccess.users.PurchaseDetails;
import database.metadata.DatabaseMetaData;
import database.metadata.DatabaseMetaData.Tables;
import database.objectParse.IParseObjects;
import database.objectParse.Status;
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
@SuppressWarnings("serial")
public class GcmDataExecutor implements
		IGcmDataExecute/* , IGcmCustomerExecutor, */ /* IGcmEditorExecutor, *//* IGcmContentManagerExecutor */ {
	IExecuteQueries queryExecutor;
	IParseObjects objectParser;

	public GcmDataExecutor(IExecuteQueries queryExecutor, IParseObjects objectParser) {
		this.queryExecutor = queryExecutor;
		this.objectParser = objectParser;
	}

	@Override
	public boolean addUser(String username, String password, User user) throws SQLException {
		return addUserToTable(username, password, user, DatabaseMetaData.getTableName(Tables.customerUsers));
	}

	public boolean addUserToTable(String username, String password, User user, String tableName) throws SQLException {
		if (queryExecutor.selectColumnsByValue(tableName, "username", username, "*").isEmpty()) {
			List<Object> userList = new ArrayList<Object>() {
				{
					add(username);
					add(password);
				}
			};
			userList.addAll(objectParser.getUserFieldsList(user));
			queryExecutor.insertToTable(tableName, userList);
			return true;
		}
		return false;
	}

	@Override
	public RequestState verifyUser(String username, String password) throws SQLException {
		if (username != null && password != null) {

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
			boolean isEditor = !queryExecutor.selectColumnsByValues(DatabaseMetaData.getTableName(Tables.editorUsers),
					namesList, valuesList, "username, password").isEmpty();
			boolean isCustomer = !queryExecutor
					.selectColumnsByValues(DatabaseMetaData.getTableName(Tables.customerUsers), namesList, valuesList,
							"username, password")
					.isEmpty();
			boolean isCManager = !queryExecutor
					.selectColumnsByValues(DatabaseMetaData.getTableName(Tables.contentManagerUsers), namesList,
							valuesList, "username, password")
					.isEmpty();
			boolean isGManager = !queryExecutor
					.selectColumnsByValues(DatabaseMetaData.getTableName(Tables.generalManagerUsers), namesList,
							valuesList, "username, password")
					.isEmpty();

			if (username.equals("editor") && password.equals("editor") || isEditor) {
				return RequestState.editor;
			} else if (username.equals("c-manager") && password.equals("c-manager") || isCManager) {
				return RequestState.contentManager;
			} else if (username.equals("manager") && password.equals("manager") || isGManager) {
				return RequestState.manager;
			} else if (isCustomer)
				return RequestState.customer;
		}
		return RequestState.wrongDetails;

	}

//private void addNewContent(String tableName, List<Object> content) throws SQLException {
//	content.add(1);
//	queryExecutor.insertToTable(tableName, content);
//}
//private void updateContent(String tableName, List<Object> content) throws SQLException {
//	content.add(2);
//	queryExecutor.deleteValueFromTable(tableName, objectName, object);(tableName, content);
//}
	@Override
	public int addMapToCity(int cityId, Map mapDescription, File mapFile/* , String pathToFilesFolder */)
			throws SQLException {
		return addMapToCityByStatus(cityId, mapDescription, mapFile, Status.toAdd);
//		int mapId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.mapsMetaDetails),
//				objectParser.getMapMetaFieldsList(mapDescription), Status.addToCity);
//		List<Object> mapFileRow = new ArrayList<Object>() {
//			{
//				add(mapId);
//				add(getBytes(mapFile));
//			}
//		};
//		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsFiles), mapFileRow, Status.toAdd);
//		List<Object> cityRow = new ArrayList<Object>() {
//			{
//				add(cityId);
//				add(mapId);
//			}
//		};
//		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds), cityRow, Status.toAdd);
//		return mapId;

	}

	public int addMapToCityByStatus(int cityId, Map mapDescription, File mapFile, Status status) throws SQLException {
		int mapId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.mapsMetaDetails),
				objectParser.getMapMetaFieldsList(mapDescription), status);
		List<Object> mapFileRow = new ArrayList<Object>() {
			{
				add(mapId);
				add(getBytes(mapFile));
			}
		};
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsFiles), mapFileRow, status);
		List<Object> cityRow = new ArrayList<Object>() {
			{
				add(cityId);
				add(mapId);
			}
		};
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds), cityRow, status);
		return mapId;
	}

	@Override
	public Map getMapDetails(int mapId) throws SQLException {
		return getMapDetailsByStatus(mapId, Status.published);
	}

	public Map getMapDetailsByStatus(int mapId, Status status) throws SQLException {
		List<List<Object>> metaDetailsRows = queryExecutor.selectColumnsByValue(
				DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId, "*", status);
		if (metaDetailsRows.isEmpty())
			return null;
		else {
			List<Integer> mapSitesIds = toIdList(queryExecutor.selectColumnsByValue(
					DatabaseMetaData.getTableName(Tables.mapsSites), "mapId", mapId, "siteId", status));
			List<Site> mapSites = new ArrayList<>();
			for (int siteId : mapSitesIds) {
				mapSites.add(getSite(siteId));
			}
			List<Integer> mapToursIds = toIdList(queryExecutor.selectColumnsByValue(
					DatabaseMetaData.getTableName(Tables.mapsTours), "mapId", mapId, "tourId", status));

			List<Tour> mapTours = new ArrayList<>();
			for (int tourId : mapToursIds) {
				mapTours.add(getTour(tourId));
			}
			return objectParser.getMap(metaDetailsRows.get(0), mapSites, mapTours); // only one row correspond to this
																					// id }
		}
	}

	public Site getSite(int siteId) throws SQLException {
		return getSite(siteId, Status.published);
	}

	public Site getSite(int siteId, Status status) throws SQLException {
		List<List<Object>> siteRows = queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.sites),
				"siteId", siteId, "*", status);
		if (siteRows.isEmpty())
			return null;
		else
			return objectParser.getSite(siteRows.get(0)); // only one site row correspond to this id
	}

	@SuppressWarnings("unchecked")
	public Tour getTour(int tourId) throws SQLException {
		List<List<Object>> siteRows = queryExecutor.selectColumnsByValue(
				DatabaseMetaData.getTableName(Tables.toursMetaDetails), "tourId", tourId, "*", Status.published);
		if (siteRows.isEmpty())
			return null;
		else {
			List<List<Object>> siteIdsAndDurances = queryExecutor.selectColumnsByValue(
					DatabaseMetaData.getTableName(Tables.tourSitesIdsAndDurance), "tourId", tourId,
					"siteId, siteDurance", Status.published);

			List<Integer> siteIds = (List<Integer>) (Object) toListOfColumnNum(siteIdsAndDurances, 1);

			List<Integer> siteDurances = (List<Integer>) (Object) toListOfColumnNum(siteIdsAndDurances, 2);

			List<Site> sites = getSitesByIds(siteIds);
			return objectParser.getTour(siteRows.get(0), sites, siteDurances); // only one site row correspond to this
																				// id
		}
	}

	private List<Site> getSitesByIds(List<Integer> siteIds) throws SQLException {
		List<Site> sites = new ArrayList<>();
		for (int siteId : siteIds)
			sites.add(getSite(siteId));
		return sites;
	}

	@Override
	public File getMapFile(int mapId) throws SQLException {
		List<List<Object>> rows = queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsFiles),
				"mapId", mapId, "mapFile");
		if (rows.isEmpty())
			return null;
		else
			return (File) getObject((byte[]) rows.get(0).get(0)); // only one row correspond to this id
	}

	@Override
	public void deleteMapEdit(int mapId) throws SQLException {
		List<Object> objectsValues = fillWithNulls(objectParser.getMapMetaFieldsNames().size());
		objectsValues.set(0, mapId);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), objectsValues,
				Status.toDelete);
	}

	List<Object> fillWithNulls(int size) {
		List<Object> list = new ArrayList<>();
		while (--size >= 0)
			list.add(null);
		return list;
	}

	public void deleteMapByStatus(int mapId, Status status) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId,
				status);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsFiles), "mapId", mapId, status);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsSites), "mapId", mapId, status);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds), "mapId", mapId, status);
	}

	public void deleteMap(int mapId) throws SQLException {
		deleteMapByStatus(mapId, Status.published);
		deleteMapByStatus(mapId, Status.toAdd);
		deleteMapByStatus(mapId, Status.toDelete);
		deleteMapByStatus(mapId, Status.toUpdate);
	}

	@Override
	public int addCity(City city) throws SQLException {
		int id = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
				objectParser.getCityMetaFieldsList(city), Status.toAdd);
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

//	@Override
//	public int addCityWithInitialMap(City city, Map mapDescription, File mapFile) throws SQLException {
//		int cityId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
//				objectParser.getCityMetaFieldsList(city));
//		int mapId = addMapToCity(cityId, mapDescription, mapFile);
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
//		return mapId;
//	}

	private static byte[] getBytes(Object object) {
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

	private static Object getObject(byte[] bytes) {

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
//	private static byte[] getBytesFromFile(File file) throws IOException {
//		// Get the size of the file
//		long length = file.length();
//
//		// You cannot create an array using a long type.
//		// It needs to be an int type.
//		// Before converting to an int type, check
//		// to ensure that file is not larger than Integer.MAX_VALUE.
//		if (length > Integer.MAX_VALUE) {
//			// File is too large
//			throw new IOException("File is too large!");
//		}
//
//		// Create the byte array to hold the data
//		byte[] bytes = new byte[(int) length];
//
//		// Read in the bytes
//		int offset = 0;
//		int numRead = 0;
//
//		InputStream is = new FileInputStream(file);
//		try {
//			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
//				offset += numRead;
//			}
//		} finally {
//			is.close();
//		}
//
//		// Ensure all the bytes have been read in
//		if (offset < bytes.length) {
//			throw new IOException("Could not completely read file " + file.getName());
//		}
//		return bytes;
//	}

	@Override
	public int addNewSiteToCity(int cityId, Site site) throws SQLException {
		return addNewSiteToCityByStatus(cityId, site, Status.toAdd);
	}

	public int addNewSiteToCityByStatus(int cityId, Site site, Status status) throws SQLException {
		int siteId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.sites),
				objectParser.getSiteFieldsList(site), status);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesSitesIds), new ArrayList<Object>() {
			{
				add(cityId);
				add(siteId);
			}
		}, status);
		return siteId;
	}

	@Override
	public void addExistingSiteToMap(int mapId, int siteId) throws SQLException {
		addSiteToMapByStatus(mapId, siteId, Status.toAdd);
	}

	private void addSiteToMapByStatus(int mapId, int siteId, Status status) throws SQLException {
		Site site = getSite(siteId);
		if (site != null) {
			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.sites),
					objectParser.getSiteFieldsList(site), status);
			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsSites), new ArrayList<Object>() {
				{
					add(mapId);
					add(siteId);

				}
			}, status);
		}
	}

	@Override
	public void deleteSiteFromMap(int mapId, int siteId) throws SQLException {
		queryExecutor.deleteValuesFromTable(DatabaseMetaData.getTableName(Tables.mapsSites), new ArrayList<String>() {
			{
				add("mapId");
				add("siteId");
			}
		}, new ArrayList<Object>() {
			{
				add(mapId);
				add(siteId);
			}
		});
	}

	@Override
	public void deleteSite(int siteId) throws SQLException {
		List<Object> listToInsert = new ArrayList<Object>() {
			{
				add(siteId);
			}
		};
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsSites), listToInsert, Status.toDelete);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesSitesIds), listToInsert,
				Status.toDelete);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.sites), listToInsert, Status.toDelete);
	}

	public void deleteSiteFromTables(int siteId) throws SQLException {
		deleteSiteByStatus(siteId, Status.published);
		deleteSiteByStatus(siteId, Status.toAdd);
		deleteSiteByStatus(siteId, Status.toUpdate);
		deleteSiteByStatus(siteId, Status.toDelete);
	}

	public void deleteSiteByStatus(int siteId, Status status) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsSites), "siteId", siteId);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.citiesSitesIds), "siteId", siteId);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.sites), "siteId", siteId);
	}

	private List<Map> getMapsByCityField(String fieldName, Object fieldVal, boolean withPartialField)
			throws SQLException {

		List<Integer> cityIds;
		if (withPartialField) {

			cityIds = toIdList(
					queryExecutor.selectColumnsByPartialValue(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
							fieldName, "%" + (String) fieldVal + "%", "cityId", Status.published));
		} else {
			cityIds = toIdList(
					queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
							fieldName, fieldVal, "cityId", Status.published));

		}
		List<Map> maps = new ArrayList<>();
		List<List<Object>> mapIdRows = new ArrayList<>();
		for (int cityId : cityIds) {
			mapIdRows.addAll(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMapsIds),
					"cityId", cityId, "mapId", Status.published));

		}
		List<Integer> mapsIds = toIdList(mapIdRows);
		for (int mapId : mapsIds) {
			maps.add(getMapDetails(mapId));

		}

		return maps;
	}

	private List<Map> getMapsBySiteField(String fieldName, Object fieldVal, boolean withPartialField)
			throws SQLException {
		List<Integer> sitesIds;
		if (withPartialField)
			sitesIds = toIdList(queryExecutor.selectColumnsByPartialValue(DatabaseMetaData.getTableName(Tables.sites),
					fieldName, "%" + (String) fieldVal + "%", "siteId", Status.published));
		else
			sitesIds = toIdList(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.sites),
					fieldName, fieldVal, "siteId", Status.published));

		List<Map> maps = new ArrayList<>();
		List<List<Object>> mapIdRows = new ArrayList<>();
		for (int siteId : sitesIds) {
			mapIdRows.addAll(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsSites),
					"siteId", siteId, "mapId", Status.published));
		}
		List<Integer> mapsIds = toIdList(mapIdRows);
		for (int mapId : mapsIds)
			maps.add(getMapDetails(mapId));
		return maps;
	}

	private List<Integer> toIdList(List<List<Object>> idsRows) {
		List<Integer> ids = new ArrayList<>();
		for (List<Object> idRow : idsRows)
			if (!ids.contains((int) idRow.get(0)))
				ids.add((int) idRow.get(0));
		return ids;
	}

	private List<Object> toListOfColumnNum(List<List<Object>> listRows, int column) {
		List<Object> rows = new ArrayList<>();
		for (List<Object> row : listRows)
			rows.add(row.get(column - 1));
		return rows;
	}

	@Override
	public List<Map> getMapsByCityName(String cityName) throws SQLException {
		List<Map> maps = getMapsByCityField("cityName", cityName, false);
		return maps;
	}

	@Override
	public List<Map> getMapsBySiteName(String siteName) throws SQLException {
		return getMapsBySiteField("siteName", siteName, false);
	}

	@Override
	public List<Map> getMapsByDescription(String description) throws SQLException {
		List<Map> mapsByDescription = getMapsByCityField("cityDescription", description, true);
		mapsByDescription.addAll(getMapsBySiteField("siteDescription", description, true));
		return mapsByDescription;
	}

	@Override
	public User getUserDetails(String username) throws SQLException {
		return objectParser.getUser(queryExecutor
				.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.customerUsers), "username", username, "*")
				.get(0));
	}

	@Override
	public List<Site> getCitySites(int cityId) throws SQLException {
		List<Integer> siteIds = toIdList(queryExecutor.selectColumnsByValue(
				DatabaseMetaData.getTableName(Tables.citiesSitesIds), "cityId", cityId, "siteId", Status.published));
		return getSitesByIds(siteIds);
	}

	@Override
	public void updateMap(int mapId, Map newMap) throws SQLException {
		List<Object> mapRow = objectParser.getMapMetaFieldsList(newMap);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId);
		mapRow.set(0, mapId);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), mapRow, Status.toUpdate);
	}

	@Override
	public void UpdateSite(int siteId, Site newSite) throws SQLException {
		List<Object> siteRow = objectParser.getSiteFieldsList(newSite);
		siteRow.set(0, siteId);
		deleteSite(siteId);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.sites), siteRow, Status.toUpdate);
	}

//	private void deleteSite(int siteId) throws SQLException {
//		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.sites), "siteId", siteId);
//	}

	@Override
	public City getCityByMapId(int mapId) throws SQLException {
		int cityId = (int) queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMapsIds),
				"mapId", mapId, "cityId", Status.published).get(0).get(0);
		return getCityById(cityId);
	}

	private City getCityById(int cityId) throws SQLException {
		return getCityById(cityId, Status.published);
	}

	private City getCityById(int cityId, Status status) throws SQLException {
		List<List<Object>> list = queryExecutor.selectColumnsByValue(
				DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "cityId", cityId, "*", status);
		if (!list.isEmpty())
			return objectParser.getCityByMetaFields(list.get(0));
		else
			return null;

	}

	@Override
	public void addExistingSiteToTour(int tourId, int siteId, int durnace) throws SQLException {
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.sites),
				objectParser.getSiteFieldsList(getSite(siteId)), Status.toAdd);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.tourSitesIdsAndDurance),
				new ArrayList<Object>() {
					{
						add(tourId);
						add(siteId);
						add(durnace);

					}
				}, Status.toAdd);
	}

	public void addSiteToTourByStatus(int tourId, int siteId, int durnace) throws SQLException {
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.sites),
				objectParser.getSiteFieldsList(getSite(siteId)), Status.toAdd);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.tourSitesIdsAndDurance),
				new ArrayList<Object>() {
					{
						add(tourId);
						add(siteId);
						add(durnace);

					}
				}, Status.toAdd);
	}

	@Override
	public int addNewTourToCity(int cityId, Tour tour) throws SQLException {
		int tourId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.toursMetaDetails),
				objectParser.getTourMetaFieldsList(tour), Status.toAdd);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesTours), new ArrayList<Object>() {
			{
				add(cityId);
				add(tourId);
			}
		}, Status.toAdd);
		return tourId;
	}

	@Override
	public void updateCity(int cityId, City city) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "cityId", cityId);
		List<Object> cityRow = objectParser.getCityMetaFieldsList(city);
		cityRow.set(0, city);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), cityRow, Status.toUpdate);
	}

	@Override
	public void addExistingTourToMap(int mapId, int tourId) throws SQLException {
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.toursMetaDetails),
				objectParser.getTourMetaFieldsList(getTour(tourId)), Status.toAdd);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsTours), new ArrayList<Object>() {
			{
				add(mapId);
				add(tourId);
			}
		}, Status.toAdd);
	}

	@Override
	public void actionMapAddEdit(Map map, boolean action) throws SQLException {
		if (map != null) {
			queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId",
					map.getId(), Status.toAdd);
			File file = getMapFile(map.getId());
			queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsFiles), "mapId", map.getId(),
					Status.toAdd);
			if (action) {
				City city = getCitiesObjectAddedTo(map.getId()).get(0);
				addMapToCityByStatus(city.getId(), map, file, Status.published);
			}

		}
	}

	@Override
	public void actionMapUpdateEdit(Map map, boolean action) throws SQLException {
		List<String> objectNames = objectParser.getMapMetaFieldsNames();
		List<Object> objectValues = objectParser.getMapMetaFieldsList(map);
		queryExecutor.deleteValuesFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), objectNames,
				objectValues, Status.toUpdate);
		if (action) {
			queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId",
					map.getId(), Status.published);
			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails),
					objectParser.getMapMetaFieldsList(map), Status.published);
		}
	}

	@Override
	public void actionMapDeleteEdit(Map map, boolean action) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", map.getId(),
				Status.toDelete);
		if (action) {
			deleteMap(map.getId());
		}

	}

	@Override
	public void actionCityDeleteEdit(City city, boolean action) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "cityId",
				city.getId(), Status.toDelete);
		if (action) {
			deleteCity(city.getId());
//			queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "cityId",
//					city.getId(), Status.published);
		}

	}

	List<Map> getMapsByStatus(Status status) throws SQLException {
		List<Integer> mapIds = toIdList(
				queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "status",
						DatabaseMetaData.getStatus(status), "*"));
		return toMapsByIds(mapIds, status);
	}

	List<City> getCitiesByStatus(Status status) throws SQLException {
		List<Integer> cityIds = toIdList(
				queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "status",
						DatabaseMetaData.getStatus(status), "*"));
		List<City> cityObjects = new ArrayList<>();
		cityIds.forEach((cityId) -> {
			try {
				City city = getCityById(cityId, status);
				if (cityId != null)
					cityObjects.add(city);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		System.err.println(cityObjects.get(0).getDescription());
		return cityObjects;
	}

	List<Site> getSitesByStatus(Status status) throws SQLException {
		List<Integer> siteIds = toIdList(
				queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.sites), "status",
						DatabaseMetaData.getStatus(status), "*"));
		System.err.println("siteId.size(): " + siteIds.size());
		List<Site> sites = new ArrayList<>();
		siteIds.forEach((siteId) -> {
			try {
				Site site = getSite(siteId, status);
				if (site != null) {
					sites.add(site);
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		});
		System.err.println("sites.size(): " + sites.size());

		return sites;
	}

	private List<Tour> getToursByStatus(Status status) throws SQLException {
		List<Integer> tourIds = toIdList(
				queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.toursMetaDetails), "status",
						DatabaseMetaData.getStatus(status), "*"));
		List<Tour> tours = new ArrayList<>();
		tourIds.forEach((tourId) -> {
			try {
				tours.add(getTour(tourId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return tours;
	}

	@Override
	public List<Map> getMapsAddEdits() throws SQLException {
//		List<Map> maps = new ArrayList<>();
//		List<Integer> mapIds = new ArrayList<>();
//		List<Integer> siteIds = new ArrayList<>();
//		List<Integer> tourIds = new ArrayList<>();
//
//		List<List<Object>> mapAndSitesIds = queryExecutor.selectColumnsByValue(
//				DatabaseMetaData.getTableName(Tables.mapsSites), "status", Status.toAdd, "mapId, siteId");
//		mapIds = (List<Integer>) (Object) toListOfColumnNum(mapAndSitesIds, 1);
//		siteIds = (List<Integer>) (Object) toListOfColumnNum(mapAndSitesIds, 2);
//		for (int i = 0; i < mapIds.size(); i++) {
//			Map map = getMapDetails(mapIds.get(i));
//			map.addSite(getSite(siteIds.get(i)));
//			maps.add(map);
//		}
//		List<List<Object>> mapAndTourIds = queryExecutor.selectColumnsByValue(
//				DatabaseMetaData.getTableName(Tables.mapsTours), "status", Status.toAdd, "mapId, tourId");
//		mapIds = (List<Integer>) (Object) toListOfColumnNum(mapAndTourIds, 1);
//		tourIds = (List<Integer>) (Object) toListOfColumnNum(mapAndTourIds, 2);
//		for (int i = 0; i < mapIds.size(); i++) {
//			Map map = getMapDetails(mapIds.get(i));
//			map.addTour(getTour(tourIds.get(i)));
//			maps.add(map);
//		}
//		return maps;
		return getMapsByStatus(Status.toAdd);
	}

	@Override
	public List<Map> getMapsUpdateEdits() throws SQLException {
		return getMapsByStatus(Status.toUpdate);

	}

	@Override
	public List<Map> getMapsDeleteEdits() throws SQLException {
		return getMapsByStatus(Status.toDelete);
	}

	@Override
	public List<Tour> getToursAddEdits() throws SQLException {
		return getToursByStatus(Status.toAdd);

	}

	@Override
	public List<Tour> getToursUpdateEdits() throws SQLException {
		return getToursByStatus(Status.toUpdate);

	}

	@Override
	public List<Tour> getToursDeleteEdits() throws SQLException {
		return getToursByStatus(Status.toDelete);
	}

	@Override
	public List<Site> getSitesAddEdits() throws SQLException {
		return getSitesByStatus(Status.toAdd);
	}

	@Override
	public List<Site> getSitesUpdateEdits() throws SQLException {
		return getSitesByStatus(Status.toUpdate);

	}

	@Override
	public List<Site> getSitesDeleteEdits() throws SQLException {
		return getSitesByStatus(Status.toDelete);
	}

	@Override
	public List<City> getCitiesAddEdits() throws SQLException {
		return getCitiesByStatus(Status.toAdd);
//		List<City> cities = new ArrayList<>();
//		List<List<Object>> lists = queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMapsIds), "status", Status.toAdd, "cityId, mapIds");
//		List<Integer> cityIds = (List<Integer>)(Object)toListOfColumnNum(lists, 1);
//		List<Integer> mapId = (List<Integer>)(Object)toListOfColumnNum(lists, 2);
//		
//		cityIds.addAll(toIdList(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesSitesIds), "status", Status.toAdd, "cityId")));
//		cityIds.addAll(toIdList(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.cityTours), "status", Status.toAdd, "cityId")));
//		return cities;
	}

	@Override
	public List<City> getCitiesUpdateEdits() throws SQLException {
		return getCitiesByStatus(Status.toUpdate);

	}

	@Override
	public List<City> getCitiesDeleteEdits() throws SQLException {
		return getCitiesByStatus(Status.toDelete);
	}

	List<Map> getMapsObjectContainedIn(int objectId, Status status) {
		List<Integer> mapsIds = new ArrayList<>();
		return toMapsByIds(mapsIds, status);
	}

	List<Map> toMapsByIds(List<Integer> mapsIds, Status status) {
		List<Map> mapsObjects = new ArrayList<>();
		mapsIds.forEach((mapId) -> {
			try {
				mapsObjects.add(getMapDetails(mapId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return mapsObjects;
	}

	@Override
	public List<Map> getMapsObjectAddedTo(int contentId) throws SQLException {
		List<Integer> mapIds = new ArrayList<>();
		mapIds.addAll(toIdList(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsTours),
				"tourId", contentId, "mapId", Status.toAdd)));
		mapIds.addAll(toIdList(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsSites),
				"siteId", contentId, "mapId", Status.toAdd)));
		return toMapsByIds(mapIds, Status.toAdd);
	}

	@Override
	public List<City> getCitiesObjectAddedTo(int contentId) throws SQLException {
		List<Integer> citiesIds = new ArrayList<>();
		citiesIds
				.addAll(toIdList(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMapsIds),
						"mapId", contentId, "cityId", Status.toAdd)));
		citiesIds.addAll(toIdList(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesTours),
				"tourId", contentId, "cityId", Status.toAdd)));
		citiesIds.addAll(toIdList(queryExecutor.selectColumnsByValue(
				DatabaseMetaData.getTableName(Tables.citiesSitesIds), "siteId", contentId, "cityId", Status.toAdd)));
		return toCities(citiesIds, Status.toAdd);
	}

	private List<City> toCities(List<Integer> citiesIds, Status status) {
		List<City> cities = new ArrayList<>();
		citiesIds.forEach((cityId) -> {
			try {
				cities.add(getCityById(cityId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return cities;
	}

	@Override
	public List<Tour> getToursObjectAddedTo(int contentId) throws SQLException {
		List<List<Object>> toursIds = queryExecutor.selectColumnsByValue(
				DatabaseMetaData.getTableName(Tables.tourSitesIdsAndDurance), "siteId", contentId, "tourId",
				Status.toAdd);
		if (toursIds.isEmpty())
			return null;
		else
			return getToursByIds(toIdList(toursIds), Status.toAdd);
	}

	private List<Tour> getToursByIds(List<Integer> idList, Status toadd) {
		List<Tour> tours = new ArrayList<>();
		idList.forEach((tourId) -> {
			try {
				tours.add(getTour(tourId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return tours;
	}

	@Override
	public void actionTourAddEdit(Site site, boolean action) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionTourUpdateEdit(Site site, boolean action) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteAddEdit(Site site, boolean action) throws SQLException {
		int siteId = site.getId();
		List<City> cities = getCitiesObjectAddedTo(siteId);
		List<Map> maps = getMapsObjectAddedTo(siteId);
		List<Tour> tours = getToursObjectAddedTo(siteId);
		deleteSiteFromTables(siteId);
		if (!cities.isEmpty())
			addNewSiteToCityByStatus(cities.get(0).getId(), site, Status.published);
		addSiteToMapsByStatus(siteId, maps, Status.published);
		addSiteToToursByStatus(siteId, tours, Status.published);

		deleteSiteByStatus(siteId, Status.toAdd);
	}

	private void addSiteToMapsByStatus(int siteId, List<Map> maps, Status status) {
		maps.forEach((map) -> {
			try {
				addSiteToMapByStatus(map.getId(), siteId, status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	private void addSiteToToursByStatus(int siteId, List<Tour> tours, Status status) {
		tours.forEach((tour) -> {
			try {

				addSiteToTourByStatus(tour.getId(), siteId, status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	private void addSiteToTourByStatus(int tourId, int siteId, Status status) throws SQLException {
		Site site = getSite(siteId);
		if (site != null) {
			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.sites),
					objectParser.getSiteFieldsList(site), status);
			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.tourSitesIdsAndDurance),
					new ArrayList<Object>() {
						{
							add(tourId);
							add(siteId);
							add(1);
						}
					}, status);
		}
	}

	public void actionTourDeleteEdit(Site site, boolean action) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCity(int cityId) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCityAddEdit(City city, boolean action) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCityUpdateEdit(City city, boolean action) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteUpdateEdit(Site site, boolean action) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteDeleteEdit(Site site, boolean action) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void editCityPrice(int cityId, double newPrice) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public double getMembershipPrice(int cityId, int timeInterval, String username) throws SQLException {
		// need to check if he buy that map befor -> 10% disscount

		List<List<Object>> checkForDisscount = queryExecutor.selectColumnsByValue("purchaseDeatailsHistory", "username",
				username, "purchaseDate");
		List<List<Object>> list = queryExecutor.selectColumnsByValue("citysesPrices", "cityId", cityId,
				"Month" + timeInterval);
		if (list.isEmpty()) {
			return -1;
		}

		if (checkForDisscount.isEmpty()) {
			return (double) list.get(0).get(0);
		} else {
			return (double) list.get(0).get(0) * 0.9;
		}
	}

	@Override
	public boolean repurchaseMembershipBySavedDetails(int cityId, int timeInterval, String username)
			throws SQLException {

		// need to check if patment is good -> nevr happen

		// subsciption
		if (timeInterval > 0) {
			List<Object> pDetails = new ArrayList<Object>() {
				{

					int days = 30 * timeInterval;
					java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Date endDate = addDays(startDate, days);
					add(username);
					add(cityId);
					add(startDate);
					add(false);
					add(timeInterval);
					add(endDate);
				}
			};
			try {
				String tableToUpdate = "subscribes";
				queryExecutor.insertToTable("purchaseDeatailsHistory", pDetails);

				updateMangerReports(cityId, tableToUpdate);

			} catch (SQLException e) {
				return false;
			}
		} else {
			// oneTimePurchase
			List<Object> pDetails = new ArrayList<Object>() {
				{

					int days = 30 * timeInterval;
					java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Date endDate = addDays(startDate, days);
					add(username);
					add(cityId);
					add(startDate);
					add(true);
					add(timeInterval);
					add(endDate);
				}
			};
			try

			{
				String tableToUpdate = "oneTimePurchase";
				queryExecutor.insertToTable("purchaseDeatailsHistory", pDetails);
				updateMangerReports(cityId, tableToUpdate);
			} catch (SQLException e) {
				return false;
			}

		}

		// if seccuss
		return true;
	}

	@Override
	public File downloadMap(int mapId, String username) throws SQLException {
		// TODO Auto-generated method stub

		return getMapFile(mapId);
	}

	@Override
	public List<Map> getPurchasedMaps(String username) throws SQLException {
		// having list of all the purchase cityId that the user bought
		List<List<Object>> cityIdList = queryExecutor.selectColumnsByValue("purchaseDeatails", "cityId", username,
				"cityId");
		List<Integer> cityId = toIdList(cityIdList);

		String tableToUpdate = "downloads";
		// getting all the maps id
		List<Map> maps = new ArrayList<>();
		List<List<Object>> mapsIdList = new ArrayList<List<Object>>();

		for (int i : cityId) {
			mapsIdList.addAll(queryExecutor.selectColumnsByValue("citiesMaps", "cityId", i, "mapId"));
			updateMangerReports(i, tableToUpdate);

		}
		List<Integer> mapsId = toIdList(mapsIdList);
		for (int i : mapsId) {
			maps.add(getMapDetails(i));
		}

		return maps;
	}

	@Override
	public double getOneTimePurchasePrice(int cityId) throws SQLException {

		List<List<Object>> list = queryExecutor.selectColumnsByValue("citysesPrices", "cityId", cityId,
				"oneTimePurchase");
		if (list.isEmpty()) {
			return -1;
		}

		return (double) list.get(0).get(0);
	}

	@Override
	public String getSavedCreditCard(String username) throws SQLException {
		List<List<Object>> list = queryExecutor.selectColumnsByValue("costumerPurchaseDeatils", "username", username,
				"creditCard");
		if (list.isEmpty()) {
			return "";
		}
		String res = (String) list.get(0).get(0);
		res = "XXXX-XXXX-XXXX-" + res.substring(res.length() - 4);
		return res;
	}

	@Override
	public boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails,
			String username) throws SQLException {
		// if seccess -> validate payment (not really can happen)

		// update user purchaseDetails in his table , update report table
		List<List<Object>> checkIfAlreadyExistUser = queryExecutor.selectColumnsByValue("purchaseDeatailsHistory",
				"username", username, "purchaseDate");
		if (checkIfAlreadyExistUser.isEmpty()) {
			List<Object> cotumerPurchaseDetails = new ArrayList<Object>() {
				{
					add(username);
					add(purchaseDetails.getFirstname());
					add(purchaseDetails.getLastname());
					add(purchaseDetails.getCreditCard());
					add(purchaseDetails.getCvv());
					add(purchaseDetails.getCardExpireDate());
				}
			};
			try {
				queryExecutor.insertToTable("costumerPurchaseDetails", cotumerPurchaseDetails);
			} catch (SQLException e) {
				// else give null
				return false;
			}
		}

		// update purchaseDeatailsHistory so can know all purchase history
		if (timeInterval > 0) {
			List<Object> pDetails = new ArrayList<Object>() {
				{

					int days = 30 * timeInterval;
					java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Date endDate = addDays(startDate, days);
					add(username);
					add(cityId);
					add(startDate);
					add(false);
					add(timeInterval);
					add(endDate);
				}
			};
			try

			{
				String tableToUpdate = "downloads";
				queryExecutor.insertToTable("purchaseDeatailsHistory", pDetails);
				updateMangerReports(cityId, tableToUpdate);

			} catch (SQLException e) {
				return false;
			}
		} else {
			// oneTimePurchase
			List<Object> pDetails = new ArrayList<Object>() {
				{

					int days = 30 * timeInterval;
					java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					java.sql.Date endDate = addDays(startDate, days);
					add(username);
					add(cityId);
					add(startDate);
					add(true);
					add(timeInterval);
					add(endDate);
				}
			};
			try {
				String tableToUpdate = "oneTimePurchase";
				queryExecutor.insertToTable("purchaseDeatailsHistory", pDetails);

				updateMangerReports(cityId, tableToUpdate);
			} catch (

			SQLException e) {
				return false;
			}

		}
		// if seccuss
		return true;
	}

	@Override
	public List<File> purchaseCityOneTime(int cityId, PurchaseDetails purchaseDetails, String username)
			throws SQLException {

		int timeInterval = 0;

		// validate details and insert to costumerpurchasedtails table
		List<List<Object>> checkIfAlreadyExistUser = queryExecutor.selectColumnsByValue("purchaseDeatailsHistory",
				"username", username, "purchaseDate");
		if (checkIfAlreadyExistUser.isEmpty()) {
			List<Object> cotumerPurchaseDetails = new ArrayList<Object>() {
				{
					add(username);
					add(purchaseDetails.getFirstname());
					add(purchaseDetails.getLastname());
					add(purchaseDetails.getCreditCard());
					add(purchaseDetails.getCvv());
					add(purchaseDetails.getCardExpireDate());
				}
			};
			try {
				queryExecutor.insertToTable("costumerPurchaseDetails", cotumerPurchaseDetails);
			} catch (SQLException e) {
				// else give null
				return null;
			}
		}
		// need to update purchaseDeatails table and mangerReports

		// give list of mapsId that belong to that cityId
		List<List<Object>> mapsIdList = queryExecutor.selectColumnsByValue("citiesMaps", "cityId", cityId, "mapId");
		List<Integer> mapsid = toIdList(mapsIdList);

		List<File> files = new ArrayList<>();

		for (int i : mapsid) {
			files.add(getMapFile(i));
		}

		// update purchase history
		List<Object> pDetails = new ArrayList<Object>() {
			{
				int days = 30 * timeInterval;
				java.sql.Date startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				java.sql.Date endDate = addDays(startDate, days);
				add(username);
				add(cityId);
				add(startDate);
				add(true);
				add(0);
				add(endDate);
			}
		};
		try {

			String tableToUpdate = "oneTimePurchase";
			queryExecutor.insertToTable("purchaseDeatailsHistory", pDetails);

			updateMangerReports(cityId, tableToUpdate);
		} catch (

		SQLException e) {
			return null;
		}

		return files;
	}

	@Override
	public void notifyMapView(int cityId) throws SQLException {

		String tableToUpdate = "viewsNum";
		updateMangerReports(cityId, tableToUpdate);

	}

	@Override
	public List<PurchaseHistory> getPurchaseHistory(String username) throws SQLException {

		// getting username purchase history
		List<List<Object>> history = queryExecutor.selectColumnsByValue("purchaseDeatailsHistory", "username", username,
				"*");

		List<PurchaseHistory> purchaseHistories = new ArrayList<>();

		// converting it to PurchaseHistory objects that contains - city id, start date
		// , end date
		for (int i = 0; i < history.size(); i++) {
			PurchaseHistory purchaseHistory = new PurchaseHistory((Date) history.get(i).get(2),
					(Date) history.get(i).get(5), (int) history.get(i).get(1));
			purchaseHistories.add(purchaseHistory);
		}

		return purchaseHistories;
	}

	@Override
	public List<Report> getAllcitiesReport() throws SQLException {

		List<List<Object>> reports = queryExecutor.selectAllColumns("mangerReports", "*");

		List<Report> allCitiesreports = new ArrayList<>();

		for (int i = 0; i < reports.size(); i++) {
			Report cityReport = new Report((int) reports.get(i).get(0), (String) reports.get(i).get(1),
					(int) reports.get(i).get(2), (int) reports.get(i).get(3), (int) reports.get(i).get(4),
					(int) reports.get(i).get(5), (int) reports.get(i).get(6));
			allCitiesreports.add(cityReport);
		}

		return allCitiesreports;
	}

	@Override
	public Report getOneCityReport(String cityName) throws SQLException {
		List<List<Object>> report = queryExecutor.selectColumnsByValue("mangerReports", "cityName", cityName, "*");

		Report cityReport = new Report((int) report.get(0).get(0), (String) report.get(0).get(1),
				(int) report.get(0).get(2), (int) report.get(0).get(3), (int) report.get(0).get(4),
				(int) report.get(0).get(5), (int) report.get(0).get(6));
		return cityReport;
	}

	// when you want to update column in mangerReports you call this
	// for example when adding new map
	private void updateMangerReports(int cityId, String tableToUpdate) throws SQLException {

		int plusOne;

		List<List<Object>> updateListCulomn = queryExecutor.selectColumnsByValue("mangerReports", "cityId", cityId,
				"oneTimePurchase");
		if (updateListCulomn.isEmpty()) {
			System.out.println("wtf is not supposed to be empty");
		} else {
			plusOne = (int) updateListCulomn.get(0).get(0) + 1;

			queryExecutor.updateTableColumn("mangerReports", tableToUpdate, plusOne, "cityId", cityId);
		}

	}

	// When adding new city to data base mangerReports need to be update
	private void addCityManagerReport(int cityId, String cityName) throws SQLException {

		List<Object> objects = new ArrayList<Object>() {
			{
				add(cityId);
				add(cityName);
				add(0);
				add(0);
				add(0);
				add(0);
				add(0);

			}
		};

		queryExecutor.insertToTable("mangerReports", objects);

	}

	// this delete column drom mangerReports
	private void deleteCityManagerReport(int cityId) throws SQLException {

		queryExecutor.deleteValueFromTable("mangerReports", "cityId", cityId);

	}

	private Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}
}
