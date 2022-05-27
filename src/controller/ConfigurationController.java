package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.ViewPath;

public class ConfigurationController {
	// Connection to DB
	Connection conn;

	@FXML
    private Label alienSpeedLabel;

    @FXML
    private TextField gameName;

    @FXML
    private TextField alienImage;
    
    @FXML
    private TextField playerImage;

    @FXML
    private Slider alienSpeedSlider;

    @FXML
    private Button cancelButton;

    @FXML
    private Spinner<?> columnsSpinner;

    @FXML
    private Spinner<?> killPlayerShots;

    @FXML
    private Button loadConfigurationButton;

    @FXML
    private Slider playerSpeedSlider;

    @FXML
    private Slider projectileSpeedSlider;

    @FXML
    private Spinner<?> rowsSpinner;

    @FXML
    private Button saveConfigurationButton;

    @FXML
    private Button saveForAllPlayersButton;

    @FXML
    private Label projectileSpeedLabel;
    
    @FXML
    private Label playerSpeedLabel;

    @FXML
    void getAlienSpeed(MouseEvent event) {
    	alienSpeedLabel.setText(String.valueOf((int)alienSpeedSlider.getValue()));
    }
    
    @FXML
    void getProjectileSpeed(MouseEvent event) {
    	projectileSpeedLabel.setText(String.valueOf((int)projectileSpeedSlider.getValue()));
    }

    @FXML
    void getPlayerSpeed(MouseEvent event) {
    	playerSpeedLabel.setText(String.valueOf((int)playerSpeedSlider.getValue()));
    }

    @FXML
    void saveConfigurationOnlyPlayer(MouseEvent event) {
    	// Set the Configuration for only one player
    }

    /**
     * This function save the configuration for all the players
     * @param event Event parameter to catch the click of the button
     */
    @FXML
    void saveForAllPlayers(ActionEvent event) {
    	boolean result;
    	try {
    		
	    	ConnectDBController c = new ConnectDBController();
			conn = c.getConnection();
			result = insertConfiguration(conn, alienImage.getText(), (int)alienSpeedSlider.getValue(), playerImage.getText(), 0, 0, 1, 10, 2, gameName.getText(), (int)killPlayerShots.getValue(), (int) playerSpeedSlider.getValue(), (int)rowsSpinner.getValue(), (int)columnsSpinner.getValue());
			// Loads the Menu Scene
			loadMainMenuScene((Stage)((Node)event.getSource()).getScene().getWindow());
    	} catch (Exception e) {
    		e.printStackTrace();
		}    	
    	System.out.println("Guardando la configuracion");
    }
    
    /**
     * This function inserts the values in the Configuration Table
     * @param c Connection to insert the data
     * @param alienImage Insert the String of the url of the image
     * @param speedAliens Insert the Speed of the Aliens
     * @param playerImage Insert the Sting of the url of the image
     * @param rotateAliens Insert if the aliens rotate or not
     * @param userId Insert the user id 
     * @param ranked Insert the ranked
     * @param sizeBlocs Insert the size of the blocs
     * @param numBlocs Insert the num of the blocs
     * @param gameName Insert the game name
     * @param killPlayerShots Insert the shots to kill the player
     * @param speedPlayer Insert the speed of the player
     * @param rowsAlien Insert the rows Of Aliens
     * @param columnsAlien Insert the columns of the Aliens
     * @return Returns true if the insert is Ok, otherwise, returns false
     */
    public boolean insertConfiguration(Connection c, String alienImage, int speedAliens, String playerImage, int rotateAliens, int userId, int ranked, int sizeBlocs, int numBlocs, String gameName, int killPlayerShots, int speedPlayer, int rowsAlien, int columnsAlien) {
    	try {
			PreparedStatement s = c.prepareStatement("INSERT INTO "
					+ "Configuration(image_aliens, speed_aliens, image_player, rotate_aliens, user_id, ranked, size_blocs, "
					+ "num_blocs, game_name, kill_player_shots, speed_player, rows_alien, columns_alien) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");						
			s.setString(1,alienImage);
			s.setInt(2,speedAliens);
			s.setString(3,playerImage);
			s.setInt(4,rotateAliens);
			s.setInt(5,userId);
			s.setInt(6,ranked);
			s.setInt(7,sizeBlocs);
			s.setInt(8,numBlocs);
			s.setString(9,gameName);
			s.setInt(10,killPlayerShots);
			s.setInt(11,speedPlayer);
			s.setInt(12,rowsAlien);
			s.setInt(13,columnsAlien);
			boolean result = s.execute();
	    	return result;
    	} catch (Exception e) {
    		e.printStackTrace();
		}    
    	return false;
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
     * This function loads a configuration.
     * @param event
     */
    @FXML
    void loadConfiguration(MouseEvent event) {
    	// Loads The Configuration
    }
}
