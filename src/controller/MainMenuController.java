package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.ViewPath;
/**
 * This Class, manage the Login Page
 *
 */
public class MainMenuController{

	/*
	 * Buttons and items */
	@FXML
	private Button playRankedButton = new Button();

	@FXML
    public Button playButton = new Button();

	@FXML
    public Button rankingButton = new Button();
	@FXML
    public Button configurationButton = new Button();
	@FXML
    public Button userOptionsButton = new Button();
	@FXML
    public Text usernameText = new Text();

	@FXML
	void playNormal(ActionEvent event) {
		System.out.println("Playing Normal Game");
	}

	@FXML
	void playRanked(ActionEvent event) {
		System.out.println("Playing Ranked Game");
	}

	@FXML
	void showRanking(ActionEvent event) {
		System.out.println("Show Ranking");
	}

	@FXML
	void showUserOptions(ActionEvent event) {
		System.out.println("Show User Options");
	}

	@FXML
	void showConfiguration(ActionEvent event) {
		System.out.println("Show Configuration");
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
	
	public void getUsername() {
		
	}

}
