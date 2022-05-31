package GameLogic;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Player extends Sprite {

	final double acceleration = 0.25;
	private float velocity = 0;
	private boolean movingLeft = false, movingRight = false;

	public Player(int w, int h, int x, int y, String type, Color color) {
		super(w, h, x, y, type, color);
		
		Image s;
		try {
			// Alien https://cdn.picpng.com/alien/alien-head-green-face-creature-55271.png
			
			s = new Image("https://cdn.picpng.com/alien/alien-head-green-face-creature-55271.png");
			super.setFill(new ImagePattern(s));
		} catch (Exception e) {
			System.out.println("HI ha hagut un error al carregar la imatge");
		}
//		Image s = new Image("https://assets.partyking.org/img/products/180/trollface-pappmask-1.jpg");
		// TODO Auto-generated constructor stub
	}
	
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

	public void setMovingLeft(boolean moving) {
		this.movingLeft = moving;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

}
