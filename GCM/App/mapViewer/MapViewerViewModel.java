package mapViewer;

public final class MapViewerViewModel {
	
	private MapViewer mapViewer;
	
	public MapViewerViewModel(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}
	
	public String getMapPath() {
		return mapViewer.getImageFilePath();
	}
	
	// Example comment

}
