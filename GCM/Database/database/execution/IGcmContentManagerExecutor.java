package database.execution;

import java.sql.SQLException;
import java.util.List;

import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;

public interface IGcmContentManagerExecutor {

	/**
	 * @param action the action to take on the edit. action true is approve, false
	 *               discard.
	 */	
	void actionMapAddEdit(Map map, boolean action) throws SQLException;
	void actionMapUpdateEdit(Map map, boolean action) throws SQLException;
	void actionMapDeleteEdit(Map map, boolean action) throws SQLException;
	
	void actionCityAddEdit(City city, boolean action) throws SQLException;
	void actionCityUpdateEdit(City city, boolean action) throws SQLException;
	void actionCityDeleteEdit(City city, boolean action) throws SQLException;

	void actionSiteAddEdit(Site site, boolean action) throws SQLException;
	void actionSiteUpdateEdit(Site site, boolean action) throws SQLException;
	void actionSiteDeleteEdit(Site site, boolean action) throws SQLException;
	
	void actionTourAddEdit(Site site, boolean action) throws SQLException;
	void actionTourUpdateEdit(Site site, boolean action) throws SQLException;
	void actionTourDeleteEdit(Site site, boolean action) throws SQLException;

	/**
	 * editors content editons. you can take with EditorDAO the original object before the edition. 
	 */
	List<Map> getMapsAddEdits() throws SQLException;
	List<Map> getMapsUpdateEdits() throws SQLException;
	List<Map> getMapsDeleteEdits() throws SQLException;

	List<Site> getSitesAddEdits() throws SQLException;
	List<Site> getSitesUpdateEdits() throws SQLException;
	List<Site> getSitesDeleteEdits() throws SQLException;

	List<Tour> getToursAddEdits()throws SQLException;
	List<Tour> getToursUpdateEdits()throws SQLException;
	List<Tour> getToursDeleteEdits()throws SQLException;
	
	List<City> getCitiesAddEdits() throws SQLException;
	List<City> getCitiesUpdateEdits() throws SQLException;
	List<City> getCitiesDeleteEdits() throws SQLException;

	List<Map> getMapsObjectAddedTo(int contentId); // gets list of the maps that the object is added to
	List<City> getCitiesObjectAddedTo(int contentId);// gets list of the cities that the object is added to
	List<Tour> getToursObjectAddedTo(int contentId);// gets list of the tours that the object is added to
	


	void editCityPrice(int cityId, double newPrice) throws SQLException;

}
