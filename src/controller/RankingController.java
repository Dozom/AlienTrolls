package controller;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import RankingViews.PlayerOnly;
import images.ImagePath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.ViewPath;
public class RankingController {	
	Connection conn;
	String[] loginInfo;
	ArrayList<PlayerOnly> playerData = new ArrayList<PlayerOnly>();
	
	public RankingController(Stage stage) {
		loadRankingScene(stage);
	}

	
	/**
	 * This Set Up the BoderPane to show the Ranking
	 * @return Returns a Parent with all Data
	 */
	private Parent setupRanking() {		
		BorderPane root = new BorderPane();		
		root.setPrefSize(640, 480);
		root.setStyle("-fx-background-image: url('"+ImagePath.class.getResource("MainBackground.jpg").toExternalForm()+"');");
		readPlayerConfig();

		root.setTop(titleLabel());
		root.setCenter(table());
		root.setBottom(backButton());
		return root;
	} 
	
	/**
	 * This creates the Table Structure and fill it with data
	 * @return Returns a Table filled of data
	 */
	private TableView<PlayerOnly> table() {
		// Initialize table
		TableView<PlayerOnly> table = new TableView<PlayerOnly>();

		// Initialize place column
		TableColumn placeColumn = new TableColumn("Place"); 
		placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));

		// Initialize score column
		TableColumn scoreColumn = new TableColumn("Score"); 
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		
		// Initialize User Name column
		TableColumn usernameColumn = new TableColumn("Username"); 
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

		// Initialize configuration column
		TableColumn configurationColumn = new TableColumn("Game Name"); 
		configurationColumn.setCellValueFactory(new PropertyValueFactory<>("gamename"));
		
		// Initialize policy to resize window
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		// Add columns to the table
		table.getColumns().addAll(placeColumn, scoreColumn, usernameColumn, configurationColumn);
		
		// Get Player data from user_id
		getPlayerDataDB(Integer.parseInt(loginInfo[0]));
		
		// Load data into Table
		for (int i = 0; i < playerData.size(); i++) {
			table.getItems().add(new PlayerOnly(i+1, playerData.get(i).getScore(), playerData.get(i).getUsername(), playerData.get(i).getGamename()));			
		}
		
		// Return table filled of content
		return table;
	}


	private Button backButton() {
		Button b = new Button("Back to Main Menu");
		b.setOnAction(e ->{
			System.out.println("Boto clicat");
			Stage mainMenu = (Stage) ((Node) e.getSource()).getScene().getWindow();
			
			// Load Main Menu Scene
			loadMainMenuScene(mainMenu);

		});
		return b;
	}
	
	private void loadMainMenuScene(Stage actualStage) {

		try {
			Parent root = FXMLLoader.load(ViewPath.class.getResource("MainMenuView.fxml"));
			Scene scene = new Scene(root, 640, 480);
			actualStage.setScene(scene);

			actualStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private Label titleLabel() {
		Label title = new Label("History of Player: " + loginInfo[2]);
	    title.setStyle("-fx-font-weight: bold; -fx-font-size: 18; -fx-text-fill: green; -fx-padding:10 5 10 5");
	    return title;
	}
	/**
	 * This method loads the Ranking Scene
	 * @param stage Stage Parameter to change Stage Scene
	 */
	public void loadRankingScene(Stage stage) {
		try {
			Scene sc = new Scene(setupRanking());
			stage.setScene(sc);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
    public boolean getPlayerDataDB(int userid) {
    	try {        		
	    	ConnectDBController c = new ConnectDBController();
			conn = c.getConnection();
	    	PreparedStatement s = conn.prepareStatement(
	    		"SELECT m.score, u.username, c.game_name FROM User u INNER JOIN Matches m ON (u.ID = m.user_id) INNER JOIN Configuration c ON (m.game_id = c.ID) WHERE " +
	    	    		" u.id = ? ORDER BY m.score DESC;"

				);						
			s.setInt(1,userid);
			ResultSet retrievedData = s.executeQuery();
			int i = 0;
			while (retrievedData.next()) {				
				playerData.add(
					new PlayerOnly(
						i, 
						Integer.valueOf(retrievedData.getString(1)), 
						String.valueOf(retrievedData.getString(2)), 
						String.valueOf(retrievedData.getString(3))
					)
				);
				i++;
			}
			return true;
    	} catch (Exception e) {
    		e.printStackTrace();
		}    
    	return false;
    }
    
    /*
     * This function reads the player configuration
     */
	public void readPlayerConfig(){
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
}
