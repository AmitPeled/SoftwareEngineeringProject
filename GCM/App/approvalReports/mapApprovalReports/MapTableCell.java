package approvalReports.mapApprovalReports;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import approvalReports.ApprovalReportsController;
import gcmDataAccess.GcmDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import users.User;

public class MapTableCell extends TableCell<MapSubmission, Button> implements Initializable{
            Button approve = new Button("Approve!");
            Button disapprove = new Button("Disapprove!");
            GcmDAO gcmDAO = new GcmDAO();
            TableView<User> notifyUsersList;
            public MapTableCell(GcmDAO gcmDAO, TableView<User> notifyUsersList){
            	this.gcmDAO = gcmDAO;
            	this.notifyUsersList = notifyUsersList;
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
                		List<User> users = takeAction(mapSubmission, true);
                		System.out.println(users);
                		if(!mapSubmission.getActionTaken().equals("ADD")) {
                			notifyUsersList.setVisible(true);
                    		ObservableList<User> details = FXCollections.observableArrayList(users);
                    		notifyUsersList.setItems(details);
                		}
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
            }

			@Override
			public void initialize(URL location, ResourceBundle resources) {
				// TODO Auto-generated method stub
				
			}


        }
