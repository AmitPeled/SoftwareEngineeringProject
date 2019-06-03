package database.metadata;

import java.util.HashMap;

public class databaseMetaData {
	private static final String host = "remotemysql.com";
	private static final String DBName = "X6SgPM1fb2";
	private static final String username = "X6SgPM1fb2";
	@SuppressWarnings("serial")
	private static HashMap<Tables, String> tablesNames = new HashMap<Tables, String>() {
		{
			put(Tables.users, "users");
			put(Tables.mapsDetails, "mapsDetails");
			put(Tables.mapsFiles, "mapsFiles");
			put(Tables.citiesMetaDetails, "citiesMeta");
			put(Tables.citiesMapsIds, "citiesMaps");
			put(Tables.citiesSitesIds, "citiesSites");
			put(Tables.sites, "sites");
		}
	};

	public static enum Tables {
		users, mapsDetails, mapsFiles, citiesMetaDetails, citiesMapsIds, citiesSitesIds, sites
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
