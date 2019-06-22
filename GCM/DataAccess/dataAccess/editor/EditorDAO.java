package dataAccess.editor;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;

/**
 * @author amit
 * 
 *         the class functionality is to retrieve and edit data. after an edit
 *         the change is 'pending', i.e waiting for a manager approval. the edit
 *         functions return the ID of the created change in the database.
 * 
 *         there are two sections in the database: unpublished, and published
 *         versions. edits are loaded to the unpublished section, and managers
 *         rule is to publish or discard these edits. eventually, system users
 *         are revealed only to the published section.
 * 
 */

public interface EditorDAO {

	/**
	 * the city contains the map
	 */

	List<Site> getCitySites(int cityId);

	City getCityByMapId(int mapId);

	public Map getMapDetails(int mapId);

	/**
	 * Retrieves Map image file of a map by its Id value
	 * 
	 * @param mapId The map Id value
	 * @return Map image file of the map associated with this mapId
	 */
	public File getMapFile(int mapId);

	public int addMapToCity(int cityId, Map mapDetails, File mapFile);

	public int addCity(City city);

	public int addNewSiteToCity(int cityId, Site site);

	public int addNewTourToCity(int cityId, Tour tour);

	public int addExistingSiteToMap(int mapId, int siteId);

	public int addExistingTourToMap(int mapId, int tourId);

	public void addExistingSiteToTour(int tourId, int siteId, int siteDurance);

	public int deleteSiteFromMap(int mapId, int siteId);

	public void deleteSiteFromTour(int mapId, int siteId);

	public int tourManager(int cityId, Tour tour);
	
	void updateCity(int cityId, City city) throws SQLException; 
	void deleteCityEdit(int cityId) throws SQLException; 
	void UpdateSite(int siteId, Site newSite) throws SQLException; 
	void deleteTourFromMap(int mapId, int tourId) throws SQLException;
	void deleteTourFromCity(int tourId) throws SQLException;
	List<Tour> getCityTours(int cityId) throws SQLException;
	void updateTour(int tourId, Tour tour) throws SQLException; 
	public void deleteSiteFromCity(int siteId) throws SQLException; 
	void updateMap(int mapId, Map newMap) throws SQLException; 
	Tour getTour(int tourId) throws SQLException;
	City getCity(int cityId);

}
