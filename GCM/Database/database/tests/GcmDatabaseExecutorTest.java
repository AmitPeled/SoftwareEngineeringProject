package database.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.sun.xml.internal.bind.v2.runtime.MarshallerImpl;

import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.GcmDataExecutor;
import database.execution.IGcmDataExecute;
import database.objectParse.DatabaseParser;
import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import queries.RequestState;
import users.User;

/**
 * @author amit
 *
 */
class GcmDatabaseExecutorTest {
	static IGcmDataExecute gcmDataExecutor;
	static int cityId;
	static int mapId;
	static int siteId;
	static Map map;
	static File mapFile;
	static float width = 112.1f;
	static float height = 11.1f;
	static String description = "";
	static String name = "";

	@BeforeAll
	static void setAll() throws IllegalArgumentException, SQLException {
		gcmDataExecutor = new GcmDataExecutor(new DatabaseExecutor(DBConnector.connect()), new DatabaseParser());
		mapFile = new File("import\\resources\\Gta3_map.gif");
		cityId = gcmDataExecutor.addCity(new City(11, "eli", "desc"));
		mapId = gcmDataExecutor.addMapToCity(cityId,
				new Map(12, name, description, width, height, new Coordinates(), 0, null, null), mapFile);
		map = new Map(mapId, name, description, width, height, new Coordinates(), 0, null, null);
		siteId = gcmDataExecutor.addNewSiteToCity(cityId,
				new Site("name", "desc", "type", false, new Coordinates(7, 9)));
		gcmDataExecutor.addExistingSiteToMap(mapId, siteId);
	}

	@Test
	void userTest() throws SQLException {
		String username = "editor", password = "editor", firstName = "first", lastName = "last", email = "aa",
				phoneNumber = "052";
		gcmDataExecutor.addUser(username, password, new User(firstName, lastName, email, phoneNumber));
		System.out.println(gcmDataExecutor.verifyUser(username, password));
		assertEquals(RequestState.editor, gcmDataExecutor.verifyUser(username, password));
		assertNotEquals(RequestState.editor, gcmDataExecutor.verifyUser(username, "bla"));
		assertFalse(gcmDataExecutor.addUser(username, "bla", new User(firstName, lastName, email, phoneNumber)));
	}

	@Test
	void getMapDetailsTest() throws SQLException {
		Map map1 = gcmDataExecutor.getMapDetails(mapId);
		System.err.println(map.getId());
		assertNull(map);
		gcmDataExecutor.actionMapAddEdit(map1, true);
		map = gcmDataExecutor.getMapDetails(mapId);

		System.out.println(map + " " + mapId);
		assertEquals(112.1f, map.getWidth());
//		assertEquals(11.1f, map.getHeight());
//		assertEquals(0f, map.getOffset().x);
//		Site site = map.getSites().get(0);
//		assertEquals(siteId, site.getId());
//		assertEquals("name", site.getName());
//		assertEquals("desc", site.getDescription());
//		assertEquals("type", site.getSiteType());
//		assertEquals(false, site.isAccessibleForDisabled());

	}

	@Test
	void getMapFileTest() throws SQLException {
		File mapFile2 = gcmDataExecutor.getMapFile(mapId);
		assertEquals(mapFile, mapFile2);
	}

	@Test
	void deleteMapTest() throws SQLException {
		gcmDataExecutor.deleteMapEdit(mapId);
		gcmDataExecutor.actionMapDeleteEdit(map, true);
		assertNull(gcmDataExecutor.getMapDetails(mapId));
		assertNull(gcmDataExecutor.getMapFile(mapId));
	}

	@Test
	void getUserTest() throws SQLException {
		gcmDataExecutor.addUser(".", ".", new User("a", "b", "c", "d"));
		User user = gcmDataExecutor.getUserDetails(".");
		assertEquals("a", user.getFirstName());

		assertEquals("c", user.getEmail());

	}

//	@Test
//	void deleteCityTest() throws SQLException {
//		gcmDataExecutor.deleteCity(cityId);
//		City city = gcmDataExecutor.getCity(mapId);
//		assertNull(city);
//
//	}

}
