package database.objectParse;

import java.util.List;

import maps.City;
import maps.Map;
import maps.Site;
import users.User;

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
	 * @param metaFieldsList, L a list of the map fields. each object in the list
	 *        contains the correspond map field, sorted by the constructor signature
	 *        field order.
	 * 
	 * @return a map that contain the fields
	 */
	Map getMap(List<Object> metaFieldsList, List<Site> sites);

	Site getSite(List<Object> fieldsList);

//	City getCity(List<Object> fieldsList, SortedSet<Integer> mapsIds, SortedSet<Integer> sitesIds);

	/**
	 * this function rule is to parse a Map object into a List of its fields values.
	 * 
	 * 
	 * @param map the map to parse
	 * @return List of the values of the map fields, sorted by the order it is
	 *         stored in the database.
	 */
	List<Object> getMapMetaFieldsList(Map map);

	List<Object> getSiteFieldsList(Site site);

	List<Object> getCityMetaFieldsList(City city);
	
	List<Object> getUserFieldsList(User user);

}
