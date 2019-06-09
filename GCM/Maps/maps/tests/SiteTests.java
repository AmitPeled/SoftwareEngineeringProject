package maps.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maps.Coordinates;
import maps.Site;

class SiteTests {
	static final int defaultId = 4;
	static final String defaultDescriptionString = "Default site description string";
	static final Coordinates defaultCoordinates = new Coordinates(10, 20);
	
	private Site site;
	@BeforeEach
	void setUp() throws Exception {
		site = new Site(defaultId, defaultCoordinates);
	}

	@Test
	void Constructor_NegativeId_Throws() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Site(-defaultId, defaultCoordinates);
		});
	}
	
	@Test
	void getId_ReturnsCorrectNumber() {
		int result = site.getId();
		assertEquals(defaultId, result);
	}
	
	@Test
	void descriptionSetterGetter_Works() {
		site.setDescription(defaultDescriptionString);
		assertEquals(defaultDescriptionString, site.getDescription());
	}
	
	@Test
	void location_Getter() {
		assertEquals(defaultCoordinates, site.getCoordinates());
	}
}
