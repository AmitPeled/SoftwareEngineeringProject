package approvalReports.mapApprovalReports;

import java.util.List;

import gcmDataAccess.GcmDAO;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import users.User;

public class MapTableCell extends TableCell<MapSubmission, Button> {
            Button approve = new Button("Approve!");
            Button disapprove = new Button("Disapprove!");
            GcmDAO gcmDAO = new GcmDAO();
            
            public MapTableCell(GcmDAO gcmDAO){
            	this.gcmDAO = gcmDAO;
            }
            
            /**
             * @param mapSubmission
             * @param approve
             * @return list of users that are affected by the action taken.
             * 				if approved actionTaken of UPDATE/DELETE, returns list of users that holds the map
             * 				else, returns an empty list (as no users holdings are affected by ADD or edit disapprovement)
             */
            public List<User> takeAction(MapSubmission mapSubmission, Boolean approve) {
            	return gcmDAO.actionMapEdit(mapSubmission, approve);
            }
            
            public void disableBtn() {
            	approve.setVisible(false);
            	disapprove.setVisible(false);
            }
            
            @Override
            public void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                	MapSubmission mapSubmission = getTableView().getItems().get(getIndex());
                	
                	approve.setOnAction(event -> {
                    	takeAction(mapSubmission, true);
                    	disableBtn();
                    });
                	disapprove.setOnAction(event -> {
                    	takeAction(mapSubmission, false);
                    	disableBtn();
                    });
                	
                	HBox pane = new HBox(approve, disapprove);

                    setGraphic(pane);
                    setText(null);
                }
            };
        }
