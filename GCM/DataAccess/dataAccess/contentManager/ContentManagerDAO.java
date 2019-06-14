package dataAccess.contentManager;

import java.util.List;

import maps.City;
import maps.Map;
import maps.Site;

public interface ContentManagerDAO {

	/**
	 * @param action the action to take on the edit. action true is approve, false
	 *               discard.
	 */	
	void actionMapAddEdit(Map map, boolean action);
	void actionMapUpdateEdit(Map map, boolean action);
	void actionMapDeleteEdit(Map map, boolean action);
	
	void actionCityAddEdit(City city, boolean action);
	void actionCityUpdateEdit(City city, boolean action);
	void actionCityDeleteEdit(City city, boolean action);

	void actionSiteAddEdit(Site site, boolean action);
	void actionSiteUpdateEdit(Site site, boolean action);
	void actionSiteDeleteEdit(Site site, boolean action);

	/**
	 * For editions that were insertions or update, the content that will be
	 * returned is the new or updated content (as expected). in case the change was
	 * deletion, the object that will be returned is null. to see the changes before
	 * the edit and after, you can take with EditorDAO the original object before
	 * the edition. (in case the edit was insertion of a new content, the original
	 * version before edition will be null, i.e empty). in order to get the map file
	 * (to compare with the origin file, if exists) use EditorDAO.getMapFile (as the
	 * ContentManager privilege bigger than editor's).
	 */
	List<Map> getMapsAddEdits();
	List<Map> getMapsUpdateEdits();
	List<Map> getMapsDeleteEdits();

	List<Site> getSitesAddEdits();
	List<Site> getSitesUpdateEdits();
	List<Site> getSitesDeleteEdits();

	List<City> getCitiesAddEdits();
	List<City> getCitiesUpdateEdits();
	List<City> getCitiesDeleteEdits();

	void changeMapPrice(int mapId, double newPrice);

}
