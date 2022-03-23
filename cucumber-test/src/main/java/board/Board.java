package board;
import piece_basics.Robot;

public class Board implements IBoard {

	private Cell[][] matrix;
	private int numObstacles;
	public void setNumberOfObstacles(int n) {
		// TODO Auto-generated method stub
		this.numObstacles = n;
		
	}
	public void setMatrix(Cell[][] m) {
		this.matrix = m;
		
	}
	@Override
	public Cell index(int x, int y) {
		return matrix[x][y];
	}
	
	public boolean hasRobotAt(int x, int y) {
		return matrix[x][y].robot != null;
	}
	public Robot robotAt(int x, int y) {
		return matrix[x][y].robot;
	}
	

}
