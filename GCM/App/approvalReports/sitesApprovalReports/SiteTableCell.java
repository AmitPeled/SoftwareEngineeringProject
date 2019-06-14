package approvalReports.sitesApprovalReports;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class SiteTableCell extends TableCell<SiteSubmission, Button> {
            Button approve = new Button("Approve!");
            Button disapprove = new Button("Disapprove!");
            
            @Override
            public void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                	approve.setOnAction(event -> {
                		// actionCityAddEdit(city, true)
                		SiteSubmission citySubmission = getTableView().getItems().get(getIndex());
                    	System.out.println(citySubmission.getSite().getId());
                    });
                	disapprove.setOnAction(event -> {
                		// actionCityAddEdit(city, false)
                		SiteSubmission city = getTableView().getItems().get(getIndex());
                    	System.out.println(city.getSite().getId());
                    });
                	HBox pane = new HBox(approve, disapprove);

                    setGraphic(pane);
                    setText(null);
                }
            };
        }
