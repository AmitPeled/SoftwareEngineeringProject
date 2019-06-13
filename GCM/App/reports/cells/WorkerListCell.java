package reports.cells;

import javafx.scene.control.ListCell;
import reports.data.WorkerData;
import reports.resultItems.WorkerItem;

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