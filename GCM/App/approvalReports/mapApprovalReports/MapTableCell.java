package approvalReports.mapApprovalReports;

import approvalReports.cityApprovalReports.CitySubmission;
import gcmDataAccess.GcmDAO;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import maps.City;
import maps.Map;

public class MapTableCell extends TableCell<MapSubmission, Button> {
            Button approve = new Button("Approve!");
            Button disapprove = new Button("Disapprove!");
            GcmDAO gcmDAO = new GcmDAO();
            
            public MapTableCell(GcmDAO gcmDAO){
            	this.gcmDAO = gcmDAO;
            }
            
            public void takeAction(MapSubmission mapSubmission, Boolean approve) {
            	String actionTaken = mapSubmission.getActionTaken();
            	Map map = mapSubmission.getMap();
            	if(actionTaken.equals("ADD")) {
            		gcmDAO.actionMapAddEdit(map, approve);
            	}else if(actionTaken.equals("UPDATE")) {
            		gcmDAO.actionMapUpdateEdit(map, approve);
            	}else {
            		gcmDAO.actionMapDeleteEdit(map, approve);
            	}
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
                    });
                	disapprove.setOnAction(event -> {
                    	takeAction(mapSubmission, false);
                    });
                	
                	HBox pane = new HBox(approve, disapprove);

                    setGraphic(pane);
                    setText(null);
                }
            };
        }
