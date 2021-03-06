package environment_elements;

import piece_basics.EnvironmentElement;
import piece_basics.Robot;

public class Checkpoint extends EnvironmentElement {
	public static final String ID = "checkpoint";
	
	private int number;
	
	public Checkpoint(int number) {
		this.number = number;
	}

	@Override
	public void performImmediateAction(Robot r) {
		/*
		 * If this checkpoint's number is one greater than the robot's most recent checkpoint, this is indeed the next checkpoint it had to go to,
		 * so its most recent checkpoint number can be incremented
		 */
		if (r.getMostRecentCheckpoint() + 1 == number) {
			System.out.println("checkpoint " + number + " activated by robot " + r.getRobotNumber());
			r.setMostRecentCheckpoint(number);
		}
	}

	@Override
	public String getPieceID() {
		return ID;
	}
	
	public int getNumber() {
		return number;
	}

}
