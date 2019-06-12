package dataAccess.editor;

import java.io.File;
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

	public int addExistingSiteToTour(int tourId, int siteId, int siteDurance);

	public int deleteSiteFromMap(int mapId, int siteId);

	public int deleteSiteFromTour(int mapId, int siteId);

//	public int DeleteTourFromMap(int mapId, int siteId);

//	/**
//	 * Function to edit content. with this function you add, update and delete content.
//	 * to Update insert contentId = id of the object to update, editedContent = the content to update the object to. 
//	 * to Add new object insert contentId = -1 (as there is no id yet for the content), editedContent = new content to add. 
//	 * to Delete, insert contentId = id of the content to delete, and editedContent = null (as you want to erase it)
//	 */
//
//	public int editContent(int contentId, Object editedContent);
	/**
	 * to replace a content by a new value. (the content that being modified is the
	 * meta fields only, i.e name description etc.)
	 */
	public int updateContent(int contentId, Object newContent); //site,map,tours

//	/**
//	 * entirely delete the content and all its occurrences. for example, by deleting
//	 * a site it is deleted from the city it contained in, and from all the maps
//	 * contained it (in contrast to deleteSiteFromMap, which keeps the site
//	 * existence in the system but deletes its occurrence just in the map)
//	 */
//	public int deleteContent(int contentId);
}
