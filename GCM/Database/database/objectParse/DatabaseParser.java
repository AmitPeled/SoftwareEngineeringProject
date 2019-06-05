package database.objectParse;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import users.User;

/**
 * @author amit
 *
 */
public class DatabaseParser implements IParseObjects {

	@Override
	public Map getMap(List<Object> objectList, SortedSet<Integer> siteIds) {
		return new Map((int) objectList.get(0), (float) objectList.get(1), (float) objectList.get(2),
				new Coordinates((float) objectList.get(3), (float) objectList.get(4)));
	}

	@Override
	public Site getSite(List<Object> objectList) {
		return new Site((int) objectList.get(0), new Coordinates((float) objectList.get(2), (float) objectList.get(3)));
	}

//	@Override
//	public City getCity(List<Object> objectList , SortedSet<Integer> maps, SortedSet<Integer> sites ) {
//		return new City((int) objectList.get(0), new String((String) objectList.get(1)));
//	}

	@SuppressWarnings("serial")
	@Override
	public List<Object> getMapFieldsList(Map map) {
		return new ArrayList<Object>() {
			{
				add(map.getId());
				add(map.getWidth());
				add(map.getHeight());
				add(map.getOffset().x);
				add(map.getOffset().y);
			}
		};
	}

	@SuppressWarnings("serial")
	@Override
	public List<Object> getSiteFieldsList(Site site) {
		return new ArrayList<Object>() {
			{
				add(site.getId());
				add(site.getDescription());
				add(site.getCoordinates().x);
				add(site.getCoordinates().y);
			}
		};
	}

	@SuppressWarnings("serial")
	@Override
	public List<Object> getCityMetaFieldsList(City city) { // meta fields are all the fields that aren't arrays.
		return new ArrayList<Object>() {
			{
				add(city.getId());
				add(city.getName());
			}
		};
	}

	@SuppressWarnings("serial")
	@Override
	public List<Object> getUserFieldsList(User user) {
		return new ArrayList<Object>() {
			{
				add(user.getEmail());
				add(user.getPhoneNumber());
			}
		};
	}
}