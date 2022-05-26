package GameLogic;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
	final int SCREEN_WIDTH  = 640;
	final int SCREEN_HEIGHT = 480;
	
	boolean dead = false;
	
	private final int MOVE_STEPS = 10; //Move speed basically.
	public String type;
	
	public Sprite(int w, int h, int x, int y, String type,  Color color) {
		super(w,h,color);
		this.type = type;
		setTranslateX(x);
		setTranslateY(y);
	}
	

	/**
	 * Moves the sprite right by {@value MOVE_STEPS}.
	 */
	public void moveRight() {
		if(getTranslateX() < SCREEN_WIDTH - 10 - getWidth())
			setTranslateX(getTranslateX() + MOVE_STEPS);
	}

	/**
	 * Moves the sprite left by {@value MOVE_STEPS}.
	 */
	public void moveLeft() {
		if(getTranslateX() > 10)
			setTranslateX(getTranslateX() - MOVE_STEPS);
	}

	/**
	 * Moves the sprite up by {@value MOVE_STEPS}.
	 */
	public void moveUp() {
		if(getTranslateY() < SCREEN_HEIGHT- 10 - getWidth())
			setTranslateY(getTranslateY() - MOVE_STEPS);
	}
	
	/**
	 * Moves the sprite down by {@value MOVE_STEPS}.
	 */
	public void moveDown() {
		if(getTranslateY() > 10)
			setTranslateY(getTranslateY() + MOVE_STEPS);
	}
	
	/**
	 * Sets dead to true.
	 */
	public void setDead(boolean d) { dead = d; }
	public boolean isDead() { return dead; }
	
	public String getType() {
		return type;
	}
	
}
