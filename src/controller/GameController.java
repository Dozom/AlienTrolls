package controller;

import java.util.Random;
import GameLogic.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.ViewPath;

public class GameController {
	
	private int score = 0;
	Random r;
	private static Pane root = new Pane();	
	private Player plyr = new Player(300, 400, "/home/rguinartv/Imatges/sneed.png");
	private Projectile plyrBull = plyr.plyrBull;
	
	private final int ROWS = 4;
	private final int COLUMNS = 6;
	private final int maxScore = (ROWS * (ROWS + 1) / 2) * 10 * COLUMNS;
	private Enemy[][] aliens = new Enemy[ROWS][COLUMNS];
	private Projectile[] alienBulls = new Projectile[10]; //At most, 3 projectiles will be on screen at the same time.
	private boolean alienDir;
	private boolean gameOver;
	
	
	GameController(Stage stage) {
		loadGameScene(stage);
	}

	
	/**
	 * Changes window size if needed, creates the player and starts the game loop.
	 * @return the pane
	 * 
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
	
		return root;
	} 

	
	/**
	 * Runs every tick. Moves bullets and enemies, checks collisions and deaths.
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
				 aliens[i][j] = new Enemy(50+j*50, 50+i*50, "https://assets.partyking.org/img/products/180/trollface-pappmask-1.jpg");
				 root.getChildren().add(aliens[i][j]);
			}
		}
	}
	
	/**
	 * Shooting function for the aliens.
	 * @param shooter alien that shoots
	 */
	private void shoot(Sprite shooter) {
		for (int i = 0; i < alienBulls.length; i++) {
			if(alienBulls[i] == null || alienBulls[i].isDead()) {
				alienBulls[i]  = new Projectile((int)(shooter.getTranslateX() + shooter.getWidth()/2),
					     (int)(shooter.getTranslateY() + shooter.getHeight()/2), 
					     "https://assets.partyking.org/img/products/180/trollface-pappmask-1.jpg", true);
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

			for (Enemy[] aliens : aliens) {
				for (Enemy alien : aliens) {
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
			String gameOverText = hasWon ? "Has guanyat, felicitats!" : "Has perdut!";
			Button tancar = new Button("Tancar joc");
			tancar.setOnAction(e -> {
				Platform.exit();
			});;
			
			Button mainMenu = new Button("Menu Principal");
			Label b = new Label(gameOverText);
			final  Stage stage = new Stage();           
			BorderPane root = new BorderPane();
			root.setCenter(b);
			BorderPane Botons = new BorderPane();
			Botons.setLeft(mainMenu);
			Botons.setRight(tancar);
			root.setBottom(Botons);
			Scene scene = new Scene(root, 200, 100);						          						         
			stage.setScene(scene);        
			stage.show();
		}
	}
	
	private void loadMainMenu() {
		
	}
	

	/**
	 * Returns the parent element.
	 */
	public static Pane getPane() {
		return root;
	}

}
