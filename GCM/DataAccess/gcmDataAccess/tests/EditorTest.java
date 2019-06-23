package gcmDataAccess.tests;


import java.io.File;
import java.util.List;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import approvalReports.ActionTaken;
import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
import dataAccess.editor.EditorDAO;
import gcmDataAccess.GcmDAO;
import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import maps.Tour;
import users.User;

class EditorTest {
	static EditorDAO editorAccess;
	static GcmDAO gcmDAO;
	static int cityId;
	static int mapId;
	static int siteId;
	static int tourId;
	static Tour tour;
	static Site site;
	static Map map;
	static City city;
	static File mapFile;

	@BeforeAll
	static void setUp() {
		gcmDAO = new GcmDAO();
		gcmDAO.register("editor", "editor", new User("", "", "", ""));
		editorAccess = gcmDAO;
		city = new City(97, "CITY NAME", "CITY DESC");
		cityId = editorAccess.addCity(city);
		map = new Map("NAME","DESC",100.1f, 18.2f, new Coordinates());
		mapFile = new File("import\\resources\\Gta3_map.gif");
		site = new Site("SITE NAME", "SITE DESC", "TYPE", false, new Coordinates());
		tour = new Tour("TOUR DESC");
	}

	@BeforeEach
	void contentInsertion() {
		gcmDAO.actionCityEdit(new CitySubmission(new City(cityId, "CITY NAME", "CITY DESC"), ActionTaken.ADD), true);
		gcmDAO.updateMap(512, new Map("new name","new DESC",100.1f, 18.2f, new Coordinates()));
		List<MapSubmission> mapSubmissions = gcmDAO.getMapSubmissions();
		for(MapSubmission mapSubmission: mapSubmissions) {
			System.out.println(mapSubmission.getActionTaken());
		}
//		mapId = editorAccess.addMapToCity(cityId, map, mapFile);
//		siteId = editorAccess.addNewSiteToCity(cityId, site);
//		tourId = editorAccess.addNewTourToCity(cityId, tour);
		
	}

//	@AfterEach
//	void eraseMap() {
//		gcmDAO.deleteContent(mapId);
//	}
//
//	@AfterAll
//	static void checkErase() {
//		assertThrows(NullPointerException.class, () -> editorAccess.getMapDetails(mapId).getHeight());
//		assertNull(editorAccess.getMapFile(mapId));
//	}
//
	@Test
	void getMapTest() {
		List<MapSubmission> mapSubmissions = gcmDAO.getMapSubmissions();
		for(MapSubmission mapSubmission: mapSubmissions) {
			System.out.println(mapSubmission.getActionTaken());
		}
//		assertEquals(mapId, editorAccess.getMapDetails(mapId).getId());
//		assertEquals(map.getHeight(), editorAccess.getMapDetails(mapId).getHeight());
//		assertEquals(map.getWidth(), editorAccess.getMapDetails(mapId).getWidth());
//		assertEquals(mapFile, editorAccess.getMapFile(mapId));
	}
//
//	@Test
//	void addExistingSiteToMapTest() {
//		editorAccess.addExistingSiteToMap(mapId, siteId);
//		assertEquals(site.getSiteType(), editorAccess.getMapDetails(mapId).getSites().get(0).getSiteType());
//
//	}
//
//	@Test
//	void addExistingTourToMapTest() {
//		editorAccess.addExistingTourToMap(mapId, tourId);
//		editorAccess.getMapDetails(mapId).getTours().get(0);
//		assertEquals(tour.getDescription(), editorAccess.getMapDetails(mapId).getTours().get(0).getDescription());
//
//	}

}
