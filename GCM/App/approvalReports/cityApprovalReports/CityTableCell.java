package approvalReports.cityApprovalReports;

import approvalReports.ActionTaken;
import gcmDataAccess.GcmDAO;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import maps.City;
import maps.Map;
import maps.Site;

public class CityTableCell extends TableCell<CitySubmission, Button> {
            Button approve = new Button("Approve!");
            Button disapprove = new Button("Disapprove!");
            GcmDAO gcmDAO = new GcmDAO();
            
            public CityTableCell(GcmDAO gcmDAO){
            	this.gcmDAO = gcmDAO;
            }
            
            public void takeAction(CitySubmission citySubmission, Boolean approve) {
            	String actionTaken = citySubmission.getActionTaken();
            	City city = citySubmission.getCity();
            	if(actionTaken.equals("ADD")) {
            		gcmDAO.actionCityAddEdit(city, approve);
            	}else if(actionTaken.equals("UPDATE")) {
            		gcmDAO.actionCityUpdateEdit(city, approve);
            	}else {
            		gcmDAO.actionCityDeleteEdit(city, approve);
            	}
            }
            
            @Override
            public void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                	CitySubmission citySubmission = getTableView().getItems().get(getIndex());
                	
                	approve.setOnAction(event -> {
                    	takeAction(citySubmission, true);
                    });
                	disapprove.setOnAction(event -> {
                    	takeAction(citySubmission, false);
                    });
                	HBox pane = new HBox(approve, disapprove);

                    setGraphic(pane);
                    setText(null);
                }
            };
        }
