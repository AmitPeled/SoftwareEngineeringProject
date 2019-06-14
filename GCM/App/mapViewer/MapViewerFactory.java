package mapViewer;

public final class MapViewerFactory {
	public static MapViewer getMapViewer(int mapId) {
		return new MapViewerComponent("file:Import/resources/Gta3_map.gif");
	}
}
