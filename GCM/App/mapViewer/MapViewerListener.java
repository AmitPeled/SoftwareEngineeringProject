package mapViewer;

/**
 * This interface is implemented by classes that listen to user interactions with the map viewer
 * @author aagami
 *
 */
public interface MapViewerListener {
	/**
	 * Called whenever a mouse click occurs on the map
	 * @param x the x coordinates in [0,1] range of where the click happened
	 * @param y the y coordinates in [0,1] range of where the click happened
	 */
	public void onMapClick(double x, double y);
}
