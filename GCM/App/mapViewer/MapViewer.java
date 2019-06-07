package mapViewer;

import javafx.scene.Scene;

public interface MapViewer {

	double getImageWidth();

	double getImageHeight();

	Scene getScene();

	/**
	 * Adds a single MapViewerListener that will be invoked whenever
	 * the MapViewer registers a mouse click on the map
	 * @param listener The listener object that implements the MapViewerListener interface
	 */
	void addListener(MapViewerListener listener);

	/**
	 * Updates the status text on the top of the map. Can be used to write anything
	 * @param text The text that's going to be in the status text
	 */
	void updateStatusText(String text);
	
	/**
	 * Removes the status text
	 */
	void clearStatusText();

}