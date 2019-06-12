package database.objectParse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import maps.Tour;
import users.User;

/**
 * @author amit
 *
 */
@SuppressWarnings("serial")
public class DatabaseParser implements IParseObjects {

	@Override
	public Map getMap(List<Object> objectList, List<Site> mapSites, List<Tour> mapTours) {
		return new Map((int) objectList.get(0), (String) objectList.get(1), (String) objectList.get(2),
				(float) objectList.get(3), (float) objectList.get(4),
				new Coordinates((float) objectList.get(5), (float) objectList.get(6)), (double) objectList.get(7),
				mapSites, mapTours);
	}

	@Override
	public Site getSite(List<Object> objectList) {
		return new Site((int) objectList.get(0), (String) objectList.get(1), (String) objectList.get(2),
				(String) objectList.get(3), (boolean) objectList.get(4),
				new Coordinates((float) objectList.get(5), (float) objectList.get(6)));
	}

	@Override
	public User getUser(List<Object> objectList) {
		Date expireDate;
		try {
			expireDate = new SimpleDateFormat("dd/MM/yyyy").parse((String) objectList.get(7));
			Date currentDate = new Date();
			if (expireDate.after(currentDate))
				expireDate = null;
		} catch (Exception e) {
			expireDate = null;
		}
		System.out.println((String) objectList.get(1));
		System.out.println((String) objectList.get(2));
		return new User((String) objectList.get(0), (String) objectList.get(2), (String) objectList.get(3),
				(String) objectList.get(4), (String) objectList.get(5), (int) objectList.get(6), expireDate,
				(int) objectList.get(8), (int) objectList.get(9));
	}

//	@Override
//	public City getCity(List<Object> objectList , SortedSet<Integer> maps, SortedSet<Integer> sites ) {
//		return new City((int) objectList.get(0), new String((String) objectList.get(1)));
//	}

	@Override
	public List<Object> getMapMetaFieldsList(Map map) {
		return new ArrayList<Object>() {
			{
				add(map.getId());
				add(map.getName());
				add(map.getDescription());
				add(map.getWidth());
				add(map.getHeight());
				add(map.getOffset().x);
				add(map.getOffset().y);
				add(map.getPrice());
			}
		};
	}

	@Override
	public List<Object> getSiteFieldsList(Site site) {
		return new ArrayList<Object>() {
			{
				add(site.getId());
				add(site.getName());
				add(site.getDescription());
				add(site.getSiteType());
				add(site.isAccessibleForDisabled());
				add(site.getCoordinates().x);
				add(site.getCoordinates().y);
			}
		};
	}

	@Override
	public List<Object> getCityMetaFieldsList(City city) { // meta fields are all the fields that aren't arrays.
		return new ArrayList<Object>() {
			{
				add(city.getId());
				add(city.getName());
				add(city.getDescription());
			}
		};
	}

	@Override
	public List<Object> getUserFieldsList(User user) {
		return new ArrayList<Object>() {
			{
				add(user.getFirstName());
				add(user.getLastName());
				add(user.getEmail());
				add(user.getPhoneNumber());
				add(0);
				add(null);
				add(0);
				add(0);
			}
		};
	}

	@Override
	public Tour getTour(List<Object> objectList, List<Site> tourSites, List<Integer> sitesDurances) {
		return new Tour((int) objectList.get(0), (String) objectList.get(1), tourSites, sitesDurances);
	}

	@Override
	public List<Object> getTourMetaFieldsList(Tour tour) {
		return new ArrayList<Object>() {
			{
				add(tour.getId());
				add(tour.getDescription());
			}
		};
	}

	@Override
	public City getCityByMetaFields(List<Object> objectList) {
		return new City((int) objectList.get(0), (String) objectList.get(1), (String) objectList.get(2));
	}
}