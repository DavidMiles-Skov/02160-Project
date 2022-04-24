package piece_basics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import board.Position;
import cards.Card;
import environment_elements.ChainingPanel;
import environment_elements.RespawnPoint;
import environment_elements.Wall;
import property_changes.PropertyChangeType;
import cards.Program;
import java.lang.Math;  

public class Robot extends Piece {
	private static int nextRobotNumber = 1;
	private int robotNumber;
	
	private Orientation orientation = Orientation.UP;
	private int health;
	public static final int MAX_ROBOT_HEALTH = 3;
	private RespawnPoint currentRespawnPoint;
	private ChainingPanel ChainedFrom;
	private boolean chainable = false;
	private boolean wallOnBoard;
	private Robot chainedTo;
	private String command;	
	private Program program;
	private int mostRecentCheckpoint = 0;
	
	public static final String ID = "robot";
	
	public Robot() {
		health = MAX_ROBOT_HEALTH;
		robotNumber = nextRobotNumber;
		nextRobotNumber++;
	}
	
	public void setPosition(Position p) {
		board.setPosition(this, p);
	}
	public Position calculatePosition() {
		return board.calculatePosition(this);
	}
	public int getX() {
		return board.calculatePosition(this).getX();
	}
	public int getY() {
		return board.calculatePosition(this).getY();
	}
	
	public void setRespawnPoint(RespawnPoint r) {
		this.currentRespawnPoint = r;
	}
	

	
	
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	public Orientation getOrientation() {
		return orientation;
	}
	public void turnLeft() {
		Orientation oldOrientation = orientation;
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
		getPropertyChangeSupport().firePropertyChange(PropertyChangeType.ROTATION, calculatePosition(), oldOrientation, orientation);
	}
	public void turnRight() {
		Orientation oldOrientation = orientation;
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
		getPropertyChangeSupport().firePropertyChange(PropertyChangeType.ROTATION, calculatePosition(), oldOrientation, orientation);
	}	
	
	//checking wallcollision after 2 or 3 steps 




	//checking wall collision	
	private boolean wallCollision(Position p) {

		if (((board.hasEElementAt(p) && !board.getEElementAt(p).isWallCollsion())) || ((board.hasEElementAt(p)== false))) {
			getPropertyChangeSupport().firePropertyChange(PropertyChangeType.MOVEMENT, calculatePosition(), p);
			board.setPosition(this, p);
		}
		return false;	

	}

	//!board.getEElementAt(posToMoveTo).isWallCollsion())
	private void tryMoveRobot(Position posToMoveTo) {
		System.out.println(board.hasRobotAt(posToMoveTo));
		if (((board.hasEElementAt(posToMoveTo) && !(board.getEElementAt(posToMoveTo) instanceof Wall))) || ((board.hasEElementAt(posToMoveTo)== false)) && board.hasRobotAt(posToMoveTo) == false) {
			getPropertyChangeSupport().firePropertyChange(PropertyChangeType.MOVEMENT, calculatePosition(), posToMoveTo);
			board.setPosition(this, posToMoveTo);
		} else if (board.hasRobotAt(posToMoveTo)){
			Robot toBePushedRobot = board.getRobotAt(posToMoveTo);
			System.out.println("X initial position: " + toBePushedRobot.getX());
			toBePushedRobot.setOrientation(this.orientation);
			System.out.println("X Position moved to: " + toBePushedRobot.getX());
			toBePushedRobot.move(1);
			getPropertyChangeSupport().firePropertyChange(PropertyChangeType.MOVEMENT, calculatePosition(), posToMoveTo);
			board.setPosition(this, posToMoveTo);
		} else {
			System.out.println("no robot");
			return;
		}
	}
	
	public void shiftX(int spaces) {
		int absSpaces = Math.abs(spaces);
		for (int i = 0; i < absSpaces; i++) {
			Position p = calculatePosition();
			int increment = spaces < 0 ? -1 : 1;
			p.incrX(increment);
			tryMoveRobot(p);
		}
	}


	public void shiftY(int spaces) {
		int absSpaces = Math.abs(spaces);
		for (int i = 0; i < absSpaces; i++) {
			Position p = calculatePosition();
			int increment = spaces < 0 ? -1 : 1;
			p.incrY(increment);
			tryMoveRobot(p);
		}
	}
	
