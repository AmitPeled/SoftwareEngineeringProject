package maps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Contains references (by ID) to the maps, sites and tours that are associated
 * to the City
 *
 */
public final class City implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String descriptionString;
	/**
	 * List of the city's prices by months. value at index contains city's price for
	 * this amount of months. at index 0 (0 months) the price for one time purchase.
	 */
	private List<Double> cityPrices;
	private SortedSet<Integer> maps;
	private SortedSet<Integer> sites;
	private SortedSet<Integer> tours;

	public City(String name, String description) throws IllegalArgumentException {
		this.id = -1;
		this.name = name;
		this.descriptionString = description;
		this.cityPrices = new ArrayList<>(7);
	}

	/**
	 * default empty maps, sites and tours
	 */
	public City(int id, String name, String description) throws IllegalArgumentException {
		this(id, name, description, Arrays.asList(new Double[7]), new TreeSet<>(), new TreeSet<>(), new TreeSet<>());
	}

	public City(int id, String name, String description, List<Double> cityPrices, SortedSet<Integer> maps,
			SortedSet<Integer> tours, SortedSet<Integer> sites) {
		if (id <= 0)
			throw new IllegalArgumentException("id has to be a positive number");
		if (cityPrices == null || cityPrices.size() != 7)
			throw new IllegalArgumentException("city must contain prices for all 7 months");
		this.id = id;
		this.name = name;
		this.descriptionString = description;
		this.cityPrices = cityPrices;
		this.maps = maps;
		this.sites = sites;
		this.tours = tours;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.descriptionString;
	}

	public SortedSet<Integer> getMapsId() {
		return this.maps;
	}

	public SortedSet<Integer> getSitesId() {
		return this.sites;
	}

	public SortedSet<Integer> getToursId() {
		return this.tours;
	}

	public List<Double> getPrices() {
		return cityPrices;
	}

	public void setPrices(List<Double> prices) {
		if (prices == null || prices.size() != 7)
			throw new IllegalArgumentException("city must contain prices for all 7 months");
		cityPrices = prices;
	}
}
