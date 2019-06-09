package Search;

import javafx.scene.control.ListCell;

public class CustomListCell extends ListCell<MapItem> {

    @Override
    protected void updateItem(MapItem item, boolean empty) {

        super.updateItem(item, empty);
        if (item != null && !empty) { 
            Data data = new Data();
            data.setInfo(item);
            setGraphic(data.getBox());
        } else {
            setGraphic(null);
        }
    }
}