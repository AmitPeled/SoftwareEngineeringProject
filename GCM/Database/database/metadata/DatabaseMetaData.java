package database.metadata;

import java.util.HashMap;

public class DatabaseMetaData {
	private static final String host = "remotemysql.com";
	private static final String DBName = "X6SgPM1fb2";
	private static final String username = "X6SgPM1fb2";
	@SuppressWarnings("serial")
	private static HashMap<Tables, String> tablesNames = new HashMap<Tables, String>() {
		{
			put(Tables.users, "users");
			put(Tables.mapsMetaDetails, "mapsMetaDetails");
			put(Tables.mapsFiles, "mapsFiles");
			put(Tables.mapsSites, "mapsSites");
			put(Tables.citiesMetaDetails, "citiesMetaDetails");
			put(Tables.citiesMapsIds, "citiesMaps");
			put(Tables.citiesSitesIds, "citiesSites");
			put(Tables.sites, "sites");
		}
	};

	public static enum Tables {
		users, mapsMetaDetails, mapsFiles, mapsSites, citiesMetaDetails, citiesMapsIds, citiesSitesIds, sites
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
}
