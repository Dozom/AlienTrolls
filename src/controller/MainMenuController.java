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
	private String[] loginInfo;
	/**
	 * This function writes the player config into a File, to play Game
	 * user_id;type;username
	 */
	public void writePlayerConfig(){
		int c;
		String content = "";
		try {
			FileReader fr = new FileReader("loggedUser.txt");
			while((c = fr.read()) != -1) {
				content += (char)c;
			}
						
			loginInfo = content.split(";");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		writePlayerConfig();
		
		Image s = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/Alien01.svg/853px-Alien01.svg.png");
		userImage = new ImageView(s);
		usernameText.setText("User: "+loginInfo[2]);
	}
	
	@FXML
	void playNormal(ActionEvent event) {
		System.out.println("Playing Normal Game");
		GameController u = new GameController(((Stage)((Node)event.getSource()).getScene().getWindow()));

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
    	loadConfiguration((Stage)((Node)event.getSource()).getScene().getWindow());
//    	new ConfigurationController((Stage)((Node)event.getSource()).getScene().getWindow());
	}
/**
	 * This method loads the Main Menu Page
	 * @param actualStage Stage to load the next Scene
	 */
	public void loadConfiguration(Stage actualStage) {
		try {
			Parent root = FXMLLoader.load(ViewPath.class.getResource("Configuration.fxml"));
			Scene scene = new Scene(root, 640, 480);
			scene.getStylesheets().add(CssPath.class.getResource("application.css").toExternalForm());
			actualStage.setScene(scene);
			actualStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void getUsername() {
		
	}

}
