package environment_elements;

import board.Position;
import piece_basics.EnvironmentElement;

public class Gear extends EnvironmentElement {
	private boolean counterClockwise;
	
	public static final String ID = "gear";

	public Gear(boolean counterClockwise) {
		this.counterClockwise = counterClockwise;
	}
	
	@Override
	public void performRegisterAction() {
		Position p = calculatePosition();
		if (board.hasRobotAt(p)) {
			if (counterClockwise) {
				board.getRobotAt(p).turnLeft();
			} else {
				board.getRobotAt(p).turnRight();
			}
		}
	}

	@Override
	public String getPieceID() {
		return ID;
	}

}
