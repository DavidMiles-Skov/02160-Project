package view;

import environment_elements.ConveyorBelt;
import environment_elements.Gear;
import piece_basics.EnvironmentElement;
import piece_basics.Orientation;
import piece_basics.Piece;
import piece_basics.Robot;
import utils.ImageUtils;

public class SpriteFactory {
	
	private static int nextRobotNumber = 1;

	public static Sprite getFromPiece(Piece piece, int cellSize, BoardPanel canvas) {
		if (piece instanceof EnvironmentElement) {
			EnvironmentElement eElement = (EnvironmentElement) piece;
			int x = eElement.getX()*cellSize;
			int y = eElement.getY()*cellSize;
			
			if (eElement instanceof Gear) {
				Gear gear = (Gear) eElement;
				String filepath = "images/gear" + (gear.isCounterClockwise() ? "_left" : "_right") + ".png";
				return new Sprite(ImageUtils.scaledImage(filepath, cellSize, cellSize), x, y, 0, canvas);
			} else if (eElement instanceof ConveyorBelt) {
				ConveyorBelt conveyorBelt = (ConveyorBelt) eElement;
				return new Sprite(ImageUtils.scaledImage("images/conveyor_belt.png", cellSize, cellSize), x, y, conveyorBelt.getOrientation().getDegrees(), canvas);
			} else {
				String filepath = "images/" + eElement.getPieceID() + ".png";
				return new Sprite(ImageUtils.scaledImage(filepath, cellSize, cellSize), x, y, 0, canvas);
			}
		} else if (piece instanceof Robot) {
			Robot robot = (Robot) piece;
			int x = robot.getX()*cellSize;
			int y = robot.getY()*cellSize;
			System.out.println(x);
			System.out.println(y);
			
			String filepath = "images/robot" + nextRobotNumber + ".png";
			nextRobotNumber++;
			
			System.out.println(filepath);
			
			return new Sprite(ImageUtils.scaledImage(filepath, cellSize, cellSize), x, y, robot.getOrientation().getDegrees(), canvas);
		} else {
			throw new IllegalArgumentException("Piece must be either an EnvironmentElement or a Robot");
		}
	}
}
