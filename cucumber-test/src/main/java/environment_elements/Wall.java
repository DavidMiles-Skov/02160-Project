package environment_elements;

import piece_basics.EnvironmentElement;

public class Wall extends EnvironmentElement {
	
	public static final String ID = "wall";
	@Override
	public boolean isConveyorBlocking() {
		return true;
	}
	@Override
	public boolean isLaserBlocking() {
		return true;
	}
	@Override
	public String getPieceID() {
		return ID;
	}
}
