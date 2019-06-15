package mapViewer;

import java.util.HashSet;
import java.util.Set;

import maps.Coordinates;
import maps.Site;

public final class MapViewerFactory {
	public static MapViewer getMapViewer(int mapId) {
		return new MapViewerComponent(
				"file:Import/resources/Gta3_map.gif",
				new HashSet<MapViewerListener>(),
				getAllMapSites(),
				800,
				600);
	}

	private static Set<Site> getAllMapSites() {
		Set<Site> sites= new HashSet<Site>();
		sites.add(new Site(5, "Some place", new Coordinates(300,400)));
		sites.add(new Site(5, "Some other place", new Coordinates(500,150)));
		sites.add(new Site(5, "Some place out of bounds", new Coordinates(541564654,4564681)));
		return sites;
	}
}
