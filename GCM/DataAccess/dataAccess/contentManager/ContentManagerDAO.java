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
	 * editors content editons. you can take with EditorDAO the original object before the edition. 
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


	void editCityPrice(int cityId, double newPrice);

}
