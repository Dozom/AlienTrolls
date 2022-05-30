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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;

import css.CssPath;

/**
 * This Class, manage the Login Page
 *
 */
public class LoginController {

	// Login Button, button to Log In
	@FXML
	private Button loginButton = new Button();
	private int type;
	private int userId;
	private String username;

	// Password Field, field which receives the password
	@FXML
	public PasswordField passwordText = new PasswordField();

	// User name Field, field which receives the user name
	@FXML
	public TextField usernameText = new TextField();

	@FXML
	/**
	 * This function is executed when the user hits the loginBUtton
	 * 
	 * @param event Catch the event of login button to check the login
	 */
	void userLogin(ActionEvent event) {
		if (validLogin()) {
			// Load Main Menu (next screen) loading the new Scene
			writePlayerConfig();
			Stage mainMenu = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Load Main Menu Scene
			loadMainMenuScene(mainMenu, type, userId);
		} else {
			// Login Failed
			loadLoginErrorView();
			System.out.println("Loggin Failed");
		}
	}

	/**
	 * This method loads the Main Menu Page
	 * 
	 * @param actualStage Stage to load the next Scene
	 */
	public void loadMainMenuScene(Stage actualStage, int type, int userId) {
		try {
			Parent root = FXMLLoader.load(ViewPath.class.getResource("MainMenuView.fxml"));
			Scene scene = new Scene(root, 640, 480);
			actualStage.setScene(scene);
			actualStage.show();
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
			// Scene with 640 width and 480 height
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
	 * This method returns if is a valid login or not
	 * 
	 * @return Returns true or false
	 */
	private boolean validLogin() {
		System.out.println(usernameText.getText());
		try {
			// Create the connection
			ConnectDBController c = new ConnectDBController();

			// Prepare the statement to check if login is valid
			PreparedStatement s = c.getConnection()
					.prepareStatement("SELECT type, username, ID FROM User where username like ? and password like ?");

			// Add parameters to check the login is valid
			s.setString(1, usernameText.getText());
			s.setString(2, passwordText.getText());

			// Execute the prepared statement and save it into ResultSet
			ResultSet r = s.executeQuery();

			// If the query returns values, then login is valid and returns true
			if (r.next()) {
				// Set properties to Play
				type = Integer.parseInt(r.getString(1));
				username = String.valueOf(r.getString(2));
				userId = Integer.parseInt(r.getString(3));
				return true;
			}

			// Else, the query doesn't returns values, then the login is invalid and returns
			// false
			return false;

		} catch (Exception e) {

			// Print in console
			e.printStackTrace();

			// If hits this point, login is not valid
			return false;
		}
	}

	/**
	 * This function writes the player config into a File, to play Game
	 * user_id;type;username
	 */
	public void writePlayerConfig() {
		try {
			PrintWriter writer = new PrintWriter("loggedUser.txt", "UTF-8");
			System.out.println("oke");
			writer.println("" + userId + ";" + type + ";" + username + "");
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
