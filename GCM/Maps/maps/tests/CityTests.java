package maps.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maps.City;

class CityTests {
	
	static final int defaultId = 4;
	static final String defaultName = "DefaultCity";
	static final String defaultDescription = "DefaultCityDescription";

	City city;
	
	@BeforeEach
	void setUp() throws Exception {
		city = new City(defaultId,defaultName,defaultDescription);		
	}

	@Test
	void Constructor_NegativeId_Throws() {
		assertThrows(IllegalArgumentException.class, () -> {
			new City(-defaultId,defaultName,defaultDescription);
		});
	}
	
	@Test
	void getId_ReturnsCorrectNumber() {
		int result = city.getId();
		assertEquals(defaultId, result);
	}

}
