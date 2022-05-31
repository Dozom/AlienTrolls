package GameLogic;

import controller.GameController;

public class Projectile extends Sprite{

	private boolean direction;
	
	public Projectile(int x, int y, String image, boolean direction) {
		super(5, 20, x, y, image);
		this.direction = direction;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		move();
	}
	
	private void move() {
		if(direction) setTranslateY(getTranslateY() + MOVE_STEPS*2);
		else setTranslateY(getTranslateY() - MOVE_STEPS*2);
		
		if(getTranslateY() > SCREEN_HEIGHT || getTranslateY() < 0) {
			GameController.getPane().getChildren().remove(this);
			setDead(true);
		}
	}

}
