package database.objectParse;

import java.util.List;
import java.sql.ResultSet;

import maps.City;
import maps.Map;
import maps.Site;

/**
 * @author amit This class rule is to parse between the way the database stores
 *         its data to the actual data objects, and vice versa - parse bewteen
 *         our objects to the way they should be stored in the database.
 *
 *         first and last 3 functions is of the same rule (the first of them is
 *         documented, and the rest are of the same purpose for a different object type)
 * 
 *
 */
public interface IParseObjects {

	/**
	 * this function rule is to parse Database map data back into a Map. map data is
	 * stored in the database by the following row (each '|' represents column
	 * separator): id_number (int) | width (float) | height (float) |
	 * x_offset_coordinate(float) | y_offset_coordinate (float)
	 * 
	 * 
	 * @param resultSet the maps data from the database. ResultSet is simply a list
	 *                  of the database corresponding rows (in our case, each row
	 *                  contains the map data as mentioned above)
	 * 
	 * @return List of the corresponding maps whose data is contained in resultSet
	 */
	List<Map> ResultsetToMaps(ResultSet resultSet);

	/**
	 * same as for maps, the database row structure for Site is: 
	 * id(int) | description(String) | x_coordinate(float) | y_coordinate(float)
	 * 
	 * @param resultSet
	 * @return Site List
	 */
	List<Site> ResultsetToSites(ResultSet resultSet);

	/**
	 * same as for maps, the database row structure for Site is: 
	 * id(int) | name(String) 
	 * 
	 * @param resultSet
	 * @return City List
	 */
	List<City> ResultsetToCities(ResultSet resultSet);

	
	
	/**
	 * this function rule is to parse the Map object into a List of Objects. the
	 * list should contain the objects in the *same order* it is stored in the
	 * database. i.e: id_number, width, height, x_offset_coordinate,
	 * y_offset_coordinate
	 * 
	 * 
	 * @param map the map to parse
	 * @return List of the values of the map fields, sorted by the order it is
	 *         stored in the database.
	 */
	List<Object> MapToObjectList(Map map);

	List<Object> SiteToObjectList(Site site);

	List<Object> CityToResultset(City city);

}
