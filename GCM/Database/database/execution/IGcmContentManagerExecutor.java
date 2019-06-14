package database.execution;

import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;

public interface IGcmContentManagerExecutor {
	void approveSiteEdit(Site site);

	void approveCityEdit(City city);

	void approveMapEdit(Map map);

	void approveTourEdit(Tour tour);

	void editMapPrice(int mapId, double newPrice); // edit map to map with new price
}
