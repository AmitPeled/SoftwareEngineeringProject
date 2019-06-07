package mapViewer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class MapViewerTest {

	MapViewerComponent mapViewerComponent;
	MapViewerListener listener;
	private String testFile = "file:Import/resources/Gta3_map.gif";
	
	@BeforeEach
	void setUp() throws Exception {
		listener = Mockito.mock(MapViewerListener.class);
		mapViewerComponent = new MapViewerComponent(testFile);
	}
	
	@Test
	void controller_MapClickRegistered_CallsMapViewerMapClick(){
		fail();
	}

}
