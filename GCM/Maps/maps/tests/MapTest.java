package maps.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maps.Map;

class MapTest {
	static final int defaultId = 1;
	private Map map;
	
	@BeforeEach
	void setUp() throws Exception {
		map = new Map(1);
	}

	@Test
	void constructor_idLessThanOne_ThrowsException() {assertThrows(IllegalArgumentException.class, () -> {
		new Map(-1);
	});}

}
