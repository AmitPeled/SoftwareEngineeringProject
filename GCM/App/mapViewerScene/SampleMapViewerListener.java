package mapViewerScene;

import mapViewer.MapViewer;
import mapViewer.MapViewerListener;

/**
 * This class is an example class that demonstrates how to implement the MapViewerListener interface
 * @author aagami
 *
 */
final class SampleMapViewerListener implements MapViewerListener {

	/**
	 * We're holding references to the MapViewer interface, not the MapViewerComponent type
	 */
	private MapViewer mapViewer;
	
	public SampleMapViewerListener(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}
	
	/**
	 * The only function a MapViewerListener implements. In this example we're
	 * writing the mouse click data to the console and updating the status text
	 */
	@Override
	public void onMapClick(double x, double y) {
		System.out.printf("Mouse click on [%.3f,%.3f] registered\n",x,y);
		//mapViewer.updateStatusText("Mouse click");
	}

}
