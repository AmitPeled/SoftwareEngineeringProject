package database.execution.selection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.execution.IExecuteQueries;
import database.metadata.DatabaseMetaData;
import database.metadata.DatabaseMetaData.Tables;
import database.objectParse.IParseObjects;
import database.objectParse.Status;
import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;

public class ContentGetter {
	IExecuteQueries queryExecutor;
	IParseObjects objectParser;

	public ContentGetter(IExecuteQueries queryExecutor, IParseObjects objectParser) {
		this.queryExecutor = queryExecutor;
		this.objectParser = objectParser;

	}

	public Tour getTour(int tourId) throws SQLException {
		return getTourByStatus(tourId, Status.PUBLISH);
	}

	@SuppressWarnings("unchecked")
	public Tour getTourByStatus(int tourId, Status status) throws SQLException {
		List<List<Object>> tourRows = queryExecutor.selectColumnsByValue(
				DatabaseMetaData.getTableName(Tables.toursMetaDetails), "tourId", tourId, "*", status);
		if (tourRows.isEmpty())
			return null;
		else {
			List<List<Object>> siteIdsAndDurances = queryExecutor.selectColumnsByValue(
					DatabaseMetaData.getTableName(Tables.tourSitesIdsAndDurance), "tourId", tourId,
					"siteId, siteDurance", status);

			List<Integer> siteIds = (List<Integer>) (Object) toListOfColumnNum(siteIdsAndDurances, 1);

			List<Integer> siteDurances = (List<Integer>) (Object) toListOfColumnNum(siteIdsAndDurances, 2);

			List<Site> sites = getSitesByIds(siteIds);
			return objectParser.getTour(tourRows.get(0), sites, siteDurances); // only one site row correspond to this
																				// id
		}
	}

	public List<Object> toListOfColumnNum(List<List<Object>> listRows, int column) {
		List<Object> rows = new ArrayList<>();
		for (List<Object> row : listRows)
			rows.add(row.get(column - 1));
		return rows;
	}

	public List<Site> getSitesByIds(List<Integer> siteIds) throws SQLException {
		List<Site> sites = new ArrayList<>();
		for (int siteId : siteIds)
			sites.add(getSite(siteId));
		return sites;
	}

	public Site getSite(int siteId) throws SQLException {
		return getSiteByStatus(siteId, Status.PUBLISH);
	}

	public Site getSiteByStatus(int siteId, Status status) throws SQLException {
		List<List<Object>> siteRows = queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.sites),
				"siteId", siteId, "*", status);
		if (siteRows.isEmpty())
			return null;
		else
			return objectParser.getSite(siteRows.get(0)); // only one site row correspond to this id
	}

	public File getMapFile(int mapId) throws SQLException {
		List<List<Object>> rows = queryExecutor.selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsFiles),
				"mapId", mapId, "mapFile");
		if (rows.isEmpty())
			return null;
		else
			return (File) getObject((byte[]) rows.get(0).get(0)); // only one row correspond to this id
	}

	public Map getMapDetails(int mapId) throws SQLException {
		return getMapDetailsByStatus(mapId, Status.PUBLISH);
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

	public List<Integer> toIdList(List<List<Object>> idsRows) {
		List<Integer> ids = new ArrayList<>();
		for (List<Object> idRow : idsRows)
			if (!ids.contains((int) idRow.get(0)))
				ids.add((int) idRow.get(0));
		return ids;
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

//	public City getCityById(int cityId) throws SQLException {
//		return getCityById(cityId, Status.published);
//	}
//
//	public City getCityById(int cityId, Status status) throws SQLException {
//		List<List<Object>> list = queryExecutor.selectColumnsByValue(
//				DatabaseMetaData.getTableName(Tables.citiesMetaDetails), "cityId", cityId, "*", status);
//		if (!list.isEmpty())
//			return objectParser.getCityByMetaFields(list.get(0));
//		else
//			return null;
//
//	}

}
