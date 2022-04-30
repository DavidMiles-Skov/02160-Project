package environment_elements;

import cards.Program;
import piece_basics.EnvironmentElement;

public class ReversalPanel extends EnvironmentElement {

	public static final String ID = "reversal_panel";
	public Program newProg = new Program();

	@Override
	public String getPieceID() {
		return ID;
	}
		
	@Override
	public void performRegisterAction() {
		if (board.hasRobotAt(calculatePosition())) { 
			Program program = board.getRobotAt(calculatePosition()).getProgram(); //get the robot's program
			int programLength = program.getCardList().size();
			
			/*iterate through the program and create a new program 
			with the opposite card of each card in the program*/
			for (int i = 0; i < programLength; i++) {
				newProg.getCardList().add(program.getCardList().get(i).getOppositeCard());
			}
			System.out.println(board.getRobotAt(calculatePosition()) + " got the moves in its program reversed");
			board.getRobotAt(calculatePosition()).setProgram(newProg.getCardList()); //set the robot's program to be the reversed program
		}

	}

}
