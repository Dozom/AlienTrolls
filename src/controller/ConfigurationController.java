package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;

public class ConfigurationController {

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
    	System.out.println((int)alienSpeedSlider.getValue());
    }
    
    @FXML
    void getProjectileSpeed(MouseEvent event) {
    	System.out.println((int)projectileSpeedSlider.getValue());
    	projectileSpeedLabel.setText(String.valueOf((int)projectileSpeedSlider.getValue()));
    }

    @FXML
    void getPlayerSpeed(MouseEvent event) {
    	System.out.println((int)playerSpeedSlider.getValue());
    }

    @FXML
    void saveConfigurationOnlyPlayer(MouseEvent event) {
    	// Set the Configuration for only one player
    }

    @FXML
    void saveForAllPlayers(MouseEvent event) {
    	// Set for all players
    }

    @FXML
    void loadConfiguration(MouseEvent event) {
    	// Loads The Configuration
    }
}
