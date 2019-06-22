package search;

import javafx.scene.control.ListCell;
import queries.RequestState;

public class CustomListCell extends ListCell<MapItem> {
	RequestState userState;
	Boolean permissionsForMap;
	
	public CustomListCell(RequestState userState, Boolean permissionsForMap) {
		this.userState = userState;
		this.permissionsForMap = permissionsForMap;
	}
    @Override
    protected void updateItem(MapItem item, boolean empty) {

        super.updateItem(item, empty);
        if (item != null && !empty) { 
            Data data = new Data(item.getListViewController(), userState, permissionsForMap);
            data.setInfo(item);
            setGraphic(data.getBox());
        } else {
            setGraphic(null);
        }
    } 
}