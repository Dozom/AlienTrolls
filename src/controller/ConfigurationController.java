package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;

public class ConfigurationController {
    @FXML
    private Label alienSpeedLabel;

    @FXML
    private Slider alienSpeedSlider;

    @FXML
    private Button cancelButton;

    @FXML
    private Spinner<?> columnsSpinner;

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

    @FXML
    void saveForAllPlayers(ActionEvent event) {
    	//image_aliens YES URL
    	//speed_aliens YES INTEGER 1 A 10
    	//rows YES 1 A 6
    	//columns YES 1 A 12
    	//image_player YES URL
    	//rotate_aliens TODO 
    	//user_id YES LO QUE SEA
    	//ranked TODO 
    	//size_blocs TODO
    	//num_blocs YES MIN 0 MAX 4
    	//game_name YES STRING
    	//kill_player_shots YES INTEGER 1 A 3
    	//speed_player YES INTEGER 1 A 10
    	int alienSpeed = (int)alienSpeedSlider.getValue();
    	int playerSpeed = (int)playerSpeedSlider.getValue();
    	int projectileSpeed = (int)projectileSpeedSlider.getValue();
    	int numRows = (int)rowsSpinner.getValue();
    	int numColumns = (int)columnsSpinner.getValue();
    	
    	// Set for all players
    	System.out.println("Guardando la configuracion");
    }

    @FXML
    void loadConfiguration(MouseEvent event) {
    	// Loads The Configuration
    }
}
