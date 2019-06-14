package database.execution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class GcmDataExecutor
		implements IGcmDataExecute, IGcmCustomerExecutor, IGcmEditorExecutor, IGcmContentManagerExecutor {
	IExecuteQueries queryExecutor;
	IParseObjects objectParser;

	public GcmDataExecutor(IExecuteQueries queryExecutor, IParseObjects objectParser) {
		this.queryExecutor = queryExecutor;
		this.objectParser = objectParser;
	}

	@Override
	public boolean addUser(String username, String password, User user) throws SQLException {
		if (queryExecutor
				.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.customerUsers), "username", username, "*")
				.isEmpty()) {
			List<Object> userList = new ArrayList<Object>() {
				{
					add(username);
					add(password);
				}
			};
			userList.addAll(objectParser.getUserFieldsList(user));
			queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.customerUsers), userList);
			return true;
		}
		return false;
	}

	@Override
	public RequestState verifyUser(String username, String password) throws SQLException {
		if (username != null && password != null) {
			if (username.equals("editor") && password.equals("editor")) {
				System.out.println("editor");
				return RequestState.editor;
			} else if (username.equals("manager") && password.equals("manager")) {
				return RequestState.contentManager;
			}
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
			List<List<Object>> rows = queryExecutor.selectColumnsByValues(
					DatabaseMetaData.getTableName(Tables.customerUsers), namesList, valuesList, "username, password",
					Status.none);

			if (!rows.isEmpty())
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
		int mapId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.mapsMetaDetails),
				objectParser.getMapMetaFieldsList(mapDescription), Status.toAdd);
		List<Object> mapFileRow = new ArrayList<Object>() {
			{
				add(mapId);
				add(getBytes(mapFile));
			}
		};
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsFiles), mapFileRow, Status.toAdd);
		List<Object> cityRow = new ArrayList<Object>() {
			{
				add(cityId);
				add(mapId);
			}
		};
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds), cityRow, Status.toAdd);
		return mapId;
	}

	@Override
	public Map getMapDetails(int mapId) throws SQLException {
		List<List<Object>> metaDetailsRows = queryExecutor.selectColumnsByValue(
				DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId, "*", Status.published);
		if (metaDetailsRows.isEmpty())
			return null;
		else {
			List<Integer> mapSitesIds = toIdList(queryExecutor.selectColumnsByValue(
					DatabaseMetaData.getTableName(Tables.mapsSites), "mapId", mapId, "siteId", Status.published));
			List<Site> mapSites = new ArrayList<>();
			for (int siteId : mapSitesIds) {
				mapSites.add(getSite(siteId));
			}
			List<Integer> mapToursIds = toIdList(queryExecutor.selectColumnsByValue(
					DatabaseMetaData.getTableName(Tables.mapTours), "mapId", mapId, "tourId", Status.published));

			List<Tour> mapTours = new ArrayList<>();
			for (int tourId : mapToursIds) {
				mapTours.add(getTour(tourId));
			}

			return objectParser.getMap(metaDetailsRows.get(0), mapSites, mapTours); // only one row correspond to this
																					// id
		}
	}

	public Site getSite(int siteId) throws SQLException {
		List<List<Object>> siteRows = queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.sites),
				"siteId", siteId, "*", Status.published);
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
				"mapId", mapId, "mapFile", Status.published);
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
		int siteId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.sites),
				objectParser.getSiteFieldsList(site), Status.toAdd);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesSitesIds), new ArrayList<Object>() {
			{
				add(cityId);
				add(siteId);
			}
		}, Status.toAdd);
		return siteId;
	}

	@Override
	public void addExistingSiteToMap(int mapId, int siteId) throws SQLException {
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsSites), new ArrayList<Object>() {
			{
				add(mapId);
				add(siteId);

			}
		}, Status.toAdd);
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
		return objectParser.getCityByMetaFields(
				queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "cityId",
						cityId, "*", Status.published).get(0));

	}

	@Override
	public void addExistingSiteToTour(int tourId, int siteId, int durnace) throws SQLException {
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
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.cityTours), new ArrayList<Object>() {
			{
				add(cityId);
				add(tourId);
				;// status

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
	public void deleteCity(City city) throws SQLException {
		// Auto-generated method stub

	}

	@Override
	public File downloadMap(int mapId, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getPurchasedMaps(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File purchaseMap(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addExistingTourToMap(int mapId, int tourId) throws SQLException {
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapTours), new ArrayList<Object>() {
			{
				add(mapId);
				add(tourId);
			}
		}, Status.toAdd);
	}

	@Override
	public double getMembershipPrice(int cityId, int timeInterval) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSavedCreditCard() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails,
			String username) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean repurchaseMembership(PurchaseDetails purchaseDetails, String username) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean repurchaseMembershipBySavedDetails(String username) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public File purchaseMapOneTime(int mapId, PurchaseDetails purchaseDetails, String username) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyMapView(int mapId, String username) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionMapAddEdit(Map map, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionMapUpdateEdit(Map map, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionMapDeleteEdit(Map map, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCityAddEdit(City city, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCityUpdateEdit(City city, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCityDeleteEdit(City city, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteAddEdit(Site site, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteUpdateEdit(Site site, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteDeleteEdit(Site site, boolean action) {
		// TODO Auto-generated method stub

	}

	List<Map> getMapsByStatus(Status status) throws SQLException {
		List<Integer> mapIds = toIdList(
				queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "status",
						DatabaseMetaData.getStatus(status), "*"));
		List<Map> mapsObjects = new ArrayList<>();
		mapIds.forEach((mapId) -> {
			try {
				mapsObjects.add(getMapDetails(mapId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return mapsObjects;
	}

	List<City> getCitiesByStatus(Status status) throws SQLException {
		List<Integer> cityIds = toIdList(
				queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "status",
						DatabaseMetaData.getStatus(status), "*"));
		List<City> cityObjects = new ArrayList<>();
		cityIds.forEach((cityId) -> {
			try {
				cityObjects.add(getCityById(cityId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return cityObjects;
	}

	List<Site> getSitesByStatus(Status status) throws SQLException {
		List<Integer> siteIds = toIdList(
				queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "status",
						DatabaseMetaData.getStatus(status), "*"));
		List<Site> sites = new ArrayList<>();
		siteIds.forEach((siteId) -> {
			try {
				sites.add(getSite(siteId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return sites;
	}

	@Override
	public List<Map> getMapsAddEdits() throws SQLException {
		// sites, tour

		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public List<Map> getMapsUpdateEdits() throws SQLException {
		return getMapsByStatus(Status.toUpdate);

	}

	@Override
	public List<Map> getMapsDeleteEdits()  throws SQLException{
		return getMapsByStatus(Status.toDelete);
	}

	@Override
	public List<Site> getSitesAddEdits()  throws SQLException{
		// TODO Auto-generated method stub
		return null;
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
	public List<City> getCitiesAddEdits()  throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesUpdateEdits() throws SQLException {
		return getCitiesByStatus(Status.toUpdate);

	}

	@Override
	public List<City> getCitiesDeleteEdits()  throws SQLException{
		return getCitiesByStatus(Status.toDelete);

	}

	@Override
	public void editCityPrice(int cityId, double newPrice)  throws SQLException{
		// TODO Auto-generated method stub

	}

}
