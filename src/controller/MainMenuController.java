package controller;

import java.io.FileReader;
import css.CssPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.ViewPath;
/**
 * This Class, manage the Login Page
 *
 */
public class MainMenuController{
	private Parent parent;
	private Stage prevStage;
	private String username;
	private String[] loginInfo;
	
	/* Buttons and items */
	@FXML 
	private ImageView userImage;
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
	
	/**
	 * Empty Constructor
	 */
	public MainMenuController() {
		
	}
	
	/**
	 * Constructor to load Main Menu Scene in Stage
	 * @param stage previous Stage
	 */
	public MainMenuController(Stage stage) {
		this.prevStage = stage;
		loadMainMenuView();
	}

	/**
	 * Loads the Main Menu View
	 */
	private void loadMainMenuView() {
		try {
			parent = FXMLLoader.load(ViewPath.class.getResource("MainMenuView.fxml"));
			Scene scene = new Scene(parent,640,480);
			scene.getStylesheets().add(CssPath.class.getResource("application.css").toExternalForm());
			
			// Change Scene
			prevStage.setScene(scene);
			prevStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function writes the player configuration into a File, to play Game
	 * user_id;type; user name
	 */
	public void readAndSavePlayerConfig(){
		int c;
		String content = "";
		FileReader fr;
		
		try {
			fr = new FileReader("loggedUser.txt");
			while((c = fr.read()) != -1) {
				content += (char)c;
			}						
			loginInfo = content.split(";");
			
			// Save to Local Properties
			username = loginInfo[2];
			fr.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads User data and User Image of the user
	 */
	public void initialize() {
		readAndSavePlayerConfig();
		Image s = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/Alien01.svg/853px-Alien01.svg.png");
		userImage = new ImageView(s);
		usernameText.setText("User: "+username);
	}
	
	@FXML
	/**
	 * Loads the Normal Game 
	 * @param event Event to catch the click of the User
	 */
	void playNormal(ActionEvent event) {
		System.out.println("Playing Normal Game");
		prevStage = ((Stage)((Node)event.getSource()).getScene().getWindow());
		new GameController(prevStage, 1);
	}

	@FXML
	/**
	 * Loads the Ranked Game 
	 * @param event Event to catch the click of the User
	 */
	void playRanked(ActionEvent event) {
		System.out.println("Playing Ranked Game");
		prevStage = ((Stage)((Node)event.getSource()).getScene().getWindow());
		new GameController(prevStage, 0);
	}

	@FXML
	/**
	 * Loads the Show Ranking 
	 * @param event Event to catch the click of the User
	 */
	void showRanking(ActionEvent event) {
		System.out.println("Show Ranking");
		prevStage = ((Stage)((Node)event.getSource()).getScene().getWindow());
		new RankingController(prevStage);
	}

	@FXML
	/**
	 * Loads the Show User Options View
	 * @param event Event to catch the click of the User
	 */
	void showUserOptions(ActionEvent event) {
		System.out.println("Show User Options");
	}

	@FXML
	/**
	 * Loads the Show Configuration View
	 * @param event Event to catch the click of the User
	 */
	void showConfiguration(ActionEvent event) {
		System.out.println("Show Configuration");		
		prevStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	loadConfiguration();
	}
	
	/**
	 * This method loads the Main Menu Page
	 * @param actualStage Stage to load the next Scene
	 */
	public void loadConfiguration() {
		try {
			new ConfigurationController(prevStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
