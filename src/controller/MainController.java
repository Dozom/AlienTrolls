package controller;
	
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the Main Controller which initialize the application
 */
public class MainController extends Application {
	
	/**
	 * This method starts the Stage and application
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			new LoginController(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method which initialize the application
	 * @param args arguments for Main
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
