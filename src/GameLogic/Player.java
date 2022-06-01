package GameLogic;


import controller.GameController;
import javafx.scene.layout.Pane;

/**
 * Player Class which inherits from Sprite
 */
public class Player extends Sprite {

	final double acceleration = 0.5;
	private float velocity = 0;
	private boolean movingLeft = false, movingRight = false;
	private boolean shooting = false;
	public Projectile plyrBull = new Projectile(0, 0, "https://assets.partyking.org/img/products/180/trollface-pappmask-1.jpg", false, getRoot());

	/**
	 * Player constructor
	 * @param x X of the player
	 * @param y Y of the player
	 * @param image image of the player
	 * @param root root Pane of the player
	 */
	public Player(int x, int y, String image, Pane root) {
		super(40, 40, x, y, image, root);
	}
	
	@Override
	/**
	 * Update the player
	 */
	public void update() {
		if(dead) return;
		move();
		if(shooting) shoot();
	}

	/**
	 * Moves the player
	 */
	public void move() {
		

		// Cancel all movement if at the edge of the screen.
		if ((getTranslateX() >= SCREEN_WIDTH - 10 - getWidth() && movingRight)
				|| (getTranslateX() <= 10 && movingLeft)) {
			velocity = 0;
			return;
		}

		// If at max velocity, don't let him accelerate further.
		if (velocity > MOVE_STEPS && movingRight) {
			velocity = MOVE_STEPS;
			setTranslateX(getTranslateX() + velocity);
			return;
		} else if (velocity < -MOVE_STEPS && movingLeft) {
			velocity = -MOVE_STEPS;
			setTranslateX(getTranslateX() + velocity);
			return;
		}

		// If pressing both inputs or neither, remove speed.
		if (((movingLeft && movingRight) || (!movingLeft && !movingRight)) && velocity != 0) {
			velocity -= velocity > 0 ? acceleration : -acceleration;


		} else if (movingLeft) {
			velocity -= acceleration;
		} else if (movingRight){
			velocity += acceleration;
		}
		
		//
		if(velocity == 0) return;

		setTranslateX(getTranslateX() + velocity);
	}
	
	/**
	 * Shoots top
	 */
	public void shoot() {
		// TODO Auto-generated method stub
		if(!plyrBull.isDead()) return;

		plyrBull.setTranslateX((int)(getTranslateX() + getWidth()/2));
		plyrBull.setTranslateY((int)(getTranslateY() - getHeight()/2));
		plyrBull.setDead(false);
		
		getRoot().getChildren().add(plyrBull);
	}

	/**
	 * Manage the move of the player
	 * @param moving true to move
	 */
	public void setMovingLeft(boolean moving) {
		this.movingLeft = moving;
	}

	/**
	 * Manage the move of the player
	 * @param movingRight true to move
	 */
	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	/**
	 * Sets if shooting
	 * @param shooting shooting
	 */
	public void setShooting(boolean shooting) { this.shooting = shooting; }
}
