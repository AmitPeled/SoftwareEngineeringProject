package dataAccess.contentManager;

import java.util.List;

import maps.City;
import maps.Map;
import maps.Site;

public interface ContentManagerDAO {
	/**
	 * changeId is the ID field of the edited object
	 */
	void discardChange(int changeId);

	void approveChange(int changeId);

	List<Map> getMapsChanges();

	List<Site> getSitesChanges();

	List<City> getCitiesChanges();
	
	void changeMapPrice(int mapId, double newPrice);

}
