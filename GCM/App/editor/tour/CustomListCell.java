package editor.tour;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import maps.Site;

public class CustomListCell extends ListCell<Site> {
	public ListCell<Site> call(ListView<Site> l) {
        return new ListCell<Site>() {
	protected void updateItem(Site item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
        } else {
            setText(item.getName());
        }
    }
        };   
	} 
}
