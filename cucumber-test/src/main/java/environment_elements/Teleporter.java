package environment_elements;

import piece_basics.EnvironmentElement;
import piece_basics.Robot;

public class Teleporter extends EnvironmentElement {
	private Teleporter receiving;
	private boolean Sending;
	
	public void setSending(boolean isSending) {
		this.Sending = isSending;
	}
	
	public boolean IsSending() {
		return this.Sending;
	}
	
	public static final String ID = "teleporter";
	
	public void setReceiving(Teleporter receiving) {
		this.receiving = receiving;
	}
	
	@Override
	public String getPieceID() {
		return ID;
	}


	@Override
	public void performRegisterAction() {
		if (board.hasRobotAt(calculatePosition()) && IsSending() == true) {
			Robot r = board.getRobotAt(calculatePosition());
			board.moveRobotFromTo(calculatePosition(), receiving.calculatePosition());
			System.out.println( r + " is being moved to " + receiving.calculatePosition());
		}
	}
}

