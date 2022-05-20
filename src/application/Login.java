package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;


public class Login {

	// JDBC
	// Jdbc:mysql://remotemysql.com/kFY9D6dHBr
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    @FXML
    void userLogin(ActionEvent event) {
    	checkLogin();
    }

	private void checkLogin() {
		// TODO Auto-generated method stub
		System.out.println(usernameText.getText());

		try {
			
			Connection c=DriverManager.getConnection("Jdbc:mysql://remotemysql.com/kFY9D6dHBr", "kFY9D6dHBr", "nYvgnwVsAK");
			
			Statement s = c.createStatement();
			
			ResultSet r = s.executeQuery("SELECT * FROM User;"); //Funciona!!
			
			while(r.next()) {
				System.out.println(r.getString(1) + r.getString(2) + r.getString(3));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
    


}
