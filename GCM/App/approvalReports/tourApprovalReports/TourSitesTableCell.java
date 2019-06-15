package approvalReports.tourApprovalReports;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.TableCell;
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



