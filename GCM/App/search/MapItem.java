package Search;

import javafx.fxml.FXML;

public class MapItem {
	@FXML // fx:id="mapName"
    private String name;
	@FXML // fx:id="description"
    private String description;
	@FXML // fx:id="pointOfInterest"
    private String pointOfInterest;
	@FXML // fx:id="tours"
    private String tours;
	
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getPointOfInterest() {
        return pointOfInterest;
    }
    public String getTours() {
        return tours;
    }
    public MapItem(String name, String description, String pointOfInterest, String tours) {
        super();
        this.name = name;
        this.description = description;
        this.pointOfInterest = pointOfInterest;
        this.tours = tours;
    }
}