package dataAccess.contentManager;

import java.util.List;

import maps.Map;
import maps.Site;

public interface ContentManagerDAO {

	/**
	 * changeId is the ID field of the edited object
	 */
	void discardMapChange(int changeId);

	void approveMapChange(int changeId);

	/**
	 * For editions that were insertions or update, the content that will be
	 * returned is the new or updated content (as expected). in case the change was deletion, the
	 * object that will be returned is null. to see the changes before the edit and
	 * after, you can take with EditorDAO the original object before the edition.
	 * (in case the edit was insertion of a new content, the original version
	 * before edition will be null, i.e empty).
	 * in order to get the map file (to compare with the origin file, if exists) use EditorDAO.getMapFile (as 
	 * the ContentManager privilege bigger than editor's).
	 */
	List<Map> getMapsEditions();
	
	List<Site> getSitesEditions();
	
	List<Map> getCitiesEditions();


	void changeMapPrice(int mapId, double newPrice);

}
