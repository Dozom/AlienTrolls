package controller;
	
import css.CssPath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ViewPath;

/**
 * This is the Main Controller which inits the application
 */
public class MainController extends Application {
	
	/**
	 * This method starts the Stage and application
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// root pane
			Parent root = FXMLLoader.load(ViewPath.class.getResource("MainMenuView.fxml"));
			// Scene with 640 width and 480 height
			Scene scene = new Scene(root,640,480);
			// Add css
			scene.getStylesheets().add(CssPath.class.getResource("application.css").toExternalForm());
			// Set Scene to the Stage
			primaryStage.setScene(scene);
			// Show Stage
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method which inits the application
	 * @param args arguments for Main
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
