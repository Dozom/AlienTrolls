package GameLogic;

import controller.GameController;
import javafx.scene.layout.Pane;

/**
 * Projectile which inherits from Sprite
 */
public class Projectile extends Sprite{

	private boolean direction;
	
	/**
	 * Projectile Constructor
	 * @param x X of the projectile
	 * @param y Y of the projectile
	 * @param image image of the projectile
	 * @param direction direction of the projectile
	 * @param root root Pane of the projectile
	 */
	public Projectile(int x, int y, String image, boolean direction, Pane root) {
		super(5, 20, x, y, image, root);
		this.direction = direction;
	}

	@Override
	/**
	 * Updates the Projectile
	 */
	public void update() {
		// TODO Auto-generated method stub
		move();
	}
	
	/**
	 * Updates the move of Projectile
	 */
	private void move() {
		if(direction) setTranslateY(getTranslateY() + MOVE_STEPS*2);
		else setTranslateY(getTranslateY() - MOVE_STEPS*2);
		
		if(getTranslateY() > SCREEN_HEIGHT || getTranslateY() < 0) {
			getRoot().getChildren().remove(this);
			setDead(true);
		}
	}

}
