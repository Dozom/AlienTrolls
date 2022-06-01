package controller;

import GameLogic.*;
import css.CssPath;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This Class manage the Game Scene
 */
public class GameController {
	private int type;
	private int userId;
	private String username;

	private Pane root = new Pane();	
	private int score = 0;
	Stage prevStage;
	private final int ROWS = 3;
	private final int COLUMNS = 5;
	private final int maxScore = (ROWS * (ROWS + 1) / 2) * 10 * COLUMNS;
	
	private Player plyr = new Player(300, 400, "https://cdn.picpng.com/alien/alien-head-green-face-creature-55271.png", root);
	private Projectile plyrBull = plyr.plyrBull;
	private Enemy[][] aliens = new Enemy[ROWS][COLUMNS];
	private Projectile[] alienBulls = new Projectile[10]; //At most, 10 projectiles will be on screen at the same time.
	
	private boolean alienDir;
	private boolean gameOver;
	private int ranked;	
	
	/**
	 * This constructor initialize the Game Screen
	 * @param stage Previous Stage
	 * @param ranked If game is ranked or not
	 */
	GameController(Stage stage, int ranked) {
		this.prevStage = stage;
		this.ranked = ranked;
		loadGameScene(prevStage);
	}
	
	/**
	 * Returns the Type of the Game
	 * @return 
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the Type of the Game
	 * @param type type parameter to set
	 */
	public void setType(int type) {
		this.type = type;
	}


