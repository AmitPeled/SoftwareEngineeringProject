package approvalReports.cityApprovalReports;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class CityTableCell extends TableCell<CitySubmission, Button> {
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
                		CitySubmission citySubmission = getTableView().getItems().get(getIndex());
                    	System.out.println(citySubmission.getCity().getId());
                    });
                	disapprove.setOnAction(event -> {
                		// actionCityAddEdit(city, false)
                		CitySubmission city = getTableView().getItems().get(getIndex());
                    	System.out.println(city.getCity().getId());
                    });
                	HBox pane = new HBox(approve, disapprove);

                    setGraphic(pane);
                    setText(null);
                }
            };
        }
