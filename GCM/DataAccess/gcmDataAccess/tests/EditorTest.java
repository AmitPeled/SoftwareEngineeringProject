package gcmDataAccess.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dataAccess.editor.EditorDAO;
import gcmDataAccess.GcmDAO;
import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import users.User;

class EditorTest {
	static EditorDAO mapAccess;
	int cityId;
	int mapId;
	Map map;
	File mapFile;

	@BeforeAll
	static void setAll() {
		GcmDAO gcmDAO = new GcmDAO();
		gcmDAO.register("editor", "editor", new User("", "", "", ""));
		mapAccess = gcmDAO;
	}

	@Test
	void testAll() {
		cityId = mapAccess.addCity(new City(97, "city1", "city1"));
		map = new Map("map name","nice map",13.1f, 2.2f, new Coordinates());
		mapFile = new File("import\\resources\\Gta3_map.gif");
		mapId = mapAccess.addMapToCity(cityId, map, mapFile);
		int siteId = mapAccess.addNewSiteToCity(cityId, new Site(1, "gabri site description", new Coordinates()));
		mapAccess.addExistingSiteToMap(mapId, siteId);
		Map map1 = mapAccess.getMapDetails(mapId);
		assertEquals("map name", map1.getName());
//		assertEquals(mapId, mapAccess.getMapDetails(mapId).getId());
//		assertEquals(map.getHeight(), mapAccess.getMapDetails(mapId).getHeight());
//		assertEquals(map.getWidth(), mapAccess.getMapDetails(mapId).getWidth());
//		assertEquals(mapFile, mapAccess.getMapFile(mapId));
//		mapAccess.deleteContent(mapId);
//		assertThrows(NullPointerException.class, () -> mapAccess.getMapDetails(mapId).getHeight());
//		assertNull(mapAccess.getMapFile(mapId));

	}
}
