package database.objectParse;

import java.util.List;

import maps.City;
import maps.Map;
import maps.Site;

/**
 * @author amit
 *
 */
public interface IParseObjects {

	/**
	 * this function rule is to parse a list of a map's fields values into a Map
	 * object.
	 * 
	 * 
	 * @param objectList a list of the map fields. each object in the list contains
	 *                   the correspond map field, sorted by the constructor
	 *                   signature field order.
	 * 
	 * @return a map that contain the fields
	 */
	Map getMap(List<Object> objectList);

	Site getSite(List<Object> objectList);

	City getCity(List<Object> objectList, List<Object> mapsIds, List<Object> sitesIds);

	/**
	 * this function rule is to parse a Map object into a List of its fields values.
	 * 
	 * 
	 * @param map the map to parse
	 * @return List of the values of the map fields, sorted by the order it is
	 *         stored in the database.
	 */
	List<Object> getMapFieldsList(Map map);

	List<Object> getSiteFieldsList(Site site);

	List<Object> getCityMetaFieldsList(City city);

}
