package purchase.mapContent;

import java.io.File;

import maps.Map;

public class MapContent {
	private Map mapDetails;
	private File mapFile;
	private int id_ContainingCity;

	public MapContent(Map mapDetails, File mapFile, int id_ContainingCity) {
		if (mapDetails == null)
			throw new IllegalArgumentException("Map must contain details");
		if (mapFile == null)
			throw new IllegalArgumentException("Map must contain a file");
		if (id_ContainingCity <= 0)
			throw new IllegalArgumentException("City id has to be a positive number");
		this.mapDetails = mapDetails;
		this.mapFile = mapFile;
		this.id_ContainingCity = id_ContainingCity;
	}

	public Map getMapDetails() {
		return mapDetails;
	}

	public File getMapFile() {
		return mapFile;
	}

	public int getId_ContainingCity() {
		return id_ContainingCity;
	}
}
