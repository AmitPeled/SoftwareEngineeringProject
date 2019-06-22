package database.metadata;

import java.util.HashMap;

import database.objectParse.CurrentEditStatus;

public class DatabaseMetaData {
	private static final String host = "remotemysql.com";
	private static final String DBName = "X6SgPM1fb2";
	private static final String username = "X6SgPM1fb2";
	@SuppressWarnings("serial")
	private static HashMap<Table, String> tablesNames = new HashMap<Table, String>() {
		{
			put(Table.customerUsers, "customerUsers");
			put(Table.editorUsers, "editorUsers");
			put(Table.contentManagerUsers, "contentManagerUsers");
			put(Table.generalManagerUsers, "generalManagerUsers");

			put(Table.mapsMetaDetails, "mapsMetaDetails");
			put(Table.mapsFiles, "mapsFiles");
			put(Table.mapsSites, "mapsSites");
			put(Table.citiesMetaDetails, "citiesMetaDetails");
			put(Table.citiesMapsIds, "citiesMaps");
			put(Table.citiesSitesIds, "citiesSites");
			put(Table.sites, "sites");
			put(Table.tourSitesIdsAndDurance, "toursSites");
			put(Table.citiesTours, "citiesTours");
			put(Table.toursMetaDetails, "toursMetaDetails");
			put(Table.mapsTours, "mapsTours");
			put(Table.purchaseHistory, "purchaseDeatailsHistory");
			put(Table.mapsDownloadHistory, "mapsDownloadHistory");
			put(Table.mapFilesPath, "mapFilesPath");
		}
	};

	public static enum Table {
		customerUsers, editorUsers, contentManagerUsers, generalManagerUsers, mapsMetaDetails, mapsFiles, mapsSites,
		citiesMetaDetails, citiesMapsIds, citiesSitesIds, sites, toursMetaDetails, tourSitesIdsAndDurance, mapsTours,
		citiesTours, purchaseHistory, mapsDownloadHistory, mapFilesPath
	}

	public static String getHostName() {
		return host;
	}

	public static String getDbName() {
		return DBName;
	}

	public static String getDbUsername() {
		return username;
	}

	public static String getTableName(Table table) {
		return tablesNames.get(table);
	}

	public static int getStatus(CurrentEditStatus status) {
		switch (status) {
		case PUBLISH:
			return 0;
		case ADD:
			return 1;
		case UPDATE:
			return 2;
		case DELETE:
			return 3;
		case PRICE_UPDATE:
			return 4;
		default:
			System.err.println("bad status value");
			return -1;
		}
	}
}
