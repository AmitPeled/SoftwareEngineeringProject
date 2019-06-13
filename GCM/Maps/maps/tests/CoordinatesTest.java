package maps.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maps.Coordinates;

class CoordinatesTest {

	private Coordinates defaultCoordinates;
	static final float DEFAULT_X = 3.0f;
	static final float DEFAULT_Y = 4.0f;
	
	@BeforeEach
	void setUp() throws Exception {
		defaultCoordinates = new Coordinates(DEFAULT_X,DEFAULT_Y);
	}

	@Test
	void constructor_CreatesCorrectCoordinates() {
		assertEquals(DEFAULT_X, defaultCoordinates.x);
		assertEquals(DEFAULT_Y, defaultCoordinates.y);
	}
	
	@Test
	void equals() {
		Coordinates otherCoordinates = new Coordinates(DEFAULT_X , DEFAULT_Y);
		assertTrue(defaultCoordinates.equals(otherCoordinates));
	}
	
	@Test
	void copyConstructor() {
		Coordinates copiedCoordinates = new Coordinates(defaultCoordinates);
		assertFalse(defaultCoordinates == copiedCoordinates);
		assertTrue(defaultCoordinates.x == copiedCoordinates.x && defaultCoordinates.y == copiedCoordinates.y);
	}
}
