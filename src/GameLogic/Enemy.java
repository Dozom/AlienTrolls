package GameLogic;

import controller.GameController;
import javafx.scene.layout.Pane;

/**
 * Enemy Class 
 */
public class Enemy extends Sprite{

	private static boolean direction;
	
	/**
	 * Constructor of the Enemy
	 * @param x X of the Enemy
	 * @param y Y of the Enemy
	 * @param image image of the Enemy
	 * @param root Root Pane of the Enemy
	 */
	public Enemy(int x, int y, String image, Pane root) {
		super(40, 40, x, y, image, root);
		
		
	}
	
	@Override
	/**
	 * Move the Enemy Down
	 */
	public void moveDown() {
		if(getTranslateY() > 10)
			setTranslateY(getTranslateY() + MOVE_STEPS*5);

	}

	@Override
	/**
	 * Update the enemy
	 */
	public void update() {
		// TODO Auto-generated method stub
		if(dead) {
			getRoot().getChildren().remove(this);
		} else {
			move();
			
		}
	}
	
	/**
	 * Move the enemy
	 */
	private void move() {
		if(direction) setTranslateY(getTranslateY() - MOVE_STEPS);
		else setTranslateY(getTranslateY() + MOVE_STEPS);
	}
	

}
