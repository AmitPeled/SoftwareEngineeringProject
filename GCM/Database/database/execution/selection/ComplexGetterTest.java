package database.execution.selection;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import approvalReports.mapApprovalReports.MapSubmission;
import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.GcmDataExecutor;
import database.objectParse.DatabaseParser;


// the tests are based on the database content according to the moment tested.
class ComplexGetterTest {
	GcmDataExecutor gcmDataExecutor = new GcmDataExecutor(new DatabaseExecutor(DBConnector.connect()),
			new DatabaseParser());

//	@Test
//	void getCitySitesTest() throws SQLException {
//		List<Site> sites = gcmDataExecutor.getCitySites(1);
//		assertEquals(4, sites.size());
//		assertEquals(2, gcmDataExecutor.getCitySitesByStatus(1, Status.toAdd).size());
//		assertEquals("siteDescription", sites.get(0).getDescription());
//	}
//
//	@Test
//	void getCityToursTest() throws SQLException {
//		List<Tour> tours = gcmDataExecutor.getCityTours(1);
//		assertEquals(1, tours.size());
//		assertEquals("tourDescription", tours.get(0).getDescription());
//		tours = gcmDataExecutor.getCityToursByStatus(1, Status.toAdd);
//		assertEquals(9, tours.size());
//		assertEquals("asd", tours.get(0).getDescription());
//	}
//
//	@Test
//	void getCityByMapTest() throws SQLException {
//		City city = gcmDataExecutor.getCityByMapId(1);
//		assertEquals("cityDescription", city.getDescription());
//		assertNull(gcmDataExecutor.getCityByMapId(-1));
//		assertEquals("cityDescription", gcmDataExecutor.getCityByMapId(87, Status.toAdd).getDescription());
//		assertNull(gcmDataExecutor.getCityByMapId(80, Status.toDelete));
//		assertNull(gcmDataExecutor.getCityByMapId(150));
//	}
//
//	@Test
//	void getCityByTourTest() throws SQLException {
//		City city = gcmDataExecutor.getCityByTourId(1);
//		assertEquals("cityDescription", city.getDescription());
//		assertNull(gcmDataExecutor.getCityByTourId(-1));
//		assertEquals("name", gcmDataExecutor.getCityByTourId(2, Status.toAdd).getName());
//		assertEquals("cityDescription", gcmDataExecutor.getCityByTourId(12, Status.toAdd).getDescription());
//		assertNull(gcmDataExecutor.getCityByTourId(80, Status.toDelete));
//		assertNull(gcmDataExecutor.getCityByTourId(150));
//	}
//
//	@Test
//	void getCityBySiteTest() throws SQLException {
//		City city = gcmDataExecutor.getCityBySite(160);
//		assertEquals("cityDescription", city.getDescription());
//		assertNull(gcmDataExecutor.getCityBySite(-1));
//		assertEquals("cityDescription", gcmDataExecutor.getCityBySite(164, Status.toAdd).getDescription());
//		assertNull(gcmDataExecutor.getCityBySite(80, Status.toDelete));
//		assertNull(gcmDataExecutor.getCityBySite(150));
//	}
//	@Test
//	void getCitiesAddEditsTest() throws SQLException {
//		List<City> cities = gcmDataExecutor.getCitiesAddEdits();
//		assertEquals("fas", cities.get(2).getDescription());
//	}
//	@Test
//	void getCitiesUpdateEditsTest() throws SQLException {
//		List<City> cities = gcmDataExecutor.getCitiesUpdateEdits();
//		System.out.println(cities.size());
//		System.out.println(gcmDataExecutor.getCitiesDeleteEdits().size());
////		assertEquals("fsd", cities.get(3).getDescription());
//	}
//	@Test
//	void getMapsUpdateEditsTest() throws SQLException {
//		List<Map> maps = gcmDataExecutor.getMapsUpdateEdits();
//		System.out.println(maps.size());
//		System.out.println(gcmDataExecutor.getMapsAddEdits().size());
//		System.out.println(gcmDataExecutor.getMapsDeleteEdits().size());
////		assertEquals("fsd", cities.get(3).getDescription());
//	}
//	@Test
//	void getSitesSubmissions() throws SQLException {
//		List<SiteSubmission> sites = gcmDataExecutor.getSiteSubmissions();
////		System.out.println(sites);
////		System.out.println(gcmDataExecutor.getSiteSubmissionsByStatus(Status.toAdd));
//		sites = gcmDataExecutor.getSiteSubmissionsByStatus(Status.toAdd);
//		System.out.println("site addition in place 4 is added to " + sites.get(4).getContainingObjectType() + ", of id="
//				+ sites.get(4).getContainingObjectID() + ". site.description() = "
//				+ sites.get(4).getSite().getDescription());
//		System.out.println("site addition in place 6 is added to " + sites.get(6).getContainingObjectType()
//				+ ", of id=" + sites.get(6).getContainingObjectID() + ". site.description() = "
//				+ sites.get(6).getSite().getDescription());
//		System.out.println(gcmDataExecutor.getSiteSubmissionsByStatus(Status.toDelete));
//		sites = gcmDataExecutor.getSiteSubmissionsByStatus(Status.toUpdate);
//		System.out.println(sites.get(0).getSite().getDescription()+", action="+sites.get(0).getActionTaken());
////		assertEquals("fsd", cities.get(3).getDescription());
//	}
	@Test
	void getMapSubmissions() throws SQLException {
		List<MapSubmission> maps = gcmDataExecutor.getMapSubmissions();
		System.out.println(maps);
//		System.out.println(gcmDataExecutor.getSiteSubmissionsByStatus(Status.toAdd));
//		maps = gcmDataExecutor.getMapSubmissionsByStatus(Status.ADD);
//		System.out.println(maps);
//		System.out.println("site addition in place 4 is added to city of id="
//				+ maps.get(0).getContainingCityID() + ". map.description() = "
//				+ maps.get(0).getMap().getDescription());
//		maps = gcmDataExecutor.getMapSubmissionsByStatus(Status.UPDATE);
//		System.out.println(maps);
//		maps = gcmDataExecutor.getMapSubmissionsByStatus(Status.DELETE);
//		System.out.println(maps);
//		System.out.println("site addition in place 6 is added to " + maps.get(6).getContainingObjectType()
//				+ ", of id=" + maps.get(6).getContainingObjectID() + ". site.description() = "
//				+ maps.get(6).getSite().getDescription());
//		System.out.println(gcmDataExecutor.getSiteSubmissionsByStatus(Status.toDelete));
//		maps = gcmDataExecutor.getSiteSubmissionsByStatus(Status.toUpdate);
//		System.out.println(maps.get(0).getSite().getDescription()+", action="+maps.get(0).getActionTaken());
//		assertEquals("fsd", cities.get(3).getDescription());
	}

}
