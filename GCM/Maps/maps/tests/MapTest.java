package maps.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maps.Map;

class MapTest {
	
	static final int DEFAULT_ID = 1;
	static final float DEFAULT_WIDTH = 200;
	static final float DEFAULT_HEIGHT = 120;
	
	private Map map;
	
	@BeforeEach
	void setUp() throws Exception {
		map = new Map(1,DEFAULT_WIDTH,DEFAULT_HEIGHT);
	}

	@Test
	void constructor_idLessThanOne_ThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Map(-1,DEFAULT_WIDTH,DEFAULT_HEIGHT);
		});
	}

	@Test
	void constructor_widthIsNotPositive_Throws() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Map(DEFAULT_ID, -DEFAULT_WIDTH, DEFAULT_HEIGHT);
		});
	}

	@Test
	void constructor_heightIsNotPositive_Throws() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Map(DEFAULT_ID, DEFAULT_WIDTH, -DEFAULT_HEIGHT);
		});
	}
	
	@Test
	void constructor_idIsSet() {
		int result = map.getId();
		assertEquals(DEFAULT_ID, result);
	}
	
	@Test
	void constructor_widthAndHeightAreSet(){
		assertEquals(DEFAULT_WIDTH, map.getWidth());
		assertEquals(DEFAULT_HEIGHT, map.getHeight());
	}
}
