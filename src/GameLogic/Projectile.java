package GameLogic;

import controller.GameController;
import javafx.scene.layout.Pane;

public class Projectile extends Sprite{

	private boolean direction;
	
	public Projectile(int x, int y, String image, boolean direction, Pane root) {
		super(5, 20, x, y, image, root);
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
			getRoot().getChildren().remove(this);
			setDead(true);
		}
	}

}
