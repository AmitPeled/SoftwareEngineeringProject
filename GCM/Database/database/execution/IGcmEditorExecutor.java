package database.execution;

import java.io.File;
import java.sql.SQLException;

import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;

public interface IGcmEditorExecutor {
	void addExistingSiteToTour(int tourId, int siteId, int durnace) throws SQLException;

	int addNewTourToCity(int cityId, Tour tour) throws SQLException;

	void addExistingTourToMap(int mapId, int tourId) throws SQLException;

	int addCity(City city) throws SQLException;

	int addCityWithInitialMap(City city, Map map, File mapFile) throws SQLException;

	void updateCity(int cityId, City city) throws SQLException;

	void deleteCity(City city) throws SQLException;

	int addNewSiteToCity(int cityId, Site site) throws SQLException;

	void addExistingSiteToMap(int mapId, int siteId) throws SQLException;

	void deleteSiteFromMap(int mapId, int siteId) throws SQLException;

	void UpdateSite(int siteId, Site newSite) throws SQLException;

	void deleteSite(int siteId) throws SQLException;

	int addMapToCity(int cityId, Map mapDescription, File mapFile) throws SQLException;

	void deleteMap(int mapId) throws SQLException;

	void updateMap(int mapId, Map newMap) throws SQLException;
}
