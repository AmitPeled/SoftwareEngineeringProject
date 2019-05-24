package maps.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maps.Coordinates;
import maps.Map;

class MapTest {
	
	static final int DEFAULT_ID = 1;
	static final float DEFAULT_WIDTH = 200;
	static final float DEFAULT_HEIGHT = 120;
	static final float NON_DEFAULT_COORDINATES_X = 2.5f;
	static final float NON_DEFAULT_COORDINATES_Y = 11.3f;
	
	private Map map;
	
	@BeforeEach
	void setUp() throws Exception {
		map = new Map(DEFAULT_ID,DEFAULT_WIDTH,DEFAULT_HEIGHT);
	}

	@Test
	void constructor_idLessThanOne_ThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Map(-DEFAULT_ID,DEFAULT_WIDTH,DEFAULT_HEIGHT);
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
	
	@Test
	void constructor_defaultOffsetIsZero() {
		assertTrue(map.getOffset().equals(new Coordinates(0f,0f)));
	}
	
	@Test
	void constructor_offsetIsSet() {
		Coordinates offset = new Coordinates(NON_DEFAULT_COORDINATES_X,NON_DEFAULT_COORDINATES_Y);
		Map anotherMap = new Map(DEFAULT_ID, DEFAULT_WIDTH, DEFAULT_HEIGHT,offset);
		assertEquals(offset, anotherMap.getOffset());
		assertTrue(offset.equals(anotherMap.getOffset()));
	}
}
