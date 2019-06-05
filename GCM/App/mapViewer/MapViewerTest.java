package mapViewer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mapViewer.MapViewer;

class MapViewerTest {

	MapViewer mapViewer;
	private String testFile = "file:Import/resources/Gta3_map.gif";
	
	@BeforeEach
	void setUp() throws Exception {
		mapViewer = new MapViewer(testFile);
	}

	@Test
	void testGetImageFilePath() {
		assertEquals(testFile, mapViewer.getImageFilePath());
	}

}
