package piece_basics;

import board.IBoard;

public abstract class Piece {
	private int xPos;
	private int yPos;
	protected IBoard board;
	
	public Piece(IBoard board, int x, int y) {
		this.board = board;
		this.xPos = x;
		this.yPos = y;
	}
	
	public void setPosition(int newX, int newY) {
		int oldXPos = xPos;
		int oldYPos = yPos;
		this.xPos = newX;
		this.yPos = newY;
		board.updatePiecePosition(oldXPos, oldYPos, this);
	}
	
	public int getX() {
		return xPos;
	}
	public int getY() {
		return yPos;
	}
	
	public void shiftX(int offset) {
		setPosition(xPos + offset, yPos);
	}
	public void shiftY(int offset) {
		setPosition(xPos, yPos + offset);
	}
}