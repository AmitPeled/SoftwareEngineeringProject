package database.ObjectParse;

import java.sql.ResultSet;
import java.util.List;

import maps.City;
import maps.Map;
import maps.Site;

/**
 * class to be implemented by Tom.
 * 
 * take a look in the interface documentation. if you need something else - contact me
 *
 */
public class DatabaseParser implements IParseObjects {

	@Override
	public List<Map> ResultsetToMaps(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> ResultsetToSites(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> ResultsetToCities(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> MapToObjectList(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> SiteToObjectList(Site site) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> CityToResultset(City city) {
		// TODO Auto-generated method stub
		return null;
	}

}
