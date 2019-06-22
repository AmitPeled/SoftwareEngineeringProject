package mapViewer;

import java.util.HashSet;
import gcmDataAccess.GcmDAO;
import maps.Map;
import maps.Site;

public final class MapViewerFactory {
	public static MapViewer getMapViewer(GcmDAO gcmDAO, int mapId) {
		int cityId = gcmDAO.getCityByMapId(mapId).getId();
		Map map = gcmDAO.getMapDetails(mapId);
		float width = map.getWidth();
		float height = map.getHeight();
		
		System.out.println("Creating map viewer component: cityId-"+cityId+" | width -"+width+" | height-"+height);
		return new MapViewerComponent(
				"file:Import/resources/Gta3_map.gif",
				new HashSet<MapViewerListener>(),
				new HashSet<Site>(gcmDAO.getCitySites(cityId)),
				width,
				height);
	}

//	private static Set<Site> getAllMapSites() {
//		Set<Site> sites= new HashSet<Site>();
//		sites.add(new Site(5, "Some place", new Coordinates(300,400)));
//		sites.add(new Site(5, "Some other place", new Coordinates(500,150)));
//		sites.add(new Site(5, "Some place out of bounds", new Coordinates(541564654,4564681)));
//		return sites;
//	}
}
