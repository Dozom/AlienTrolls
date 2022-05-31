package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import view.ViewPath;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import css.CssPath;

/**
 * This Class, manage the Login Page
 *
 */
public class LoginController {
	private Stage prevStage;
	private Parent parent;

	// Login variables
	private int type;
	private int userId;
	private String username;

	// FXML Elements
	@FXML
	private Button loginButton = new Button();
	@FXML
	public PasswordField passwordText = new PasswordField();
	@FXML
	public TextField usernameText = new TextField();

	/**
	 * Empty Constructor
	 */
	public LoginController() {

	}

	/**
	 * This Controller initialize the Login View
	 * @param stage
	 */
	public LoginController(Stage stage) {
		// Obtain the previous Stage, to Change it
		this.prevStage = stage;

		try {
			loadLoginView();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function, loads the Login View
	 * @throws IOException Input Output Exception
	 */
	private void loadLoginView() throws IOException {
		parent = FXMLLoader.load(ViewPath.class.getResource("LoginView.fxml"));
		Scene scene = new Scene(parent, 640, 480);
		scene.getStylesheets().add(CssPath.class.getResource("application.css").toExternalForm());

		// Change Scene
		prevStage.setScene(scene);
		prevStage.show();
	}

	// Events
	/**
	 * Function executed when loginButton is Pressed, it loads Main Menu Scene or
	 * Error View depending on Login
	 * @param event Catch the User Login button
	 */
	@FXML
	void userLogin(ActionEvent event) {
		if (checkLogin()) {
			// Save previous Stage
			prevStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Load Main Menu Scene
			loadMainMenuScene();
		} else {

			// Login Failed
			loadLoginErrorView();
		}
	}

	/**
	 * This method loads the Main Menu View
	 */
	public void loadMainMenuScene() {
		try {
			new MainMenuController(prevStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load Login Error View
	 */
	public void loadLoginErrorView() {
		try {
			Stage loginError = new Stage();
			Parent root = FXMLLoader.load(ViewPath.class.getResource("LoginErrorView.fxml"));
			Scene scene = new Scene(root, 100, 100);
			// Add css
			scene.getStylesheets().add(CssPath.class.getResource("application.css").toExternalForm());
			// Set Scene to the Stage
			loginError.setScene(scene);
			// Show Stage
			loginError.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns if is a valid login or not and writes the User Properties
	 * @return Returns true or false
	 */
	private boolean checkLogin() {
		try {
			ResultSet userData = checkUserPasswordInDb();

			if (userData.next()) {
				// Set properties to Play
				type = Integer.parseInt(userData.getString(1));
				username = String.valueOf(userData.getString(2));
				userId = Integer.parseInt(userData.getString(3));

				// Write user Properties in a text file
				writeUserProperties();
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Login is not valid");
			return false;
		}
	}

	/**
	 * Check and Return User Data from database
	 * @return Return a ResultSet with Logged user Data or Empty
	 * @throws SQLException SQL Exception
	 */
	private ResultSet checkUserPasswordInDb() throws SQLException {
		// Create the connection
		ConnectDBController c = new ConnectDBController();
		// Prepare the statement to check if login is valid
		PreparedStatement s = c.getConnection()
				.prepareStatement("SELECT type, username, ID FROM User where username like ? and password like ?");
		s.setString(1, usernameText.getText());
		s.setString(2, passwordText.getText());

		// Execute the prepared statement and save it into ResultSet
		ResultSet r = s.executeQuery();
		return r;
	}

	/**
	 * This method write the logged User Properties in a file
	 */
	private void writeUserProperties() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("loggedUser.txt", "UTF-8");
		// Write player variables in file
		writer.println("" + userId + ";" + type + ";" + username + "");
		writer.close();
	}
}