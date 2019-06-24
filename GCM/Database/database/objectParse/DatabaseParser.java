package database.objectParse;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

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
public class DatabaseParser implements IParseObjects, Serializable {

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
		        (String) objectList.get(4), (String) objectList.get(5));
	}

//	@Override
//	public City getCity(List<Object> objectList , SortedSet<Integer> maps, SortedSet<Integer> sites ) {
//		return new City((int) objectList.get(0), new String((String) objectList.get(1)));
//	}

	@Override
	public List<Object> getMapMetaFieldsList(Map map) {
		if (map == null)
			return new ArrayList<>();
		return new ArrayList<Object>() {
			{
				add(map.getId());
				add(map.getName());
				add(map.getDescription());
				add(map.getWidth());
				add(map.getHeight());
				add(map.getOffset().x);
				add(map.getOffset().y);
				add(-1);
			}
		};
	}

	@Override
	public List<String> getMapMetaFieldsNames() {
		return new ArrayList<String>() {
			{
				add("mapId");
				add("mapName");
				add("mapDescription");
				add("mapWidth");
				add("mapHeight");
				add("map_x_offset");
				add("map_y_offset");
				add("mapPrice");
			}
		};
	}

	@Override
	public List<Object> getSiteFieldsList(Site site) {
		if (site == null)
			return new ArrayList<>();
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
	public List<Object> getCityFieldsWithDefualtPrice(City city) { // default prices values
		if (city == null)
			return new ArrayList<>();
		return new ArrayList<Object>() {
			{
				add(city.getId());
				add(city.getName());
				add(city.getDescription());
				addAll(defaultPrice());
			}

			/**
			 * @return default city price
			 */
			private List<Object> defaultPrice() { // suppose to handle in a separate class PriceManager.getDefaultPrice
				return new ArrayList<Object>() {
					{
						add(7);
						add(10);
						add(15);
						add(19.5);
						add(23.5);
						add(27);
						add(30);
					}
				};
			}
		};
	}

	@Override
	public List<Object> getCityFields(City city) {
		if (city == null)
			return new ArrayList<>();
		List<Double> prices = city.getPrices();
		return new ArrayList<Object>() {
			{
				add(city.getId());
				add(city.getName());
				add(city.getDescription());
				add(prices.get(0));
				add(prices.get(1));
				add(prices.get(2));
				add(prices.get(3));
				add(prices.get(4));
				add(prices.get(5));
				add(prices.get(6));
			}
		};
	}

	@Override
	public List<Object> getTourMetaFieldsList(Tour tour) {
		if (tour == null)
			return new ArrayList<>();
		return new ArrayList<Object>() {
			{
				add(tour.getId());
				add(tour.getDescription());

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
			}
		};
	}

	@Override
	public Tour getTour(List<Object> objectList, List<Site> tourSites, List<Integer> sitesDurances) {
		return new Tour((int) objectList.get(0), (String) objectList.get(1), tourSites, sitesDurances);
	}

	@Override
	public City getCity(List<Object> objectList, SortedSet<Integer> mapsId, SortedSet<Integer> toursId,
	        SortedSet<Integer> sitesId) {
		return new City((int) objectList.get(0), (String) objectList.get(1), (String) objectList.get(2),
		        new ArrayList<Double>() {
			        {
				        add((Double) objectList.get(3));
				        add((Double) objectList.get(4));
				        add((Double) objectList.get(5));
				        add((Double) objectList.get(6));
				        add((Double) objectList.get(7));
				        add((Double) objectList.get(8));
				        add((Double) objectList.get(9));
			        }
		        }, mapsId, toursId, sitesId);
	}

	@Override
	public List<String> getCityMetaFieldsNames() {
		return new ArrayList<String>() {
			{
				add("cityId");
				add("cityName");
				add("cityDescription");
			}
		};

	}

	@Override
	public List<String> getSiteFieldsNames() {
		return new ArrayList<String>() {
			{
				add("siteId");
				add("siteName");
				add("siteDescription");
				add("siteType");
				add("siteAccessiblity");
				add("site_x_coordinate");
				add("site_y_coordinate");
			}
		};
	}

	@Override
	public List<String> getTourMetaFieldsNames() {
		return new ArrayList<String>() {
			{
				add("tourId");
				add("tourDescription");
			}
		};
	}
}