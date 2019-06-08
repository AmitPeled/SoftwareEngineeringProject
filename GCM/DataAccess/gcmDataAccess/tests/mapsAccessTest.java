package gcmDataAccess.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dataAccess.editor.EditorDAO;
import gcmDataAccess.GcmDAO;
import maps.City;
import maps.Map;
import users.User;

class mapsAccessTest {
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
		cityId = mapAccess.addCity(new City(97, "name", "desc"));
		map = new Map(97, 13.1f, 2.2f);
		mapFile = new File("import\\resources\\Gta3_map.gif");
		mapId = mapAccess.addMapToCity(cityId, map, mapFile);
		assertEquals(mapId, mapAccess.getMapDetails(mapId).getId());
		assertEquals(map.getHeight(), mapAccess.getMapDetails(mapId).getHeight());
		assertEquals(map.getWidth(), mapAccess.getMapDetails(mapId).getWidth());
		assertEquals(mapFile, mapAccess.getMapFile(mapId));
		mapAccess.deleteMap(mapId);
		assertThrows(NullPointerException.class, () -> mapAccess.getMapDetails(mapId).getHeight());
		assertNull(mapAccess.getMapFile(mapId));

	}
}
