package database.execution.selection;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.objectParse.DatabaseParser;
import database.objectParse.Status;
import maps.City;
import maps.Map;
import maps.Tour;

class GetterTest {
	ContentGetter contentGetter = new ContentGetter(new DatabaseExecutor(DBConnector.connect()), new DatabaseParser());

	@Test
	void getMapFileTest() throws SQLException {
		File file = contentGetter.getMapFile(10);
		assertEquals("import\\resources\\Gta3_map.gif", file.toString());
	}

	@Test
	void getMapDetails() throws SQLException {
		Map map = contentGetter.getMapDetails(1);
		assertEquals("mapDescription", map.getDescription());
		assertEquals(1, map.getSites().size());
		assertEquals("siteDescription", map.getSites().get(0).getDescription());
		assertEquals(1, map.getTours().size());
		assertEquals("tourDescription", map.getTours().get(0).getDescription());
		map = contentGetter.getMapDetailsByStatus(1, Status.toDelete);
		assertNull(map);
		map = contentGetter.getMapDetails(-1);
		assertNull(map);
	}

	@Test
	void getCity() throws SQLException {
		City city = contentGetter.getCityById(1);
		assertEquals("cityDescription", city.getDescription());
		city = contentGetter.getCityById(-1);
		assertNull(city);
	}

	@Test
	void getTour() throws SQLException {
		Tour tour = contentGetter.getTour(1);
		assertEquals("tourDescription", tour.getDescription());
		tour = contentGetter.getTour(-1);
		assertNull(tour);
		tour = contentGetter.getTourByStatus(1,Status.toDelete);
		assertNull(tour);
		tour = contentGetter.getTourByStatus(2,Status.toAdd);
		assertEquals("tourDesc", tour.getDescription());
	}

}
