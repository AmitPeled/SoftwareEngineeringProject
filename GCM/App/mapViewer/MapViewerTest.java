package mapViewer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mapViewer.MapViewer;

class MapViewerTest {

	MapViewer mapViewer;
	
	@BeforeEach
	void setUp() throws Exception {
		mapViewer = new MapViewer();
	}

	@Test
	void testGetNumber() {
		assertEquals(3, mapViewer.getNumber());
	}

}
