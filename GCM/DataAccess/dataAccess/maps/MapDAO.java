package dataAccess.maps;

import java.io.File;

import maps.Map;

public interface MapDAO {

	/**
	 * Retrieves Map details of a map by its ID value
	 * 
	 * @param mapID the map ID value. used for map identification in the Database
	 * @return The Map details of the map associated with this mapID
	 */
	public Map getMapDetails(int mapID); // -for map viewing and downloading

	/**
	 * Retrieves Map image file of a map by its ID value
	 * 
	 * @param mapID The map ID value
	 * @return Map image file of the map associated with this mapID
	 */
	public File getMapFile(int mapID); // -for map viewing and downloading
}
