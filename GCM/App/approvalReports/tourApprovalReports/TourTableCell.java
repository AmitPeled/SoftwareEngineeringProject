package approvalReports.tourApprovalReports;

import gcmDataAccess.GcmDAO;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import maps.Tour;

public class TourTableCell extends TableCell<TourSubmission, Button> {
            Button approve = new Button("Approve!");
            Button disapprove = new Button("Disapprove!");
            GcmDAO gcmDAO = new GcmDAO();
            
            public TourTableCell(GcmDAO gcmDAO){
            	this.gcmDAO = gcmDAO;
            }
            
            public void takeAction(TourSubmission tourSubmission, Boolean approve) {
            	gcmDAO.actionTourEdit(tourSubmission, approve);
//            	String actionTaken = tourSubmission.getActionTaken();
//            	Tour tour = tourSubmission.getTour();
//            	if(actionTaken.equals("ADD")) {
//            		//gcmDAO.actionToursAddEdit(tour, approve);
//            	}else if(actionTaken.equals("UPDATE")) {
//            		//gcmDAO.actionToursUpdateEdit(tour, approve);
//            	}else {
//            		//gcmDAO.actionToursDeleteEdit(tour, approve);
//            	}
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
            		TourSubmission siteSubmission = getTableView().getItems().get(getIndex());

                	approve.setOnAction(event -> {
                		takeAction(siteSubmission, true);
                		disableBtn();
                    });
                	disapprove.setOnAction(event -> {
                		takeAction(siteSubmission, false);
                		disableBtn();
                    });
                	HBox pane = new HBox(approve, disapprove);

                    setGraphic(pane);
                    setText(null);
                }
            };
        }



