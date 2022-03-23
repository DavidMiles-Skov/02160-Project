package piece_basics;

public class Robot extends Piece implements IRegisterActor{
	private Orientation orientation;
	private int health;
	
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
			setY(getY() + spaces);
			break;
		case RIGHT:
			setX(getX() + spaces);
			break;
		case DOWN:
			setY(getY() - spaces);
			break;
		case LEFT:
			setX(getX() - spaces);
			break;
		}
	}
	@Override
	public void performRegisterAction() {
		
	}
}
