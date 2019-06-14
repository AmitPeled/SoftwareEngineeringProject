//package approvalReports;
//
//import java.io.IOException;
//
//import gcmDataAccess.GcmDAO;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.stage.Stage;
//import utility.TextFieldUtility;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//
//public class Main extends Application {
//	
//	@Override
//	public void start(Stage primaryStage) throws IOException {
//		 // constructing our scene
//		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editor/addCity.fxml"));
//		 GcmDAO gcmDAO = new GcmDAO();
//		 gcmDAO.login("editor", "editor"); 
//		 TextFieldUtility utilities = new TextFieldUtility();
//		 fxmlLoader.setController(new ApprovalReportsController());
//		 Parent root = fxmlLoader.load();
//		 Scene scene = new Scene( root ); 
// 
//		 // setting the stage
//		 primaryStage.setScene( scene );
//		 primaryStage.setTitle( "Search" );
//
//		 primaryStage.show();
//	}
//	
//	public static void main(String[] args) {
//		launch(args);
//	}
//}