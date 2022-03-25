package piece_basics;

import board.IBoard;

public class Robot extends Piece implements IRegisterActor{
	private Orientation orientation;
	private int health = 3;
	private final int maxHealth = 3;
	
	public Robot(IBoard board, int x, int y) {
		super(board, x, y);
		orientation = Orientation.RIGHT;
		orientation = Orientation.LEFT;
		orientation = Orientation.UP;
		orientation = Orientation.DOWN;
		
	}

	public void executeProgram() {
		
		System.out.println("Program executed");
		
	}
	
	public void executeCommand() {
		
		System.out.println("Command executed");
		
	}
	
	public void turnLeft() {
		switch(orientation) {
		case UP:
			orientation = Orientation.LEFT;
			break;
		case RIGHT:
			orientation = Orientation.UP;
			break;
		case DOWN:
			orientation = Orientation.RIGHT;
			break;
		case LEFT:
			orientation = Orientation.DOWN;
			break;
		}
	}
	public void turnRight() {
		switch(orientation) {
		case UP:
			orientation = Orientation.RIGHT;
			break;
		case RIGHT:
			orientation = Orientation.DOWN;
			break;
		case DOWN:
			orientation = Orientation.LEFT;
			break;
		case LEFT:
			orientation = Orientation.UP;
			break;
		}
	}
	public void move(int spaces) {
		switch(orientation) {
		case UP:
			shiftY(spaces);
			break;
		case RIGHT:
			shiftX(spaces);
			break;
		case DOWN:
			shiftY(-spaces);
			break;
		case LEFT:
			shiftX(-spaces);
			break;
		}
	}
	

	public void takeDamage() {
		health--;
	}
	public void heal() {
		if (health < maxHealth) health++;
	}
	
	public void setHealth(int x) {
		this.health = x;
	}
	public int getHealth() {
		return this.health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}

	@Override
	public void performRegisterAction() {
		
	}
	public Orientation getOrientation() {
		
		return orientation;

		
	}
}
