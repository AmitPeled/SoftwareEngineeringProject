package gcmDataAccess.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataAccess.editor.EditorDAO;
import dataAccess.search.SearchDAO;
import gcmDataAccess.GcmDAO;
import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import users.User;

public class SearchTest {
	static SearchDAO searchDAO;
	static EditorDAO editorDAO;
	static GcmDAO gcmDAO;
	static String cityName = "cityName", cityDescription = "nice city", siteName = "siteName",
			siteDescription = "bad site";
	static int mapId;
	static int siteId;
	static int cityId;

	@BeforeAll
	static void setAll() {
		// by now, editors load to the published section.
		gcmDAO = new GcmDAO();
		gcmDAO.register("editor", "editor", new User("", "", "", ""));
		editorDAO = gcmDAO;
		searchDAO = gcmDAO;
		cityId = editorDAO.addCity(new City(1, cityName, cityDescription));
		siteId = editorDAO.addNewSiteToCity(cityId,
				new Site(1, siteName, siteDescription, "museum", false, new Coordinates()));

	}

	@BeforeEach
	void deleteInsertions() {
		gcmDAO.deleteContent(mapId);
		mapId = editorDAO.addMapToCity(cityId, new Map(12f, 8.4f), new File("import\\resources\\Gta3_map.gif"));
		editorDAO.addExistingSiteToMap(mapId, siteId);
	}

	@AfterAll
	static void eraseAll() {
		gcmDAO.deleteContent(mapId);
		// cityId

	}

	@Test
	void getMapsByCityNameTest() {
		List<Map> maps = searchDAO.getMapsByCityName(cityName);
		assertEquals(1, maps.size());
		assertEquals(12f, maps.get(0).getWidth());
		assertEquals(8.4f, maps.get(0).getHeight());
	}

	@Test
	void getMapsBySiteNameTest() {
		List<Map> maps = searchDAO.getMapsBySiteName(siteName);
		assertEquals(1, maps.size());
		editorDAO.addExistingSiteToMap(mapId, siteId);
		maps = searchDAO.getMapsBySiteName(siteName);
		assertEquals(1, maps.size()); // the two sites contain to the same map
		assertEquals(12f, maps.get(0).getWidth());
		assertEquals(8.4f, maps.get(0).getHeight());
	}

	@Test
	void getMapsByDescriptionTest() {
		List<Map> maps = searchDAO.getMapsByDescription(cityDescription);
		assertEquals(1, maps.size());
		maps = searchDAO.getMapsByDescription(cityDescription.substring(1));
		assertEquals(1, maps.size());
		maps = searchDAO.getMapsByDescription(cityDescription + "a");
		assertEquals(0, maps.size());

		maps = searchDAO.getMapsByDescription(siteDescription);
		assertEquals(1, maps.size());
		maps = searchDAO.getMapsByDescription(siteDescription.substring(1));
		assertEquals(1, maps.size());
		maps = searchDAO.getMapsByDescription(siteDescription + "a");
		assertEquals(0, maps.size());
		int siteId = editorDAO.addNewSiteToCity(cityId,
				new Site(1, "", siteDescription + "a", "a", false, new Coordinates()));
		int mapId = editorDAO.addMapToCity(cityId, new Map(1f, 1f), null);
		editorDAO.addExistingSiteToMap(mapId, siteId);
		maps = searchDAO.getMapsByDescription(siteDescription);
		assertEquals(2, maps.size());
		assertEquals(12f, maps.get(0).getWidth());
		assertEquals(8.4f, maps.get(0).getHeight());
		assertEquals(1f, maps.get(1).getWidth());
		assertEquals(1f, maps.get(1).getHeight());
		gcmDAO.deleteContent(mapId);

	}
}
