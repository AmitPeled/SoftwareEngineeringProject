package database.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import approvalReports.ActionTaken;
import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.GcmDataExecutor;
import database.execution.IGcmDataExecute;
import database.objectParse.DatabaseParser;
import database.objectParse.Status;
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
	static int mapId = 2;
	static int siteId;
	static Map map;
	static Site site;
	static City city;
	static File mapFile;
	static float width = 112.1f;
	static float height = 11.1f;
	static String description = ".";
	static String name = ".1";

	@BeforeAll
	static void setAll() throws IllegalArgumentException, SQLException {
		gcmDataExecutor = new GcmDataExecutor(new DatabaseExecutor(DBConnector.connect()), new DatabaseParser());
		mapFile = new File("import\\resources\\Gta3_map.gif");
		city = new City(11, "test name", "test desc");
		cityId = gcmDataExecutor.addCity(city);
		city = new City(cityId, "test name", "test desc");
		gcmDataExecutor.actionCityEdit(new CitySubmission(city, ActionTaken.ADD), true);
		map = new Map(12, name, description, width, height, new Coordinates(), 0, null, null);
		mapId = gcmDataExecutor.addMapToCity(cityId, map, mapFile);
		map = new Map(mapId, name, description, width, height, new Coordinates(), 0, null, null);
		gcmDataExecutor.actionMapEdit(new MapSubmission(cityId, map, mapFile, ActionTaken.ADD), true);

//		map = new Map(mapId, name, description, width, height, new Coordinates(), 0, null, null);
//		siteId = gcmDataExecutor.addNewSiteToCity(cityId,
//				new Site("name", "desc", "type", false, new Coordinates(7, 9)));
//		gcmDataExecutor.addExistingSiteToMap(mapId, siteId);
	}

//	@Test
//	void userTest() throws SQLException {
//		String username = "editor", password = "editor", firstName = "first", lastName = "last", email = "aa",
//				phoneNumber = "052";
//		gcmDataExecutor.addUser(username, password, new User(firstName, lastName, email, phoneNumber));
//		System.out.println(gcmDataExecutor.verifyUser(username, password));
//		assertEquals(RequestState.editor, gcmDataExecutor.verifyUser(username, password));
//		assertNotEquals(RequestState.editor, gcmDataExecutor.verifyUser(username, "bla"));
//		assertFalse(gcmDataExecutor.addUser(username, "bla", new User(firstName, lastName, email, phoneNumber)));
//	}
//
//	@Test
//	void getMapDetailsTest() throws SQLException {
//		Map mapReceived = gcmDataExecutor.getMapDetails(mapId);
//		assertNull(mapReceived);
//		gcmDataExecutor.actionMapAddEdit(map, true);
//		mapReceived = gcmDataExecutor.getMapDetails(mapId);
//
//		assertEquals(width, mapReceived.getWidth());
//		assertEquals(height, mapReceived.getHeight());
//		assertTrue(mapReceived.getSites().isEmpty());
//
//		gcmDataExecutor.actionSiteAddEdit(site, true);
//		Map newMapReceived = gcmDataExecutor.getMapDetails(mapId);
//		assertFalse(newMapReceived.getSites().isEmpty());
//
//		
////		Site site = map.getSites().get(0);
////		assertEquals(siteId, site.getId());
////		assertEquals("name", site.getName());
////		assertEquals("desc", site.getDescription());
////		assertEquals("type", site.getSiteType());
////		assertEquals(false, site.isAccessibleForDisabled());
//
//	}
//
//	@Test
//	void getMapFileTest() throws SQLException {
//		File mapFile2 = gcmDataExecutor.getMapFile(mapId);
//		assertEquals(mapFile, mapFile2);
//	}
//
////	@Test
////	void deleteMapTest() throws SQLException {
////		gcmDataExecutor.deleteMapEdit(mapId);
////		gcmDataExecutor.actionMapDeleteEdit(map, true);
////		assertNull(gcmDataExecutor.getMapDetails(mapId));
////		assertNull(gcmDataExecutor.getMapFile(mapId));
////	}
//
//	@Test
//	void getUserTest() throws SQLException {
//		gcmDataExecutor.addUser(".", ".", new User("a", "b", "c", "d"));
//		User user = gcmDataExecutor.getUserDetails(".");
//		assertEquals("a", user.getFirstName());
//
//		assertEquals("c", user.getEmail());
//
//	}

	@Test
	void deleteCityTest() throws SQLException {
		gcmDataExecutor.deleteCityEdit(cityId);
		city = gcmDataExecutor.getCityByMapId(mapId);
		assertNotNull(city);
		System.err.println("mapId=" + mapId);
		assertNotNull(gcmDataExecutor.getMapDetails(mapId));
		System.out.println("cityid = " + city.getId());
		gcmDataExecutor.actionCityEdit(new CitySubmission(city, ActionTaken.DELETE), true);
		city = gcmDataExecutor.getCityByMapId(mapId);
		assertNull(city);
		assertNull(gcmDataExecutor.getMapDetails(mapId));
		assertNull(gcmDataExecutor.getMapFile(mapId));
	}
}
