package Reports.cells;

import Reports.data.WorkerData;
import Reports.resultItems.WorkerItem;
import javafx.scene.control.ListCell;

public class WorkerListCell extends ListCell<WorkerItem> {

    @Override
    protected void updateItem(WorkerItem item, boolean empty) {

        super.updateItem(item, empty);
        if (item != null && !empty) { 
            WorkerData data = new WorkerData();
            data.setInfo(item);
            setGraphic(data.getBox());
        } else {
            setGraphic(null);
        }
    }
}