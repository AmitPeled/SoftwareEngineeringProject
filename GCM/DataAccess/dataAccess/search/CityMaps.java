package dataAccess.search;

import java.io.Serializable;
import java.util.List;

import maps.Map;

public class CityMaps implements Serializable {

	 private static final long serialVersionUID = 1L;

	 private int          cityId;
	 private String       cityName;
	 private String       cityDescription;
	 /**
	  * List of the city's prices by months. value at index contains city's price for
	  * this amount of months. at index 0 (0 months) the price for one time purchase.
	  */
	 private List<Double> cityPricesList;
	 private List<Map>    cityMaps;

	 public CityMaps(int id, String name, String description, List<Double> cityPrices, List<Map> maps) {
		  if (id <= 0)
			   throw new IllegalArgumentException("id has to be a positive number");
		  if (cityPrices == null || cityPrices.size() != 7)
			   throw new IllegalArgumentException("city must contain prices for all 7 months");
		  this.cityId = id;
		  this.cityName = name;
		  this.cityDescription = description;
		  this.cityPricesList = cityPrices;
		  this.cityMaps = maps;

	 }

	 public int getId() {
		  return this.cityId;
	 }

	 public String getName() {
		  return this.cityName;
	 }

	 public String getDescription() {
		  return this.cityDescription;
	 }

	 public List<Map> getMapsId() {
		  return this.cityMaps;
	 }

	 public List<Double> getPrices() {
		  return cityPricesList;
	 }
}
