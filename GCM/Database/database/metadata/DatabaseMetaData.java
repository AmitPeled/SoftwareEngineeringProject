package database.metadata;

import java.util.HashMap;

import database.objectParse.Status;

public class DatabaseMetaData {
	private static final String host = "remotemysql.com";
	private static final String DBName = "X6SgPM1fb2";
	private static final String username = "X6SgPM1fb2";
	@SuppressWarnings("serial")
	private static HashMap<Tables, String> tablesNames = new HashMap<Tables, String>() {
		{
			put(Tables.customerUsers, "customerUsers");
			put(Tables.editorUsers, "editorUsers");
			put(Tables.contentManagerUsers, "contentManagerUsers");
			put(Tables.generalManagerUsers, "generalManagerUsers");

			put(Tables.mapsMetaDetails, "mapsMetaDetails");
			put(Tables.mapsFiles, "mapsFiles");
			put(Tables.mapsSites, "mapsSites");
			put(Tables.citiesMetaDetails, "citiesMetaDetails");
			put(Tables.citiesMapsIds, "citiesMaps");
			put(Tables.citiesSitesIds, "citiesSites");
			put(Tables.sites, "sites");
			put(Tables.tourSitesIdsAndDurance, "toursSites");
			put(Tables.cityTours, "citiesTours");
			put(Tables.toursMetaDetails, "toursMetaDetails");
			put(Tables.mapsTours, "mapsTours");
		}
	};

	public static enum Tables {
		customerUsers, editorUsers, contentManagerUsers, generalManagerUsers, mapsMetaDetails, mapsFiles, mapsSites,
		citiesMetaDetails, citiesMapsIds, citiesSitesIds, sites, toursMetaDetails, tourSitesIdsAndDurance, mapsTours,
		cityTours
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

	public static String getTableName(Tables table) {
		return tablesNames.get(table);
	}

	public static int getStatus(Status status) {
		switch (status) {
		case published:
			return 0;
		case toAdd:
			return 1;
		case toUpdate:
			return 2;
		case toDelete:
			return 3;
		default:
			System.err.println("bad status value");
			return -1;
		}
	}
}
