package database.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import approvalReports.ActionTaken;
import approvalReports.ObjectsEnum;
import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.tourApprovalReports.TourSubmission;
import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.GcmDataExecutor;
import database.execution.IGcmDataExecute;
import database.objectParse.DatabaseParser;
import database.objectParse.CurrentEditStatus;
import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import maps.Tour;
import queries.RequestState;
import users.User;

/**
 * @author amit
 *
 */
class GcmDatabaseExecutorTest {
	static GcmDataExecutor gcmDataExecutor;
	static int             cityId;
	static int             mapId       = 2;
	static int             siteId;
	static Map             map;
	static Site            site;
	static City            city;
	static File            mapFile;
	static float           width       = 112.1f;
	static float           height      = 11.1f;
	static String          description = ".";
	static String          name        = ".1";

	@BeforeAll
	static void setAll() throws IllegalArgumentException, SQLException, IOException {
		gcmDataExecutor = new GcmDataExecutor(new DatabaseExecutor(DBConnector.connect()), new DatabaseParser());
		mapFile = new File("import\\resources\\Gta3_map.gif");
		city = new City("test name", "test desc");
		cityId = gcmDataExecutor.addCity(city);
		city = new City(cityId, "test name", "test desc");
		gcmDataExecutor.actionCityEdit(new CitySubmission(city, ActionTaken.ADD), true);
		map = new Map(12, name, description, width, height, new Coordinates(), 0, null, null);
		mapId = gcmDataExecutor.addMapToCity(cityId, map, Files.readAllBytes(mapFile.toPath()));
		map = new Map(mapId, name, description, width, height, new Coordinates(), 0, null, null);
		gcmDataExecutor.actionMapEdit(new MapSubmission(cityId, map, Files.readAllBytes(mapFile.toPath()), ActionTaken.ADD), true);

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

//	@Test
//	void deleteCityTest() throws SQLException {
//		gcmDataExecutor.deleteCityEdit(cityId);
//		city = gcmDataExecutor.getCityByMapId(mapId);
//		assertNotNull(city);
////		System.err.println("mapId=" + mapId);
//		assertNotNull(gcmDataExecutor.getMapDetails(mapId));
////		System.out.println("cityid = " + city.getId());
//		gcmDataExecutor.actionCityEdit(new CitySubmission(city, ActionTaken.DELETE), true);
//		city = gcmDataExecutor.getCityByMapId(mapId);
//		assertNull(city);
//		assertNull(gcmDataExecutor.getMapDetails(mapId));
//		assertNull(gcmDataExecutor.getMapFile(mapId));
//	}

//	@Test
//	void addSiteTest() throws SQLException {
//		site = new Site("site name", "site", "type", true, new Coordinates());
//		siteId = gcmDataExecutor.addNewSiteToCity(cityId, site);
//		List<SiteSubmission> siteSubmissions = gcmDataExecutor.getSiteSubmissions();
//		assertFalse(siteSubmissions.isEmpty());
//		for (SiteSubmission submission : gcmDataExecutor.getSiteSubmissions()) {
//			System.out.println("approving site. " + submission.getActionTaken() + ", containingType="
//					+ submission.getContainingObjectID() + ", containingId=" + submission.getContainingObjectType() + ", "
//					+ submission.getSite().getDescription());
//			gcmDataExecutor.actionSiteEdit(submission, true);
//		}
//		gcmDataExecutor.UpdateSite(siteId, new Site("updated name", "updated desc", "type", false, new Coordinates()));
//		assertEquals(site.getDescription(), siteSubmissions.get(0).getSite().getDescription());
//		for (SiteSubmission submission : gcmDataExecutor.getSiteSubmissions()) {
//			System.out.println("approving site. " + submission.getActionTaken() + ", containingType="
//					+ submission.getContainingObjectID() + ", containingId=" + submission.getContainingObjectType() + ", "
//					+ submission.getSite().getDescription());
//			gcmDataExecutor.actionSiteEdit(submission, true);
//		}
//		System.err.println("site received: " + gcmDataExecutor.getSite(siteId).getId());
//		assertTrue(gcmDataExecutor.getSiteSubmissions().isEmpty());
////		assertNotNull(gcmDataExecutor.getSite(siteId));
////		assertEquals(gcmDataExecutor.getSite(siteId).getDescription(), "updated desc");
////		gcmDataExecutor.actionSiteEdit(tour, action);
////		assertNull(gcmDataExecutor.getSiteSubmissions());
////		System.err.println("mapId=" + mapId);
////		assertNotNull(gcmDataExecutor.getMapDetails(mapId));
////		System.out.println("cityid = " + city.getId());
////		gcmDataExecutor.actionCityEdit(new CitySubmission(city, ActionTaken.DELETE), true);
////		city = gcmDataExecutor.getCityByMapId(mapId);
////		assertNull(city);
////		assertNull(gcmDataExecutor.getMapDetails(mapId));
////		assertNull(gcmDataExecutor.getMapFile(mapId));
//	}
//	@Test
//	void addMapTest() throws SQLException {
//		map = new Map("map name", "map", 1, 1, new Coordinates());
//		mapId = gcmDataExecutor.addMapToCity(cityId, map, mapFile);
//		List<MapSubmission> mapSubmissions = gcmDataExecutor.getMapSubmissions();
//		assertFalse(mapSubmissions.isEmpty());
//
//		for (MapSubmission submission : gcmDataExecutor.getMapSubmissions()) {
//			System.out.println("map to approve " + submission.getActionTaken() + ", containingCityId="
//			        + submission.getContainingCityID() /* + ", " + submission.getMap().getDescription() */);
////			gcmDataExecutor.actionMapEdit(submission, false);
//		}
//	}
//	@Test
//	void getMapSitesTest() throws SQLException {
//		List<Site> sitesAddedToMap = new ArrayList<>();
//		for (int i = 0; i < 7; i++) {
//			site = new Site("agami's site", "site number " + i, "museum", false, new Coordinates());
//			siteId = gcmDataExecutor.addNewSiteToCity(cityId, site);
//			site = new Site(siteId, "agami's site", "site number " + i, "museum", false, new Coordinates());
//			gcmDataExecutor.actionSiteEdit(new SiteSubmission(cityId, ObjectsEnum.CITY, site, ActionTaken.ADD), true);
//			gcmDataExecutor.addExistingSiteToMap(mapId, siteId);
//			gcmDataExecutor.actionSiteEdit(new SiteSubmission(mapId, ObjectsEnum.MAP, site, ActionTaken.ADD), true);
//			sitesAddedToMap.add(site);
//		}
//		map = gcmDataExecutor.getMapDetails(mapId);
//		int i = 0;
//		assertEquals(map.getSites().size(), sitesAddedToMap.size());
//		for (Site site : map.getSites()) {
//			assertEquals(site.getDescription(), sitesAddedToMap.get(i).getDescription());
//			i++;
//		}
//	}
	@Test
	void getMasToursTest() throws SQLException {
		Tour tour;
		int tourId;
		List<Tour> toursAddedToMap = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			tour = new Tour("tour number " + i);
			tourId = gcmDataExecutor.addNewTourToCity(cityId, tour);
			tour = new Tour(tourId, "tour number " + i, new ArrayList<>(), new ArrayList<>());
			gcmDataExecutor.actionTourEdit(new TourSubmission(cityId, ObjectsEnum.CITY, tour, ActionTaken.ADD), true);
			gcmDataExecutor.addExistingTourToMap(mapId, tourId);
			gcmDataExecutor.actionTourEdit(new TourSubmission(mapId, ObjectsEnum.MAP, tour, ActionTaken.ADD), true);
			toursAddedToMap.add(tour);
		}
		map = gcmDataExecutor.getMapDetails(mapId);
		int i = 0;
		assertEquals(map.getTours().size(), toursAddedToMap.size());
		for (Tour mapTour : map.getTours()) {
			assertEquals(mapTour.getDescription(), toursAddedToMap.get(i).getDescription());
			i++;
		}
	}
//		gcmDataExecutor.UpdateSite(siteId, new Site("updated name", "updated desc", "type", false, new Coordinates()));
//		assertEquals(site.getDescription(), siteSubmissions.get(0).getSite().getDescription());
//		for (SiteSubmission submission : gcmDataExecutor.getSiteSubmissions()) {
//			System.out.println("approving site. " + submission.getActionTaken() + ", containingType="
//					+ submission.getContainingObjectID() + ", containingId=" + submission.getContainingObjectType() + ", "
//					+ submission.getSite().getDescription());
//			gcmDataExecutor.actionSiteEdit(submission, true);
//		}
//		System.err.println("site received: " + gcmDataExecutor.getSite(siteId).getId());
//		assertTrue(gcmDataExecutor.getSiteSubmissions().isEmpty());
//		assertNotNull(gcmDataExecutor.getSite(siteId));
//		assertEquals(gcmDataExecutor.getSite(siteId).getDescription(), "updated desc");
//		gcmDataExecutor.actionSiteEdit(tour, action);
//		assertNull(gcmDataExecutor.getSiteSubmissions());
//		System.err.println("mapId=" + mapId);
//		assertNotNull(gcmDataExecutor.getMapDetails(mapId));
//		System.out.println("cityid = " + city.getId());
//		gcmDataExecutor.actionCityEdit(new CitySubmission(city, ActionTaken.DELETE), true);
//		city = gcmDataExecutor.getCityByMapId(mapId);
//		assertNull(city);
//		assertNull(gcmDataExecutor.getMapDetails(mapId));
//		assertNull(gcmDataExecutor.getMapFile(mapId));
//	}
//
//	@Test
//	void pricesTest() throws SQLException {
////		assertThrows(IllegalArgumentException.class, () -> gcmDataExecutor.changeCityPrices(1, new ArrayList<Double>() {
////			{
////				add(12.2);
////				add(15d);
////				add(17d);
////				add(18d);
////			}
////		}));
////		gcmDataExecutor.changeCityPrices(1, new ArrayList<Double>() {
////			{
////				add(12.2);
////				add(15d);
////				add(17d);
////				add(18d);
////				add(18.8d);
////				add(19.4);
////				add(19.6d);
////			}
////		});
//		gcmDataExecutor.approveCityPrice(1, new ArrayList<Double>() {
//			{
//				add(12.2);
//				add(15d);
//				add(17d);
//				add(18d);
//				add(18.8d);
//				add(19.4);
//				add(19.6d);
//			}
//		}, true);
//
//	}

}
