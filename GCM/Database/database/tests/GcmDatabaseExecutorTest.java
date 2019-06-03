package database.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.GcmDataExecutor;
import database.execution.IGcmDataExecute;
import database.metadata.DatabaseMetaData;
import database.objectParse.DatabaseParser;
import maps.City;
import maps.Map;
import users.User;

class GcmDatabaseExecutorTest {
	static IGcmDataExecute gcmDataExecutor;
	static int cityId;
	static int mapId;
	static File mapFile;

	@BeforeAll
	static void setAll() throws IllegalArgumentException, SQLException {
		gcmDataExecutor = new GcmDataExecutor(
				new DatabaseExecutor(DBConnector.connect()/*, DatabaseMetaData.getDbName()*/), new DatabaseParser());
		mapFile = new File("import\\resources\\Gta3_map.gif");
		cityId = gcmDataExecutor.addCity(new City(11, "haifa"));
		mapId = gcmDataExecutor.addMapToCity(cityId, new Map(11, 13.1f, 11.1f), mapFile);
	}

	@Test
	void userTest() throws SQLException {
		String username = "user1", password = "pass1", email = "aa", phoneNumber = "052";
		gcmDataExecutor.addUser(username, password, new User(email, phoneNumber));
		assertTrue(gcmDataExecutor.verifyUser(username, password));
		assertFalse(gcmDataExecutor.verifyUser(username, "bla"));
		assertFalse(gcmDataExecutor.addUser(username, "bla", new User(email, phoneNumber)));
	}

	@Test
	void getMapDetailsTest() throws SQLException {
		Map map = gcmDataExecutor.getMapDetails(mapId);
		assertEquals(13.1f, map.getWidth());
		assertEquals(11.1f, map.getHeight());
		assertEquals(0f, map.getOffset().x);
	}

	@Test
	void getMapFileTest() throws SQLException {
		File mapFile2 = gcmDataExecutor.getMapFile(mapId);
		assertEquals(mapFile, mapFile2);
	}

	@Test
	void deleteMapTest() throws SQLException {
		gcmDataExecutor.deleteMap(mapId);
		assertNull(gcmDataExecutor.getMapDetails(mapId));
		assertNull(gcmDataExecutor.getMapFile(mapId));


	}
//	@Test
//	void deleteCityTest() throws SQLException {
//		gcmDataExecutor.deleteCity(cityId);
//		City city = gcmDataExecutor.getCity(mapId);
//		assertNull(city);
//
//	}

}