	public void move(int spaces) {
		System.out.println("Number of spaces to move: " + spaces);
 
		if(this.getChainedTo() == null) {
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
		else {
			switch(orientation) {
			case UP:
				shiftY(spaces);
				this.getChainedTo().shiftY(spaces);
				break;
			case RIGHT:
				shiftX(spaces);
				this.getChainedTo().shiftX(spaces);
				break;
			case DOWN:
				shiftY(-spaces);
				this.getChainedTo().shiftY(-spaces);
				break;
			case LEFT:
				shiftX(-spaces);
				this.getChainedTo().shiftX(-spaces);
				break;
			}
		}
	}

	public void heal() {
		if (health < MAX_ROBOT_HEALTH) {
			health++;
			getPropertyChangeSupport().firePropertyChange(PropertyChangeType.HEALTH_CHANGE, calculatePosition(), health, robotNumber);
		}
	}
	public void takeDamage() {
		health--;
		if (health == 0) reboot();
		getPropertyChangeSupport().firePropertyChange(PropertyChangeType.HEALTH_CHANGE, calculatePosition(), health, robotNumber);
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getMaxHealth() {
		return this.MAX_ROBOT_HEALTH;
	}
	
	public boolean isChainable() {
		return this.chainable;
	}
	public void setChainable(boolean chainable) {
		this.chainable = chainable;
	}
	public Robot getChainedTo() {
		return this.chainedTo;
	}
	public void setChainedTo(Robot chainedTo) {
		this.chainedTo = chainedTo;
	}
	
	public void reboot() {
		//unchains the robot when it reboots
		if (getChainedTo() != null) {
			getChainedTo().setChainedTo(null);
			getChainedTo().setChainable(false);
			setChainable(false);
			setChainedTo(null);
		}
		
		Position respawnPointPos = board.calculatePosition(currentRespawnPoint);
		if (board.hasRobotAt(respawnPointPos) && board.getRobotAt(respawnPointPos) != this) {
			board.getRobotAt(respawnPointPos).reboot();
		}
		System.out.println(calculatePosition());
		getPropertyChangeSupport().firePropertyChange(PropertyChangeType.TELEPORT, calculatePosition(), respawnPointPos);
		setPosition(respawnPointPos);
		health = MAX_ROBOT_HEALTH;
		// TODO: (maybe) also must discard all cards in hand and stop moving
	}

	@Override
	public void performRegisterAction() {
		Robot foundRobot = findRobotAhead();
		
		if (foundRobot != null) {
			foundRobot.takeDamage();
			getPropertyChangeSupport().firePropertyChange(PropertyChangeType.ROBOT_LASER, calculatePosition(), foundRobot.calculatePosition());
		}
	}
	private Robot findRobotAhead() {
		Position p = calculatePosition();
		
		switch(orientation) {
		case UP:
			p.incrY(1);
			break;
		case RIGHT:
			p.incrX(1);
			break;
		case DOWN:
			p.incrY(-1);
			break;
		case LEFT:
			p.incrX(-1);
			break;
		}
		
		Robot foundRobot = null;
		while (board.coordinateWithinBounds(p) && !laserBlocking(p)) {
			if (board.hasRobotAt(p)) {
				foundRobot = board.getRobotAt(p);
				break;
			}
			switch (orientation) {
			case UP:
				p.incrY(1); break;
			case RIGHT:
				p.incrX(1); break;
			case DOWN:
				p.incrY(-1); break;
			case LEFT:
				p.incrX(-1); break;
			}
		}
		return foundRobot;
	}
	
	
	private boolean laserBlocking(Position p) {
		return board.hasEElementAt(p) && board.getEElementAt(p).isLaserBlocking();
	}
	
	public Program getProgram(){
		return this.program;
	}
	
	public void setChainedFrom(ChainingPanel chainedFrom) {
		this.ChainedFrom = chainedFrom;
	}
	
	public ChainingPanel getChainedFrom() {
		return this.ChainedFrom;
	}
	
	public void setProgram(ArrayList<Card> program) {
		Program p = new Program();
		p.setCardList(program);
		this.program = p;
	}

	@Override
	public String getPieceID() {
		return ID;
	}
	


	public int getMostRecentCheckpoint() {
		return mostRecentCheckpoint;
	}

	public void setMostRecentCheckpoint(int mostRecentCheckpoint) {
		this.mostRecentCheckpoint = mostRecentCheckpoint;
	}

	public int getRobotNumber() {
		return robotNumber;
	}

}
