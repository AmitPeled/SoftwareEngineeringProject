package approvalReports.sitesApprovalReports;

import gcmDataAccess.GcmDAO;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import maps.Site;

public class SiteTableCell extends TableCell<SiteSubmission, Button> {
            Button approve = new Button("Approve!");
            Button disapprove = new Button("Disapprove!");
            GcmDAO gcmDAO = new GcmDAO();
            
            public SiteTableCell(GcmDAO gcmDAO){
            	this.gcmDAO = gcmDAO;
            }
            
            public void takeAction(SiteSubmission siteSubmission, Boolean approve) {
            	String actionTaken = siteSubmission.getActionTaken();
            	Site map = siteSubmission.getSite();
            	if(actionTaken.equals("ADD")) {
            		gcmDAO.actionSiteAddEdit(map, approve);
            	}else if(actionTaken.equals("UPDATE")) {
            		gcmDAO.actionSiteUpdateEdit(map, approve);
            	}else {
            		gcmDAO.actionSiteDeleteEdit(map, approve);
            	}
            }
            
            @Override
            public void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                	SiteSubmission siteSubmission = getTableView().getItems().get(getIndex());
                	
                	approve.setOnAction(event -> {
                    	takeAction(siteSubmission, true);
                    });
                	disapprove.setOnAction(event -> {
                    	takeAction(siteSubmission, false);
                    });
                	
                	HBox pane = new HBox(approve, disapprove);

                    setGraphic(pane);
                    setText(null);
                }
            };
        }
