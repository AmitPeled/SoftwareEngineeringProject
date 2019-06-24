package dataAccess.search;

public interface SearchDAO {

	 CityMaps getMapsByCityName(String cityName);

	 CityMaps getMapsBySiteName(String siteName);

	 /**
	  * by description of a city or site (may be partial)
	  */
	 CityMaps getMapsByDescription(String description);

	 CityMaps getMapsBySiteAndCityNames(String cityName, String siteName);

}
