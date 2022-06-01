package GameLogic;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Abstract class of the Sprite
 */
public abstract class Sprite extends Rectangle {
	final int SCREEN_WIDTH  = 640;
	final int SCREEN_HEIGHT = 480;
	
	boolean dead = false;
	private Pane root;
	
	public final int MOVE_STEPS = 2; //Move speed basically.
	public String type;
	
	/**
	 * Constructor of theS Sprite
	 * @param w width of the Sprite
	 * @param h Height of the Sprite
	 * @param x X of the Sprite
	 * @param y Y of the Sprite
	 * @param image image of the Sprite
	 * @param root root Pane of the Sprite
	 */
	public Sprite(int w, int h, int x, int y, String image, Pane root) {
		super(w,h);
		this.setRoot(root);
		setTranslateX(x);
		setTranslateY(y);
		Image s;
		try {
			s = new Image(image);
			//s = new Image(new FileInputStream("/home/rguinartv/Imatges/sneed.png"));			
			super.setFill(new ImagePattern(s));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * abstract update method
	 */
	public abstract void update();
	

	/**
	 * Moves the sprite right by {@value MOVE_STEPS}.
	 */
	public void moveRight() {
		if(checkRight())
			setTranslateX(getTranslateX() + MOVE_STEPS);
	}
	/**
	 * Checks that the sprite is inside the screen by the right-side.
	 * @return true if it's inside the screen, false if outside
	 */
	public boolean checkRight() {
		return getTranslateX() < SCREEN_WIDTH - 10 - getWidth();
	}

	/**
	 * Moves the sprite left by {@value MOVE_STEPS}.
	 */
	public void moveLeft() {
		if(getTranslateX() > 10)
			setTranslateX(getTranslateX() - MOVE_STEPS);
	}
	
	/**
	 * Checks that the sprite is inside the screen by the left-side.
	 * @return true if it's inside the screen, false if outside
	 */
	public boolean checkLeft() {
		return getTranslateX() > 10;
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
	
	/**
	 * Gets the type
	 * @return returns the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the root pane
	 * @return returns the root pane
	 */
	public Pane getRoot() {
		return root;
	}

	/**
	 * Sets the root pane
	 * @param root sets the root pane
	 */
	public void setRoot(Pane root) {
		this.root = root;
	}
	
}
