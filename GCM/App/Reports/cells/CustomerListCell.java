package Reports.cells;

import Reports.data.CustomerData;
import Reports.resultItems.CustomerItem;
import javafx.scene.control.ListCell;

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