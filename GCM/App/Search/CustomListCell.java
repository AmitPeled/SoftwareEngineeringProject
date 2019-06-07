package Search;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;

public class CustomListCell extends ListCell<MapItem> {
	@FXML // fx:id="mapName"
    private Text name;
	@FXML // fx:id="description"
    private Text description;
	@FXML // fx:id="pointOfInterest"
    private Text pointOfInterest;
	@FXML // fx:id="tours"
    private Text tours;

    public CustomListCell() {
        super();
        name = new Text();
        description = new Text();
        pointOfInterest = new Text();
        tours = new Text();
    }

    @Override
    protected void updateItem(MapItem item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { // <== test for null item and empty parameter
            name.setText(item.getName());
            description.setText(item.getDescription());
            pointOfInterest.setText(item.getPointOfInterest());
            tours.setText(item.getTours());
        } else {
            setGraphic(null);
        }
    }
}