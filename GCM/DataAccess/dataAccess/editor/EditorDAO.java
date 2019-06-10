package dataAccess.editor;

import java.io.File;

import maps.City;
import maps.Map;
import maps.Site;

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

	public Map getMapDetails(int mapID);

	/**
	 * Retrieves Map image file of a map by its ID value
	 * 
	 * @param mapID The map ID value
	 * @return Map image file of the map associated with this mapID
	 */
	public File getMapFile(int mapID);

	public int addMapToCity(int cityId, Map mapDetails, File mapFile);

	public int addCity(City city);

	public int addNewSiteToCity(int cityId, Site site);

	public int addExistingSiteToMap(int mapId, int siteId);

	public int DeleteSiteFromMap(int mapId, int siteId);

	/**
	 * replace content with another content.
	 */
	public int updateContent(int contentId, Object newContent);

	/**
	 * entirely delete the content and all its occurrences. 
	 * for example, by deleting a site it is deleted from the city it contained in, and from
	 * all the maps contained it (in contrast to deleteSiteFromMap, which keeps the
	 * site existence in the system but deletes its occurrence just in the map)
	 */
	public int deleteContent(int contentId);
}
