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
import java.sql.*;
/**
 * This Class, manage the Login Page
 *
 */
public class LoginController{

	/*
	 * Buttons and items */
	@FXML
	private Button loginButton = new Button();

	@FXML
    public PasswordField passwordText = new PasswordField();

	@FXML
    public TextField usernameText = new TextField();

	@FXML
	void userLogin(ActionEvent event) {
    	if(validLogin()) {
    		// Load Main Menu    		
    		loadMainMenuScene((Stage)((Node)event.getSource()).getScene().getWindow());
    	} else {
    		System.out.println("Loggin Failed");
    	}
    }

	/**
	 * This method loads the Main Menu Page
	 * @param actualStage Stage to load the next Scene
	 */
	public void loadMainMenuScene(Stage actualStage) {
		try {
			Parent root = FXMLLoader.load(ViewPath.class.getResource("MainMenuView.fxml"));
			Scene scene = new Scene(root,640,480);
			actualStage.setScene(scene);
			actualStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * This method returns if is a valid login or not
	 * @return Returns true or false 
	 */
	private boolean validLogin() {
		System.out.println(usernameText.getText());

		try {
			ConnectDBController c = new ConnectDBController();
			Connection conn = c.getConnection();
			PreparedStatement s = conn.prepareStatement("SELECT username FROM User where username like ? and password like ?");						
			s.setString(1, usernameText.getText());
			s.setString(2, passwordText.getText());
			ResultSet r = s.executeQuery();
			if(r.next()) {
				return true;				
			} else {
				return false;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}
    


}
