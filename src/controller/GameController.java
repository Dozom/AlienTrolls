package controller;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import GameLogic.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.ViewPath;
enum Test {
	valor1,
	valor2
}

public class GameController {
	
	Random r;
	private Pane root = new Pane();	
	private Sprite plyr = new Sprite(40, 40, 300, 400, "player", Color.BLUE);
	private Sprite plyrBull = new Sprite(5, 20, 0, 0, "playerBullet", Color.BLACK);
	
	private final int ROWS = 5;
	private final int COLUMNS = 10;
	private Sprite[][] aliens = new Sprite[ROWS][COLUMNS];
	
	
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
	 * Runs every tick. Moves bullets and enemies, checks collisions, and checks deaths.
	 */
	private void update() {
		
		//t+=0.016; //Each tick is calculated every 16ms.

		if(!plyrBull.isDead()) {
			plyrBull.moveUp();

			for (int i = 0; i < aliens.length; i++) {
				for (int j = 0; j < aliens[i].length; j++) {
					if(!plyrBull.isDead() && !aliens[i][j].isDead() &&
						plyrBull.getBoundsInParent().intersects(aliens[i][j].getBoundsInParent())
					) {
						kill(aliens[i][j]);
						kill(plyrBull);
					}
				}
			}
			
			if(plyrBull.getTranslateY() < 0) {
				kill(plyrBull);
			}
		}
		
	}
	
	/**
	 * Adds enemies.
	 */
	private void nextLevel() {
		for (int i = 0; i < aliens.length; i++) {
			for (int j = 0; j < aliens[i].length; j++) {
				 aliens[i][j] = new Sprite(30, 30, 50+j*50, 50+i*50, "enemy", Color.RED);
				 root.getChildren().add(aliens[i][j]);
			}
		}
	}
	
	/**
	 * Shoots 
	 * @param shooter
	 */
	private void shoot(Sprite shooter) {
		if(shooter.getType().equals("player")) {
			System.out.println("henlo");
			if(!plyrBull.isDead()) return;
			System.out.println("hola");
			plyrBull.setTranslateX((int)(plyr.getTranslateX() + plyr.getWidth()/2));
			plyrBull.setTranslateY((int)(shooter.getTranslateY() - shooter.getHeight()/2));
			plyrBull.setDead(false);
			
			root.getChildren().add(plyrBull);
		} else {
			Sprite enemyBull = new Sprite(5, 20,(int)(shooter.getTranslateX() + shooter.getWidth()/2),
				     (int)(shooter.getTranslateY() - shooter.getHeight()/2), "enemyBullet", Color.BLACK);
			root.getChildren().add(enemyBull);
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
				case A: 
					plyr.moveLeft();
					break;
				case D:
					plyr.moveRight();
					break;
				case SPACE:
					shoot(plyr);
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
	 * Sets a sprite to dead, removing it from collision checks or movement.
	 * @param s
	 */
	private void kill(Sprite s) {
		root.getChildren().remove(s);
		s.setDead(true);
	}
}
