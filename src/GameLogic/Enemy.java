package GameLogic;

import controller.GameController;

public class Enemy extends Sprite{

	private static boolean direction;
	
	public Enemy(int x, int y, String image) {
		super(40, 40, x, y, image);
		
		
	}
	
	@Override
	public void moveDown() {
		if(getTranslateY() > 10)
			setTranslateY(getTranslateY() + MOVE_STEPS*5);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(dead) {
			GameController.getPane().getChildren().remove(this);
		} else {
			move();
			
		}
	}
	
	private void move() {
		if(direction) setTranslateY(getTranslateY() - MOVE_STEPS);
		else setTranslateY(getTranslateY() + MOVE_STEPS);
	}
	

}
