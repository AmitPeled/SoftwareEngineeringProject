package search;

import javafx.fxml.FXML;

public class MapItem {
	@FXML // fx:id="id"
    private String id;
	@FXML // fx:id="mapName"
    private String name;
	@FXML // fx:id="description"
    private String description;
	@FXML // fx:id="pointOfInterest"
    private String pointOfInterest;
	@FXML // fx:id="tours"
    private String tours;
	@FXML // fx:id="price"  
    private double price;
	 
	public String getId() {
        return id;  
    }
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
    public double getPrice() {
        return price;
    }
    public MapItem(String id, String name, String description, String pointOfInterest, String tours, double price) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.pointOfInterest = pointOfInterest;
        this.tours = tours;
        this.price = price;
    }
}