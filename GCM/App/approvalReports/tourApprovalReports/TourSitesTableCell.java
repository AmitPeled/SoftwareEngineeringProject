package approvalReports.tourApprovalReports;

import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import maps.Site;

public class TourSitesTableCell extends TableCell<Site, List<Site>> {

            @Override
            public void updateItem(List<Site> site, boolean empty) {
            	super.updateItem(site, empty);
            	if (empty) {
                    setText(null);
                } else {
                    setText(site.stream().map(Site::getName)
                        .collect(Collectors.joining(", ")));
                }
            };
        }



