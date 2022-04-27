package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import animations.SpriteActivationAnimation;
import animations.SpriteMovementAnimation;
import animations.SpriteRobotLaserAnimation;
import animations.SpriteRotationAnimation;
import board.*;
import environment_elements.Laser;
import environment_elements.Pit;
import piece_basics.EnvironmentElement;
import piece_basics.Orientation;
import piece_basics.Piece;
import piece_basics.Robot;
import property_changes.ActivationEvent;
import property_changes.IPropertyChangeEvent;
import property_changes.MovementEvent;
import property_changes.PlacementEvent;
import property_changes.RemovalEvent;
import property_changes.RobotLaserEvent;
import property_changes.RotationEvent;
import property_changes.TeleportEvent;
import utils.ImageUtils;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = -2140843137512577992L;
	
	private static final int maxDimension = 600;
	
	private final MasterView masterView;
	
	private Image backgroundTile;
	
	private int rows;
	private int cols;
	private int cellWidth;
	private int width;
	private int height;
	private Board board;
	
	private List<Sprite> eElementSpriteList = new ArrayList<>();
	private List<ImageToggleSprite> robotLaserSpriteList = new ArrayList<>();
	private List<Sprite> robotSpriteList = new ArrayList<>();
	private Map<Integer, Sprite> robotNumToSpriteMap = new HashMap<>();

	public BoardPanel(IBoard board, MasterView masterView) {
		this.masterView = masterView;
		
		this.rows = board.getNumRows();
		this.cols = board.getNumColumns();
		
		if (rows >= cols) {
			cellWidth = maxDimension/rows;
		} else {
			cellWidth = maxDimension/cols;
		}
		
		width = cols*cellWidth;
		height = rows*cellWidth;
		
		setPreferredSize(new Dimension(width, height));
		
		backgroundTile = ImageUtils.scaledImage("images/tile.png", cellWidth, cellWidth);
	}

	public void addSprite(Piece piece, Position p) {
		if (piece instanceof EnvironmentElement) {
			eElementSpriteList.add(SpriteFactory.getFromPiece(piece, cellWidth, this));
		} else if (piece instanceof Robot) {
			Sprite robotSprite = SpriteFactory.getFromPiece(piece, cellWidth, this);
			robotSpriteList.add(robotSprite);
			Robot robot = (Robot) piece;
			robotNumToSpriteMap.put(robot.getRobotNumber(), robotSprite);
		} else {
			throw new IllegalArgumentException("Piece must be either a Robot or an EnvironmentElement");
		}
		repaint();
	}
	
	//add non-piece sprite, only lasers shot by robots use this
	public void addSprite(Orientation orientation, Position p, boolean endCap) {
		
		robotLaserSpriteList.add(SpriteFactory.getLaserSprite(orientation, p, cellWidth, this, endCap));
		//System.out.println(orientation + " " + p);
		repaint();
	}
	
	public void removeEElementSprite(Position p) {
		eElementSpriteList.remove(getEElementSpriteAtPosition(p));
		repaint();
	}
	
	public Sprite getEElementSpriteAtPosition(Position p) {
		int x = p.getX();
		int y = p.getY();
		
		for (Sprite sprite: eElementSpriteList) {
			if(sprite.getX()==cellWidth*x && sprite.getY()==cellWidth*y) {
				 return sprite;
			}
		}
		throw new SpriteNotFoundException("Could not find EElement sprite at " + p);
	}
	
	public Sprite getRobotSpriteAtPosition(Position p) {
		int x = p.getX();
		int y = p.getY();
		
		for (Sprite sprite: robotSpriteList) {
			if(sprite.getX()==cellWidth*x && sprite.getY()==cellWidth*y) {
				 return sprite;
			}
		}
		throw new SpriteNotFoundException("Could not find robot sprite at " + p);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				g2.drawImage(backgroundTile, col*cellWidth, row*cellWidth, null);
			}
		}
		
		for (Sprite sprite: eElementSpriteList) {
			sprite.drawUsing(g2);
		}
		for (Sprite sprite: robotLaserSpriteList) {
			sprite.drawUsing(g2);
		}
		for (Sprite sprite: robotSpriteList) {
			sprite.drawUsing(g2);
		}
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	public void propertyChange(IPropertyChangeEvent pci) {
		if (pci instanceof PlacementEvent) {
			PlacementEvent pe = (PlacementEvent) pci;
			addSprite(pe.getPiece(), pe.getPos());
		} else if (pci instanceof RemovalEvent) {
			RemovalEvent re = (RemovalEvent) pci;
			removeEElementSprite(re.getPos());
		} else if (pci instanceof ActivationEvent) {
			ActivationEvent ae = (ActivationEvent) pci;
			activateSprite(ae);
		} else if (pci instanceof MovementEvent) {
			MovementEvent me = (MovementEvent) pci;
			moveRobot(me);
		} else if (pci instanceof TeleportEvent) {
			TeleportEvent te = (TeleportEvent) pci;
			teleportRobot(te);
		} else if (pci instanceof RotationEvent) {
			RotationEvent re = (RotationEvent) pci;
			rotateSprite(re);
		} else if (pci instanceof RobotLaserEvent) {
			RobotLaserEvent rle = (RobotLaserEvent) pci;
			displayRobotLaser(rle);
		}
	}
	
	private void activateSprite(ActivationEvent ae) {
		masterView.enqueueAnimation(new SpriteActivationAnimation(getEElementSpriteAtPosition(ae.getPos())));
		System.out.println("Activation animation enqueued");
	}
	
	private void moveRobot(MovementEvent me) {
		Sprite spriteToMove = robotNumToSpriteMap.get(me.getRobotNum());
		int screenDiffX = me.getPosChange().getX() * cellWidth;
		int screenDiffY = me.getPosChange().getY() * cellWidth;
		masterView.enqueueAnimation(new SpriteMovementAnimation(500, spriteToMove, screenDiffX, screenDiffY));
		System.out.println("Movement animation enqueued");
	}
	
	private void teleportRobot(TeleportEvent te) {
		Position posCurrent = te.getPosCurrent();
		Sprite sprite = getRobotSpriteAtPosition(posCurrent);
		Position posNew = te.getPosNew();
		sprite.setX(posNew.getX()*cellWidth);
		sprite.setY(posNew.getY()*cellWidth);
		repaint();
	}
	
	private void rotateSprite(RotationEvent re) {
		
		Sprite spriteToRotate = robotNumToSpriteMap.get(re.getRobotNum());
		int diffAngle;
		int degCurr = re.getOrientationOld().getDegrees();
		int degNew = re.getOrientationNew().getDegrees();
		if(((degCurr < degNew) || ((degNew==0) && (degCurr==270))) && !(degCurr == 0 && degNew == 270)) {
			diffAngle = 90;
		} else {
			diffAngle = -90;
		}
		masterView.enqueueAnimation(new SpriteRotationAnimation(500, spriteToRotate, diffAngle));
		System.out.println("Rotation animation enqueued");
		
	}
	
	private void displayRobotLaser(RobotLaserEvent rle) {
		System.out.println("rle sequence started");
		Position startingPosition = rle.getPosStart();
		Position finishPosition = rle.getPosFinish();
		Position rollingPosition = startingPosition;
		System.out.println("s: " + startingPosition + "f: " + finishPosition);
		
		//horizontal laser
		if(startingPosition.getY()==finishPosition.getY()) {	
			//going right
			if(startingPosition.getX()<finishPosition.getX()) {
				addSprite(Orientation.RIGHT, startingPosition, true);
				while (rollingPosition.getX()<finishPosition.getX()-1){
					rollingPosition.incrX(1);
					addSprite(Orientation.RIGHT, rollingPosition, false);
				}
				addSprite(Orientation.LEFT, finishPosition, true);
			//going left
			} else {
				addSprite(Orientation.LEFT, startingPosition, true);
				while (rollingPosition.getX()>finishPosition.getX()+1){
					rollingPosition.incrX(-1);
					addSprite(Orientation.LEFT, rollingPosition, false);
				}
				addSprite(Orientation.RIGHT, finishPosition, true);
			}
			
		//vertical laser
		} else if(startingPosition.getX()==finishPosition.getX()) {
			//going up
			if(startingPosition.getY()-finishPosition.getY()<0) {
				addSprite(Orientation.UP, startingPosition, true);
				while (rollingPosition.getY()<finishPosition.getY()-1){
					rollingPosition.incrY(1);
					addSprite(Orientation.UP, rollingPosition, false);
				}
				addSprite(Orientation.DOWN, finishPosition, true);
			//going down
			} else {
				addSprite(Orientation.DOWN, startingPosition, true);
				while (rollingPosition.getY()>finishPosition.getY()+1){
					rollingPosition.incrY(-1);
					addSprite(Orientation.DOWN, rollingPosition, false);
				}
				addSprite(Orientation.UP, finishPosition, true);
			}
		}  
		masterView.enqueueAnimation(new SpriteRobotLaserAnimation(500, robotLaserSpriteList, cellWidth));
		//robotLaserSpriteList.clear();
		
		
	}
}
