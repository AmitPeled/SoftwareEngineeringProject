package database.execution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.customer.PurchaseHistory;
import dataAccess.users.PurchaseDetails;
import database.metadata.DatabaseMetaData;
import database.metadata.DatabaseMetaData.Tables;
import database.objectParse.IParseObjects;
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
					DatabaseMetaData.getTableName(Tables.customerUsers), namesList, valuesList, "username, password");

			if (!rows.isEmpty())
				return RequestState.customer;
		}
		return RequestState.wrongDetails;

	}

	@Override
	public int addMapToCity(int cityId, Map mapDescription, File mapFile/* , String pathToFilesFolder */)
			throws SQLException {
		int mapId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.mapsMetaDetails),
				objectParser.getMapMetaFieldsList(mapDescription));
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
			List<Integer> mapSitesIds = toIdList(queryExecutor
					.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsSites), "mapId", mapId, "siteId"));
			List<Site> mapSites = new ArrayList<>();
			for (int siteId : mapSitesIds) {
				mapSites.add(getSite(siteId));
			}
			List<Integer> mapToursIds = toIdList(queryExecutor
					.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapTours), "mapId", mapId, "tourId"));
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
				"siteId", siteId, "*");
		if (siteRows.isEmpty())
			return null;
		else
			return objectParser.getSite(siteRows.get(0)); // only one site row correspond to this id
	}

	@SuppressWarnings("unchecked")
	public Tour getTour(int tourId) throws SQLException {
		List<List<Object>> siteRows = queryExecutor
				.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.toursMetaDetails), "tourId", tourId, "*");
		if (siteRows.isEmpty())
			return null;
		else {
			List<List<Object>> siteIdsAndDurances = queryExecutor.selectColumnsByValue(
					DatabaseMetaData.getTableName(Tables.tourSitesIdsAndDurance), "tourId", tourId,
					"siteId, siteDurance");
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

	@Override
	public int addCityWithInitialMap(City city, Map mapDescription, File mapFile) throws SQLException {
		int cityId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
				objectParser.getCityMetaFieldsList(city));
		int mapId = addMapToCity(cityId, mapDescription, mapFile);
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
		return mapId;
	}

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
							fieldName, "%" + (String) fieldVal + "%", "cityId"));
		} else {
			cityIds = toIdList(queryExecutor.selectColumnsByValue(
					DatabaseMetaData.getTableName(Tables.citiesMetaDetails), fieldName, fieldVal, "cityId"));
		}
		List<Map> maps = new ArrayList<>();
		List<List<Object>> mapIdRows = new ArrayList<>();
		for (int cityId : cityIds) {
			mapIdRows.addAll(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMapsIds),
					"cityId", cityId, "mapId"));
		}
		List<Integer> mapsIds = toIdList(mapIdRows);
		for (int mapId : mapsIds)
			maps.add(getMapDetails(mapId));

		return maps;
	}

	private List<Map> getMapsBySiteField(String fieldName, Object fieldVal, boolean withPartialField)
			throws SQLException {
		List<Integer> sitesIds;
		if (withPartialField)
			sitesIds = toIdList(queryExecutor.selectColumnsByPartialValue(DatabaseMetaData.getTableName(Tables.sites),
					fieldName, "%" + (String) fieldVal + "%", "siteId"));
		else
			sitesIds = toIdList(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.sites),
					fieldName, fieldVal, "siteId"));

		List<Map> maps = new ArrayList<>();
		List<List<Object>> mapIdRows = new ArrayList<>();
		for (int siteId : sitesIds) {
			mapIdRows.addAll(queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsSites),
					"siteId", siteId, "mapId"));
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
			rows.add(row.get(column));
		return rows;
	}

	@Override
	public List<Map> getMapsByCityName(String cityName) throws SQLException {
		return getMapsByCityField("cityName", cityName, false);
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
				DatabaseMetaData.getTableName(Tables.citiesSitesIds), "cityId", cityId, "siteId"));
		return getSitesByIds(siteIds);
	}

	@Override
	public void updateMap(int mapId, Map newMap) throws SQLException {
		List<Object> mapRow = objectParser.getMapMetaFieldsList(newMap);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId);
		mapRow.set(0, mapId);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), mapRow);
	}

	@Override
	public void UpdateSite(int siteId, Site newSite) throws SQLException {
		List<Object> siteRow = objectParser.getSiteFieldsList(newSite);
		siteRow.set(0, siteId);
		deleteSite(siteId);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.sites), siteRow);
	}

//	private void deleteSite(int siteId) throws SQLException {
//		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.sites), "siteId", siteId);
//	}

	@Override
	public City getCityByMapId(int mapId) throws SQLException {
		int cityId = (int) queryExecutor
				.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMapsIds), "mapId", mapId, "cityId")
				.get(0).get(0);
		return getCityById(cityId);
	}

	private City getCityById(int cityId) throws SQLException {
		return objectParser.getCityByMetaFields(queryExecutor
				.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "cityId", cityId, "*")
				.get(0));

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
				});
	}

	@Override
	public int addNewTourToCity(int cityId, Tour tour) throws SQLException {
		int tourId = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.toursMetaDetails),
				objectParser.getTourMetaFieldsList(tour));
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.cityTours), new ArrayList<Object>() {
			{
				add(cityId);
				add(tourId);
			}
		});
		return tourId;
	}

	@Override
	public void updateCity(int cityId, City city) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "cityId", cityId);
		List<Object> cityRow = objectParser.getCityMetaFieldsList(city);
		cityRow.set(0, city);
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails), cityRow);
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
	public void addExistingTourToMap(int mapId, int tourId) throws SQLException {
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapTours), new ArrayList<Object>() {
			{
				add(mapId);
				add(tourId);
			}
		});
	}

	@Override
	public void editMapPrice(int mapId, double newPrice) {
		// TODO Auto-generated method stub

	}

	


	@Override
	public boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails,
			String username) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	

	@Override
	public void approveSiteEdit(Site site) {
		// TODO Auto-generated method stub

	}

	@Override
	public void approveCityEdit(City city) {
		// TODO Auto-generated method stub

	}

	@Override
	public void approveMapEdit(Map map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void approveTourEdit(Tour tour) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSavedCreditCard(String username) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getMembershipPrice(int cityId, int timeInterval, String username) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getOneTimePurchasePrice(int cityId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean repurchaseMembershipBySavedDetails(int cityId, int timeInterval, String username)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<File> purchaseMapOneTime(int cityId, PurchaseDetails purchaseDetails, String username)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyMapView(int cityId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PurchaseHistory> getPurchaseHistory(String username) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
