package reports.cells;

import javafx.scene.control.ListCell;
import reports.data.CustomerData;
import reports.resultItems.CustomerItem;

public class CustomerListCell extends ListCell<CustomerItem> {

    @Override
    protected void updateItem(CustomerItem item, boolean empty) {

        super.updateItem(item, empty);
        if (item != null && !empty) { 
            CustomerData data = new CustomerData();
            data.setInfo(item);
            setGraphic(data.getBox());
        } else {
            setGraphic(null);
        }
    }
}