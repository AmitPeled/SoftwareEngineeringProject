package database.execution.selection;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import approvalReports.ActionTaken;
import approvalReports.ObjectsEnum;
import approvalReports.tourApprovalReports.TourSubmission;
import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.GcmDataExecutor;
import database.objectParse.DatabaseParser;
import database.objectParse.Status;
import maps.Map;
import maps.Tour;

class GetterTest {
	GcmDataExecutor contentGetter = new GcmDataExecutor(new DatabaseExecutor(DBConnector.connect()),
			new DatabaseParser());

//	@Test
//	void getMapFileTest() throws SQLException {
//		File file = contentGetter.getMapFile(10);
//		assertEquals("import\\resources\\Gta3_map.gif", file.toString());
//	}

	@Test
	void getMapDetails() throws SQLException {
		Map map = contentGetter.getMapDetails(1);
		assertEquals("mapDescription", map.getDescription());
		assertEquals(1, map.getSites().size());
		assertEquals("siteDescription", map.getSites().get(0).getDescription());
		assertEquals(1, map.getTours().size());
		Tour tour = new Tour("d");
		int tourId = contentGetter.addNewTourToCity(1, tour, Status.ADD);
		System.err.println(tourId);
		contentGetter.actionTourEdit(new TourSubmission(1,ObjectsEnum.CITY,new Tour(tourId, "d", null, null), ActionTaken.ADD), true);
//		contentGetter.addSiteToTourByStatus(tourId, 1, 5, Status.ADD);

		contentGetter.addExistingTourToMap(1, tourId, Status.PUBLISH);
//		contentGetter.actionTourEdit(new TourSubmission(1,ObjectsEnum.MAP,new Tour(tourId, "d", null, null), ActionTaken.ADD), true);

		map = contentGetter.getMapDetails(1);

		assertEquals("tourDescription", map.getTours().get(0).getDescription());
		assertEquals("d", map.getTours().get(1).getDescription());
//		assertEquals("siteDescription", map.getTours().get(1).getSites().get(0).getDescription());
//		map = contentGetter.getMapDetailsByStatus(1, Status.DELETE);
//		assertNull(map);
//		map = contentGetter.getMapDetails(-1);
//		assertNull(map);

	}
//
//	@Test
//	void getCity() throws SQLException {
//		City city = contentGetter.getCityById(1);
//		assertEquals("cityDescription", city.getDescription());
//		city = contentGetter.getCityById(-1);
//		assertNull(city);
//	}

	@Test
	void getTour() throws SQLException {
//		Tour tour = contentGetter.getTour(1);
//		assertEquals("tourDescription", tour.getDescription());
//		tour = contentGetter.getTour(-1);
//		assertNull(tour);
//		tour = contentGetter.getTourByStatus(1, Status.DELETE);
//		assertNull(tour);
//		tour = contentGetter.getTourByStatus(2, Status.ADD);
//		assertEquals("tourDesc", tour.getDescription());
	}

}
