package controller;
	
import css.CssPath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ViewPath;

/**
 * This is the Main Controller
 *
 */
public class MainController extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(ViewPath.class.getResource("LoginView.fxml"));
			Scene scene = new Scene(root,640,480);
			scene.getStylesheets().add(CssPath.class.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
