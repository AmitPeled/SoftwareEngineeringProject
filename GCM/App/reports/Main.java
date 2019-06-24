//package reports;
//
//import java.io.IOException;
//
//import gcmDataAccess.GcmDAO;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
// 
//public class Main extends Application {
//	
//	@Override
//	public void start(Stage primaryStage) throws IOException {
//		 // constructing our scene
//		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reports/ReportsScene.fxml"));
//		 GcmDAO gcmDAO = new GcmDAO();
//		 fxmlLoader.setController(new ReportsController(gcmDAO));
//		 Parent root = fxmlLoader.load();
//		 Scene scene = new Scene( root );
//
//		 // setting the stage
//		 primaryStage.setScene( scene );
//		 primaryStage.setTitle( "Reports" );
//
//		 primaryStage.show();
//	}
//	
//	public static void main(String[] args) {
//		launch(args);
//	}
//}