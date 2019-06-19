package search;

import javafx.scene.control.ListCell;
import queries.RequestState;

public class CustomListCell extends ListCell<MapItem> {
	RequestState userState;
	public CustomListCell(RequestState userState) {
		this.userState = userState;
	}
    @Override
    protected void updateItem(MapItem item, boolean empty) {

        super.updateItem(item, empty);
        if (item != null && !empty) { 
            Data data = new Data(item.getListViewController(), userState);
            data.setInfo(item);
            setGraphic(data.getBox());
        } else {
            setGraphic(null);
        }
    } 
}