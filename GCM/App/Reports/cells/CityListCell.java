package Reports.cells;

import Reports.data.CityData;
import Reports.resultItems.CityItem;
import javafx.scene.control.ListCell;

public class CityListCell extends ListCell<CityItem> {

    @Override
    protected void updateItem(CityItem item, boolean empty) {

        super.updateItem(item, empty);
        if (item != null && !empty) { 
            CityData data = new CityData();
            data.setInfo(item);
            setGraphic(data.getBox());
        } else {
            setGraphic(null);
        }
    }
}