	/**
	 * Gets the User Id
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Setter userId
	 * @param userId User Id parameter
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the user name
	 * @return returns the user name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user name
	 * @param username user name to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	
	/**
	 * Changes window size if needed, creates the player and starts the game loop.
	 * @return the pane
	 * @see update
	 */
	private Parent setupGame() {
		
		root.setPrefSize(640, 480);
		root.getChildren().add(plyr);
		gameOver = false;
		
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
			}
		};
		
		timer.start();
		
		nextLevel();
		if (ranked == 1) {
			root.setId("rankedGame");
		} else {
			root.setId("gamePane");
		}		
		root.getStylesheets().add(CssPath.class.getResource("gameBackground.css").toExternalForm());			
		return root;
	} 

	
	/**
	 * Runs every tick. Moves bullets and enemies, checks collisions, and checks deaths.
	 */
	private void update() {

		//MOVEMENT
		plyr.update();
		moveAliens();
		for (Projectile p : alienBulls) { if(p != null) p.update(); }

		//COLLISIONS
		
		if(!plyrBull.isDead()) {
			plyrBull.update(); 

			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLUMNS; j++) {
					if(!plyrBull.isDead() && !aliens[i][j].isDead() &&
						plyrBull.getBoundsInParent().intersects(aliens[i][j].getBoundsInParent())
					) {
						
						score += (ROWS - i) * 10;							
						if (score == maxScore) {
							gameOver(true);
							System.out.println("El joc ha acabat. Puntuació: " + score);
						}
						
						kill(aliens[i][j]);
						System.out.println(i + ", " + j);
						kill(plyrBull);
					}
				}
			}
			
			if(plyrBull.getTranslateY() < 0) {
				kill(plyrBull);
			}
			
		}
		
		//Enemy shooting
		if(score != maxScore) {
			
			if(Math.random() < 0.04) {
				int i , j;
				do {
					i = (int)(Math.random() * ROWS);
					j = (int)(Math.random()*COLUMNS);
				} while (aliens[i][j].isDead());
				shoot(aliens[i][j]);
			}
		}

		//Enemy bullet collision check
		for (Projectile p : alienBulls) {
			if(p == null || p.isDead()) return;
			
			if(p.getBoundsInParent().intersects(plyr.getBoundsInParent()) && !plyr.isDead()) {
				kill(plyr);
				gameOver(false);
			}
		}
		
		
		
	}
	
	/**
	 * Adds enemies.
	 */
	private void nextLevel() {
		for (int i = 0; i < aliens.length; i++) {
			for (int j = 0; j < aliens[i].length; j++) {
				 aliens[i][j] = new Enemy(50+j*50, 50+i*50, "https://cdn-icons-png.flaticon.com/512/779/779088.png", root);
				 root.getChildren().add(aliens[i][j]);
			}
		}
	}
	
	/**
	 * Shoots 
	 * @param shooter
	 */
	private void shoot(Sprite shooter) {
		for (int i = 0; i < alienBulls.length; i++) {
			if(alienBulls[i] == null || alienBulls[i].isDead()) {
				alienBulls[i]  = new Projectile((int)(shooter.getTranslateX() + shooter.getWidth()/2),
					     (int)(shooter.getTranslateY() + shooter.getHeight()), 
					     "https://assets.partyking.org/img/products/180/trollface-pappmask-1.jpg", true, root);
				root.getChildren().add(alienBulls[i]);
				break;
			}
		}
		
	}
	
	/**
	 * Begins the game and checks for input.
	 * @param stage stage
	 */
	public void loadGameScene(Stage stage) {
		try {
			Scene sc = new Scene(setupGame());

			sc.setOnKeyPressed(e -> {
				
				switch (e.getCode()) {
				case SPACE, X, Z:
					plyr.setShooting(true);
					break;
				case A, LEFT:
					plyr.setMovingLeft(true);
					break;
				case D, RIGHT:
					plyr.setMovingRight(true);
				default:
					break;
				}
			});
			
			sc.setOnKeyReleased(e -> {
				
				switch (e.getCode()) {
				case SPACE, X, Z:
					plyr.setShooting(false);
					break;
				case A, LEFT:
					plyr.setMovingLeft(false);
					break;
				case D, RIGHT:
					plyr.setMovingRight(false);
				default:
					break;
				}
			});
			
			stage.setScene(sc);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Moves all the aliens. 
	 * Can move to the sides and down, and handles the logic for it.
	 * @see checkAlienColumns
	 */
	private void moveAliens() {
		
		if(checkAlienColumn()) { //If they've reached one of the screen's edges...

			for (Sprite[] aliens : aliens) {
				for (Sprite alien : aliens) {
					alien.moveDown();
				}
			}
			alienDir = !alienDir; //Change direction
			if(checkAlienRows()) {
				gameOver(false);
			}
			
		} else {
			
			for (Sprite[] aliens : aliens) {
				for (Sprite alien : aliens) {
					
					//alienDir : right -> true | left <- false
					if(alienDir) {
						alien.moveRight();
					} else {
						alien.moveLeft();;
					}
				}
			}
		}
	}
	
	/**
	 * Checks if a column of aliens has reached the edge of the screen. If it has, it moves down and changes direction.
	 * @return whether it has reached the edge of the screen. 
	 */
	private boolean checkAlienColumn() {
		
		//TODO: check only outer columns instead of all aliens
			//If column 0 is at > 10, then the other columns can't be on the left side. No need to check for it in that case.
			//If column COLUMNS-1 is at < WIDTH-10, then the other columns can't be on the right side. No need to check for it in that case.
			//If one of them is outside the screen, check if they're alive. If they aren't, then do the same check with the column next to them.
			//We check until one of the aliens is alive and outside, or we find a column inside the range.
		
		
		for (Sprite[] aliens : aliens) {
			for (Sprite alien : aliens) {
				if(!alien.isDead()) { //Don't check if the alien is dead
					if(alienDir && !alien.checkRight()) { //If they're moving to the right and it's outside the screen from the right.
						return true;
					} else if(!alienDir && !alien.checkLeft()) {//If they're moving to the left and it's outside the screen from the left.
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if the aliens have reached the limit of the map. If they have, it's game over, the player loses.
	 * @return whether the aliens have reached their destination.
	 */
	private boolean checkAlienRows() {
			for (int j = ROWS-1; j >= 0; j--) {
				if(aliens[j][0].getTranslateY() >= plyr.getTranslateY() - plyr.getHeight()/2) { 
					//If it's below the limit
					
					for (int i = 0; i < aliens[j].length; i++) {
						//If at least one alien isn't dead, then it's game over.
						if(!aliens[j][i].isDead()) { 
							return true;
						}
					}
				} else { //If the row is above the limit, return false - the game isn't over!
					return false;
				}
			}
			return false;
		}
	
	/**
	 * Sets a sprite to dead, removing it from collision checks or movement.
	 * @param s entity to kill
	 */
	private void kill(Sprite s) {
		root.getChildren().remove(s);
		s.setDead(true);
	}
	
	/**
	 * Game over screen.
	 * @param hasWon boolean, whether the player has won or lost.
	 */
	private void gameOver(boolean hasWon) {
		if(!gameOver) {
			gameOver = true;
			endedGameScreen(hasWon);
		}
	}

	/**
	 * Loads the ended Game Screen
	 * @param hasWon it save if the user won or lose
	 */
	private void endedGameScreen(boolean hasWon) {
		final  Stage stage = new Stage();           

		// Label Text
		String gameOverText = hasWon ? "Has guanyat, felicitats!" : "Has perdut!";

		// Button to close the game
		Button tancar = new Button("Tancar joc");
		tancar.setOnAction(e -> {
			// Exit application
			Platform.exit();
		});			
		
		// Button to load Main Menu
		Button mainMenu = new Button("Menu Principal");
		mainMenu.setOnAction(e -> {
			closeEndedGameMenu(e);
			// Load Main Menu Scene
			loadMainMenuScene();
		});			

		// Text which indicates if the player won or lose
		Label winOrLose = new Label(gameOverText);
		
		// ended Game Screen Stage
		BorderPane endGameScreenGame = new BorderPane();
		endGameScreenGame.setCenter(winOrLose);
		
		// Add buttons
		BorderPane buttonsPane = new BorderPane();
		buttonsPane.setLeft(mainMenu);
		buttonsPane.setRight(tancar);
		endGameScreenGame.setBottom(buttonsPane);
		
		// Add Scene with the content and set it to the Stage & Show
		Scene scene = new Scene(endGameScreenGame, 200, 100);						          						         
		stage.setScene(scene);        
		stage.show();
	}

	/**
	 * Manage the Close button of the ended Game Screen
	 * @param e Event of the button
	 */
	private void closeEndedGameMenu(ActionEvent e) {
		Stage hideOnClick = ((Stage)((Node)e.getSource()).getScene().getWindow());
		hideOnClick.hide();
	}
	
	/**
	 * This method loads the Main Menu Page
	 * @param actualStage Stage to load the next Scene
	 */
	public void loadMainMenuScene() {
		try {
			new MainMenuController(prevStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	


}
