package database.execution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
import userInfo.UserInfoImpl;
import users.User;

@SuppressWarnings("serial")
public class CostumerDataExecutor
		implements IGcmDataExecute, IGcmCustomerExecutor, IGcmEditorExecutor, IGcmContentManagerExecutor {
	IExecuteQueries queryExecutor;
	IParseObjects objectParser;
	private UserInfoImpl userInfoImpl;

	public CostumerDataExecutor(IExecuteQueries queryExecutor, IParseObjects objectParser) {
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
			List<Integer> mapsToursIds = toIdList(queryExecutor
					.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsTours), "mapId", mapId, "tourId"));
			List<Tour> mapsTours = new ArrayList<>();
			for (int tourId : mapsToursIds) {
				mapsTours.add(getTour(tourId));
			}
			return objectParser.getMap(metaDetailsRows.get(0), mapSites, mapsTours); // only one row correspond to this
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
	public void deleteMapEdit(int mapId) throws SQLException {
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsFiles), "mapId", mapId);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsSites), "mapId", mapId);
		queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds), "mapId", mapId);
	}

	@Override
	public int addCity(City city) throws SQLException {
		int id = queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
				objectParser.getCityMetaFieldsList(city));
		// for (Map map : city.getMaps()) {
		// queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds),
		// new ArrayList<Object>() {
		// {
		// add(id);
		// add(map.getId());
		// }
		// });
		// }
		// for (Site site : city.getSites()) {
		// queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
		// new ArrayList<Object>() {
		// {
		// add(id);
		// add(site.getId());
		// }
		// });
		return id;
	}

	// @Override
	// public int addCityWithInitialMap(City city, Map mapDescription, File mapFile)
	// throws SQLException {
	// int cityId =
	// queryExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
	// objectParser.getCityMetaFieldsList(city));
	// int mapId = addMapToCity(cityId, mapDescription, mapFile);
	// for (Map map : city.getMaps()) {
	// queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMapsIds),
	// new ArrayList<Object>() {
	// {
	// add(id);
	// add(map.getId());
	// }
	// });
	// }
	// for (Site site : city.getSites()) {
	// queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesMetaDetails),
	// new ArrayList<Object>() {
	// {
	// add(id);
	// add(site.getId());
	// }
	// });
	// return mapId;
	// }
	//
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
	// private static byte[] getBytesFromFile(File file) throws IOException {
	// // Get the size of the file
	// long length = file.length();
	//
	// // You cannot create an array using a long type.
	// // It needs to be an int type.
	// // Before converting to an int type, check
	// // to ensure that file is not larger than Integer.MAX_VALUE.
	// if (length > Integer.MAX_VALUE) {
	// // File is too large
	// throw new IOException("File is too large!");
	// }
	//
	// // Create the byte array to hold the data
	// byte[] bytes = new byte[(int) length];
	//
	// // Read in the bytes
	// int offset = 0;
	// int numRead = 0;
	//
	// InputStream is = new FileInputStream(file);
	// try {
	// while (offset < bytes.length && (numRead = is.read(bytes, offset,
	// bytes.length - offset)) >= 0) {
	// offset += numRead;
	// }
	// } finally {
	// is.close();
	// }
	//
	// // Ensure all the bytes have been read in
	// if (offset < bytes.length) {
	// throw new IOException("Could not completely read file " + file.getName());
	// }
	// return bytes;
	// }

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

	// private void deleteSite(int siteId) throws SQLException {
	// queryExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.sites),
	// "siteId", siteId);
	// }

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
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.citiesTours), new ArrayList<Object>() {
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
	public void deleteCity(int cityId) throws SQLException {
		// Auto-generated method stub

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
		List<List<Object>> mapsIdList = null;

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
	public void addExistingTourToMap(int mapId, int tourId) throws SQLException {
		queryExecutor.insertToTable(DatabaseMetaData.getTableName(Tables.mapsTours), new ArrayList<Object>() {
			{
				add(mapId);
				add(tourId);
			}
		});
	}

	@Override
	public void editCityPrice(int mapId, double newPrice) {
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
			try {
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
			} catch (SQLException e) {
				return false;
			}

		}
		// if seccuss
		return true;
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
			try {
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
		} catch (SQLException e) {
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

	private Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
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
	public void actionTourDeleteEdit(Site site, boolean action) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map> getMapsObjectAddedTo(int contentId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesObjectAddedTo(int contentId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> getToursObjectAddedTo(int contentId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addCity(int cityId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void actionMapAddEdit(Map map, boolean action) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionMapUpdateEdit(Map map, boolean action) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionMapDeleteEdit(Map map, boolean action) throws SQLException {
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
	public void actionCityDeleteEdit(City city, boolean action) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionSiteAddEdit(Site site, boolean action) throws SQLException {
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
	public List<Map> getMapsAddEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getMapsUpdateEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getMapsDeleteEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> getSitesAddEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> getSitesUpdateEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> getSitesDeleteEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesAddEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesUpdateEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesDeleteEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> getToursDeleteEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> getToursUpdateEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> getToursAddEdits() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
