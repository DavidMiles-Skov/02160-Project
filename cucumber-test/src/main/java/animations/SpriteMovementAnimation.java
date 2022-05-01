package animations;

import board.Position;
import view.Sprite;

// An animation for smooth sprite movement
public class SpriteMovementAnimation extends Animation {
	
	private Sprite sprite;
	private int cellWidth;
	private Position oldPos;
	private Position newPos;
	
	private int screenDiffX;
	private int screenDiffY;
	
	private double screenSmoothX;
	private double screenSmoothY;
	private double screenShiftX;
	private double screenShiftY;

	public SpriteMovementAnimation(int durationMs, Sprite sprite, Position oldPos, Position newPos, int cellWidth) {
		super(durationMs);
		this.sprite = sprite;
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.cellWidth = cellWidth;
	}

	@Override
	public void initializeAnimation() {
		screenDiffX = (newPos.getX()-oldPos.getX())*cellWidth;
		screenDiffY = (newPos.getY()-oldPos.getY())*cellWidth;
		
		screenShiftX = (double) screenDiffX / getNumFrames();
		screenShiftY = (double) screenDiffY / getNumFrames();
		
		screenSmoothX = sprite.getX();
		screenSmoothY = sprite.getY();
	}

	@Override
	public void establishNextFrame() {
		screenSmoothX += screenShiftX;
		screenSmoothY += screenShiftY;
		sprite.setX((int) screenSmoothX);
		sprite.setY((int) screenSmoothY);
	}

	@Override
	public void finalizeAnimation() {
		sprite.setX(newPos.getX()*cellWidth);
		sprite.setY(newPos.getY()*cellWidth);
	}

}
